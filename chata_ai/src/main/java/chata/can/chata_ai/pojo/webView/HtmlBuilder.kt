package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object HtmlBuilder
{
	fun build(queryBase: QueryBase): String
	{
		val aRows = queryBase.aRows
		val aColumn = queryBase.aColumn
		var tableHtml = TableHtmlBuilder.build(aRows, aColumn)

		if (queryBase.isTypeColumn(TypeDataQuery.DATE))
		{
			tableHtml = if (aColumn.size == 2)
				DatePivot.buildBi(aRows, aColumn)
			else {
				DatePivot.buildTri(aRows, aColumn)
			}
		}

		return tableHtml
	}
}