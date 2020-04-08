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

		var pData = TableHtmlBuilder.build(aRows, aColumn)
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
				}
				3 ->
				{
					dataForWebView.datePivot = DatePivot.buildTri(aRows, aColumn)
					dataForWebView.rowsPivot = 180//change for rows
				}
				else -> {}
			}
		}
		//endregion

		return dataForWebView
	}
}