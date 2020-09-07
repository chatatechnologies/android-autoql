package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.toCapitalColumn

object TableHtmlBuilder
{
	fun buildTable(
		aRows: ArrayList<ArrayList<String>>,
		aColumn: ArrayList<ColumnQuery>,
		idTable: String = "idTableBasic"): Pair<String, Int>
	{
		//region create table head
		val headTable = StringBuilder("<thead><tr>")
		for (column in aColumn)
		{
			if (column.isVisible)
			{
				val cellHead = if (column.displayName.isNotEmpty())
				{
					column.displayName
				}
				else
				{
					column.name.toCapitalColumn()
				}
				headTable.append("<th>$cellHead</th>")
			}
		}
		headTable.append("</tr></thead>")
		//endregion

		var numRows = 1
		//region create body table with id idTableBasic
		val bodyTable = StringBuilder("<tbody>")
		for (aRow in aRows)
		{
			val sRow = StringBuilder("")
			for ((index, cell) in aRow.withIndex())
			{
				val column = aColumn[index]
				val valueRow = if (column.isVisible)
				{
					if (cell.isNotEmpty())
						cell.formatWithColumn(column)
					else ""
				}
				else
					"_--_"

				if (valueRow != "_--_")
					sRow.append("<td>$valueRow</td>")
			}
			numRows++
			bodyTable.append("<tr>$sRow</tr>")
		}

		bodyTable.append("</tbody>")
		//endregion

		return Pair("<table id=\"$idTable\">$headTable$bodyTable</table>", numRows)
	}
}