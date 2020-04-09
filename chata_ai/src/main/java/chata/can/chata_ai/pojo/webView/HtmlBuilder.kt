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
						//queryBase.configActions = 2
					}
					else
					{
						dataForWebView.datePivot = DatePivot.buildTri(aRows, aColumn)
						dataForWebView.rowsPivot = 180
						//queryBase.configActions = 3
					}
				}
				else -> {}
			}
		}
		//endregion

		return dataForWebView
	}
}