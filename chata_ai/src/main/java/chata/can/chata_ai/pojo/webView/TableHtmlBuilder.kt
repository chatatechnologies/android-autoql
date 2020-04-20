package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.toCapitalColumn

object TableHtmlBuilder
{
	fun buildTable(
		aRows: ArrayList<ArrayList<String>>,
		aColumn: ArrayList<ColumnQuery>,
		mIndexColumn: LinkedHashMap<Int, Int>): Pair<String, Int>
	{
		//region create table head
		val headTable = StringBuilder("<thead><tr>")
		aColumn.joinTo(headTable, "", postfix =  "</tr></thead>\n") {
			val cellHead = if (it.displayName.isNotEmpty())
			{
				it.displayName
			}
			else
				it.name.toCapitalColumn()
			"<th>$cellHead</th>"
		}

		var numRows = 1
		//region create body table with id idTableBasic
		val bodyTable = StringBuilder("<tbody>")

		aRows.joinTo(bodyTable,"", postfix = "</tbody>") {
			aRow ->
			var iterator = 0
			numRows++
			aRow.joinTo(StringBuilder("<tr>"),"",postfix = "</tr>") {
				cell ->
				val valueRow =
					if (cell.isEmpty()) ""
					else
					{
						var column: ColumnQuery? = null
						for ((_, pos) in mIndexColumn)
						{
							if (pos == iterator)
							{
								column = aColumn[pos]
								cell.formatWithColumn(column)
								iterator++
								break
							}
						}
						column?.let { cell.formatWithColumn(it) }?: ""
					}
				"<td>$valueRow</td>"
			}
		}

		return Pair("<table id=\"idTableBasic\">$headTable$bodyTable</table>", numRows)
	}
}