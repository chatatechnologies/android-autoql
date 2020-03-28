package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.QueryBase

object HtmlBuilder
{
	fun build(queryBase: QueryBase): String
	{
		val aRows = queryBase.aRows
		val aColumn = queryBase.aColumn
		TableHtmlBuilder.build(aRows, aColumn)
		return ""
	}
}