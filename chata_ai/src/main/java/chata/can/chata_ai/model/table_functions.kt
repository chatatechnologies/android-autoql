package chata.can.chata_ai.model

import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.pojo.chat.ColumnQuery

fun getDataPivotColumn(aRows: ArrayList<ArrayList<String>>)
	: Pair<ArrayList<ArrayList<String>>, ArrayList<String>>
{
	val categoriesX = ArrayList<String>()
	val categoriesY = ArrayList<String>()
	val totalSum = ArrayList<DataPivotRow>()
	val totalDate = ArrayList<ArrayList<Any>>()

	for (row in aRows)
	{
		val data1 = row[0]
		val data2 = row[1]
		val data3 = row[2].toDoubleOrNull() ?: 0.0

		if (data1 !in categoriesX)
		{
			categoriesX.add(data1)
		}

		if (data2 !in categoriesY)
		{
			categoriesY.add(data2)
			totalDate.add(arrayListOf())
		}

		val locX = categoriesX.indexOf(data1)
		val locY = categoriesY.indexOf(data2)
		totalDate[locY].add(data3)
		val test = DataPivotRow(locX, locY, data3)
		totalSum.add(test)
	}

	//revert categoriesY
	val final = ArrayList<ArrayList<String>>()
	for ((indexX, category) in categoriesY.withIndex())
	{
		val finalB = arrayListOf(category)
		for ((indexY, _) in categoriesY.withIndex())
		{
			val position = totalSum.indexOfFirst { it.posX == indexX && it.posY == indexY }
			//TODO CHECK TO MONEY
			val value = if (position == -1) "$0" else totalSum[position].value.toString()
			finalB.add(value)
		}
		final.add(finalB)
	}
	val header = arrayListOf("")
	val headerFree = ArrayList<String>()
	for (cat in categoriesX)
	{
		header.add(cat)
		headerFree.add(cat)
	}
	final.add(0, header)
	return Pair(final, headerFree)
}

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