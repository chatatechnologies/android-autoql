package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.toCapitalColumn
import chata.can.chata_ai.model.StringContainer
import chata.can.chata_ai.pojo.query.SearchColumn

object TableHtmlBuilder
{
	fun buildTable(
		aRows: ArrayList<ArrayList<String>>,
		aColumn: ArrayList<ColumnQuery>,
		limitRow: Int,
		idTable: String = "idTableBasic"): Pair<String, Int>
	{
		aColumn.find { it.isVisible }?.let {
			//region create table head
			val headTable = StringBuilder("<thead><tr>")
			val footTable = StringBuilder("<tfoot><tr>")
			for (column in aColumn)
			{
//				if (column.isVisible)
//				{
					val cellHead = column.displayName.ifEmpty {
						column.name.toCapitalColumn()
					}
					headTable.append("<th>$cellHead</th>")
					footTable.append("<th>$cellHead</th>")
//				}
			}
			footTable.append("</tr></tfoot>")
			headTable.append("</tr></thead>")
			//endregion

			var numRows = 1
			//region create body table with id idTableBasic
			val bodyTable = StringBuilder("<tbody>")
			val aRowsTR = ArrayList<String>()
			for (aRow in aRows) {
				if (numRows >= limitRow)
					break

				val sRow = StringBuilder("")
				for ((index, cell) in aRow.withIndex()) {
					val column = aColumn[index]

					val valueRow = if (cell.isNotEmpty() && cell != "null")
						cell.formatWithColumn(column)
					else ""

//					val classHidden = if (!column.isVisible) " class=\"td-hidden\"" else ""
//					sRow.append("<td$classHidden>$valueRow</td>")
					sRow.append("<td>$valueRow</td>")
				}
				numRows++
				aRowsTR.add("<tr>$sRow</tr>")
			}

			if (SearchColumn.isDateColumn(aColumn))
				aRowsTR.reverse()

			for (row in aRowsTR)
				bodyTable.append(row)

			bodyTable.append("</tbody>")
			//endregion
			return Pair("<table id=\"$idTable\">$headTable$footTable$bodyTable</table>", numRows)
		} ?: run {

			return Pair(
				"""<div id='idTableBasic' class="empty-state">
	<span class="alert-icon">&#9888</span>
	<p>
		${StringContainer.columnHidden}
	</p>
</div>""",
				5)
		}
	}
}