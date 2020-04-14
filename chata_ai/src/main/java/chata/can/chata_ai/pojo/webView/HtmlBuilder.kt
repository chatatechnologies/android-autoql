package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.toCapitalColumn
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
		var textDatePivot = ""
		val configAllow = aColumn.size == 3

		if (queryBase.configActions == 1 || queryBase.configActions == 3)
		{

		}

		with(Categories)
		{
			val aCatX = buildCategoryByPosition(
				Category(aRows, aColumn[0], 0,
					true, hasQuotes = true, allowRepeat = !configAllow))
			val aCatY = buildCategoryByPosition(
				Category(aRows, aColumn[1], 1,
					true, hasQuotes = true, allowRepeat = !configAllow))
			val aCatYS = buildCategoryByPosition(
				Category(aRows, aColumn[1], 1,
					true, hasQuotes = true, allowRepeat = !configAllow))
			dataForWebView.drillX = buildCategoryByPosition(
				Category(aRows, aColumn[0], 0,
					false, hasQuotes = true, allowRepeat = true)).toString()
			dataForWebView.drillY = buildCategoryByPosition(
				Category(aRows, aColumn[1], 1,
					false, hasQuotes = true, allowRepeat = true)).toString()

			dataForWebView.catX = aCatX.toString()
			dataForWebView.catY = aCatY.toString()

			if (configAllow)
			{
				if (aColumn.size > 1)
				{
					val aDataTable = TableTriBuilder.generateDataTableTri(aRows, aColumn[1], aCatX, aCatY)
					dataForWebView.dataChartBi = aDataTable.toString()

					dataForWebView.catYS = LineBuilder.generateDataChartLine(aDataTable, aCatY).toString()
					queryBase.isTri = true
					dataForWebView.isBi = false
				}

				if (queryBase.configActions == 2)
				{
					val aDataXAxis = ArrayList<String>()
					val aDataYAxis = ArrayList<String>()

					for (row in aRows)
					{
						if (row.size == 3)
							aDataXAxis.add(row[1])
						aDataYAxis.add(row[2])
					}

					val title1 = aColumn[0].name
					val title2 = aColumn[1].name
					dataForWebView.catYS = "[{name:\"${title1.toCapitalColumn()}\", data:$aDataXAxis}," +
						"{name:\"${title2.toCapitalColumn()}\", data:$aDataYAxis}]"
					queryBase.isContrast = true
					queryBase.isTri = true
					dataForWebView.isBi = false
				}
			}
			else
			{
				dataForWebView.catYS = aCatYS.toString()
				dataForWebView.dataChartBi = Table.generateDataTable(aRows, aColumn,true)
			}
		}

		return dataForWebView
	}
}