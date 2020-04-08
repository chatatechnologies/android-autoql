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

		dataForWebView.table = TableHtmlBuilder.build(aRows, aColumn)

		//region date pivot
		if (queryBase.isTypeColumn(TypeDataQuery.DATE))
		{
			when(aColumn.size)
			{
				2 ->
				{
					dataForWebView.datePivot = DatePivot.buildBi(aRows, aColumn)
				}
				3 ->
				{
					dataForWebView.datePivot = DatePivot.buildTri(aRows, aColumn)
				}
				else -> {}
			}
		}
		//endregion

		return dataForWebView
	}
}