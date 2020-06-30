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
			var iterator = 0

			val sRow = StringBuilder("")
			for (cell in aRow)
			{
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
								iterator++
								break
							}
						}
						column?.let {
							if (it.isVisible)
							{
								cell.formatWithColumn(it)
							}
							else
							{
								"_--_"
							}
						}?: ""
					}

				if (valueRow != "_--_")
					sRow.append("<td>$valueRow</td>")
			}
			numRows++
			bodyTable.append("<tr>$sRow</tr>")
		}

		bodyTable.append("</tbody>")
		//endregion

		return Pair("<table id=\"idTableBasic\">$headTable$bodyTable</table>", numRows)
	}
}