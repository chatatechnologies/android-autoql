package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object HtmlBuilder
{
	fun build(queryBase: QueryBase): DataForWebView
	{
		val aRows = queryBase.aRows
		val aColumn = queryBase.aColumn

		val dataForWebView = DataForWebView()

		var pData = TableHtmlBuilder.buildTable(aRows, aColumn)
		dataForWebView.table = pData.first
		dataForWebView.rowsTable = pData.second

		//region date pivot
		if (queryBase.isTypeColumn(TypeDataQuery.DATE))
		{
			when(aColumn.size)
			{
				2 ->
				{
					pData = DatePivot.buildBi(aRows, aColumn)
					with(dataForWebView)
					{
						datePivot = pData.first
						rowsPivot = pData.second
					}
					queryBase.configActions = 1
				}
				3 ->
				{
					val isDate = aColumn[0].type == TypeDataQuery.DATE
					val isDollar1 = aColumn[1].type == TypeDataQuery.DOLLAR_AMT
					val isDollar2 = aColumn[2].type == TypeDataQuery.DOLLAR_AMT

					if (isDate && isDollar1 && isDollar2)
					{
						queryBase.configActions = 2
					}
					else
					{
						dataForWebView.datePivot = DatePivot.buildTri(aRows, aColumn)
						dataForWebView.rowsPivot = 180
						queryBase.configActions = 3
					}
				}
				else -> {}
			}
		}
		else
		{
			queryBase.configActions = when(aColumn.size)
			{
				2 -> 4
				else -> 0
			}
		}
		//endregion
		val configAllow = aColumn.size == 3

		//with categories()
		with(Categories)
		{
			val aCatX = buildCategoryByPosition(
				Category(queryBase.aRows, aColumn[0], 0,
					true, hasQuotes = true, allowRepeat = !configAllow))
			val aCatY = buildCategoryByPosition(
				Category(queryBase.aRows, aColumn[1], 1,
					true, hasQuotes = true, allowRepeat = !configAllow))
			val aCatYS = buildCategoryByPosition(
				Category(queryBase.aRows, aColumn[1], 1,
					true, hasQuotes = true, allowRepeat = !configAllow))

			dataForWebView.catX = aCatX.toString()
			dataForWebView.catY = aCatY.toString()

			if (configAllow)
			{

			}
			else
			{
				dataForWebView.catYS = aCatYS.toString()
				dataForWebView.dataChartBi = Table.generateDataTable(queryBase.aRows, aColumn,true)
			}
		}

		return dataForWebView
	}
}