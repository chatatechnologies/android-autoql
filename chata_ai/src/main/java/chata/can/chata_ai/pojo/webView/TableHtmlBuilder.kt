package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.view.extension.formatWithColumn
import chata.can.chata_ai.view.extension.toCapitalColumn

object TableHtmlBuilder
{
	fun build(
		aRows: ArrayList<ArrayList<String>>,
		aColumn: ArrayList<ColumnQuery>): String
	{
		//region create table head
		var headTable = "<thead><tr>"
		headTable += aColumn.joinToString("") {
			val column = it.name.toCapitalColumn()
			"<th>$column</th>"
		}
		headTable += "</tr></thead>"
		//endregion

		//region create body table with id nativeTable
		var bodyTable = "<tbody>"
		for (aRow in aRows)
		{
			var iterator = 0
			bodyTable += aRow.joinToString("", "<tr>", "</tr>") {
				if (it.isEmpty())
				{
					it
				}
				else
				{
					val valueRow = if (it.isEmpty())
						it
					else
					{
						val column = aColumn[iterator++]
						it.formatWithColumn(column, "$", ",")
					}
					"<td>$valueRow</td>"
				}
			}
		}
		bodyTable += "</tbody>"
		//endregion

		//return "<table id='${typeTable}'>$headTable$bodyTable</table>"
		return "<table>$headTable$bodyTable</table>"
	}
}