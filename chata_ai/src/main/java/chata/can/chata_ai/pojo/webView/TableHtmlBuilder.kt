package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.toCapitalColumn

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
		//endregion

		//region create body table with id nativeTable
		var bodyTable = "<tbody>"
		for (aRow in aRows)
		{
			var iterator = 0

			var sRow = ""
			for (cell in aRow)
			{
				val valueRow =
				if (cell.isEmpty()) ""
				else
				{
					val column = aColumn[iterator++]
					cell.formatWithColumn(column)
				}
				sRow += "<td>$valueRow</td>"
			}
			bodyTable += "<tr>$sRow</tr>"
		}

		bodyTable += "</tbody>"
		//endregion

		return "<table>$headTable$bodyTable</table>"
	}
}