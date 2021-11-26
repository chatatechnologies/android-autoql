package chata.can.chata_ai.model

import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.pojo.chat.ColumnQuery

fun tableString(
	dataTable: ArrayList<ArrayList<String>>,
	dataColumn: List<String>,
	idTable: String,
	columns: ArrayList<ColumnQuery>,
	datePivot: Boolean = false): String
{
	val start = "<table id='$idTable'>"
	var body = ""
	val end = "</table>"

	if (dataColumn.isNotEmpty())
	{
		body = "<thead><tr>"
		for ((index, column) in dataColumn.withIndex())
		{
			if (columns.size == dataColumn.size)
			{
				if (columns[index].isVisible)
				{
					body +="<th>$column</th>"
				}
			}
			else
			{
				body +="<th>$column</th>"
			}
		}
		body += "</tr></thead><tbody>"
		for (row in dataTable)
		{
			body += "<tr>"
			for ((index, column) in row.withIndex())
			{
				if (columns.size == row.size)
				{
					if (columns[index].isVisible)
					{
						val finalColumn =
							if (datePivot) column
							else column.formatWithColumn(columns[index])
						body += "<td><span class='limit'>$finalColumn</span></td>"
					}
				}
				else
				{
					val finalColumn =
						if (datePivot) column
						else column.formatWithColumn(columns[index])
					body += "<td><span class='limit'>$finalColumn</span></td>"
				}
			}
			body += "</tr>"
		}
		body+="</tbody>"
	}
	return "$start$body$end"
}