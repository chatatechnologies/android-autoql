package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object HtmlBuilder
{
	fun build(queryBase: QueryBase): String
	{
		val aRows = queryBase.aRows
		val aColumn = queryBase.aColumn
		val tableHtml = TableHtmlBuilder.build(aRows, aColumn)

		if (queryBase.isTypeColumn(TypeDataQuery.DATE))
		{
			DatePivot.build(aRows, aColumn)
		}

		return tableHtml
	}
}