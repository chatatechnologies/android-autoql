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
		for (column in aColumn)
		{
			val cellHead = column.name.toCapitalColumn()
			headTable += "<th>$cellHead</th>"
		}
		headTable += "</tr></thead>"
		/*headTable += aColumn.joinToString("") {
			val column = it.name.toCapitalColumn()
			"<th>$column</th>"
		}*/
		//endregion

		//region create body table with id nativeTable
		var bodyTable = "<tbody>"
		for (aRow in aRows)
		{
			var iterator = 0
			/*for (cell in aRow)
			{
				if (cell.isEmpty()) ""
				else
				{
					val column = aColumn[iterator++]
					cell.formatWithColumn(column, "$", ",")
				}
			}*/

			bodyTable += aRow.joinToString("", "<tr>", "</tr>") {
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
		bodyTable += "</tbody>"
		//endregion

		//return "<table id='${typeTable}'>$headTable$bodyTable</table>"
		return "<table>$headTable$bodyTable</table>"
	}
}