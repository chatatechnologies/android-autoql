package chata.can.chata_ai.retrofit.data.model.html

import chata.can.chata_ai.extension.toCapitalColumn
import chata.can.chata_ai.model.StringContainer
import chata.can.chata_ai.retrofit.ColumnEntity
import chata.can.chata_ai.retrofit.core.formatValue

class TableBuilder(
	private val rows: MutableList< List<String> >,
	private val columns: List<ColumnEntity>,
	private val maxNumRows: Int,
	private val idTable: String = "idTableBasic"
) {
	fun getTable(): Pair<String, Int> {
		columns.find { it.isVisible }?.let {
			//region create table head
			val headTable = StringBuilder("<thead><tr>")
			val footTable = StringBuilder("<tfoot><tr>")
			for (column in columns)
			{
				if (column.isVisible)
				{
					val cellHead = column.displayName.ifEmpty {
						column.name.toCapitalColumn()
					}
					headTable.append("<th>$cellHead</th>")
					footTable.append("<th>$cellHead</th>")
				}
			}
			footTable.append("</tr></tfoot>")
			headTable.append("</tr></thead>")
			//endregion

			var numRows = 1
			//region create body table with id idTableBasic
			val bodyTable = StringBuilder("<tbody>")
			val aRowsTR = ArrayList<String>()
			for (aRow in rows)
			{
				if (numRows >= maxNumRows)
					break

				val sRow = StringBuilder("")
				for ((index, cell) in aRow.withIndex())
				{
					val column = columns[index]
					column.isVisible

					val valueRow = if (cell.isNotEmpty() && cell != "null")
						cell.formatValue(column)
					else ""

					val classHidden = if (!column.isVisible) " class=\"td-hidden\"" else ""
					sRow.append("<td$classHidden>$valueRow</td>")
				}
				numRows++
				aRowsTR.add("<tr>$sRow</tr>")
			}

			if (SearchColumn().isDateColumn(columns))
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