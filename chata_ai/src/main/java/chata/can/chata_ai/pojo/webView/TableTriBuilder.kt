package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.clearDecimals
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.toDoubleNotNull
import chata.can.chata_ai.pojo.chat.ColumnQuery

object TableTriBuilder
{
	fun getMapDataTable(aDataTable: ArrayList<ArrayList<Any>>): LinkedHashMap<String, String>
	{
		val mData = LinkedHashMap<String, String>()
		if (aDataTable.isNotEmpty())
		{
			aDataTable.firstOrNull()?.let {
				for (data in aDataTable)
				{
					val x = data.getOrElse(0) { "" }.toString()
					val y = data.getOrElse(1) { "" }.toString()
					val value = data.getOrElse(2) { "" }.toString()
					mData["${x}_$y"] = value
				}
			}
		}
		return mData
	}

	fun lineDataPivot(
		mDataPivot: LinkedHashMap<String, String>,
		aCatX: List<String>,
		nameHeader: String
	): Pair<String, Int>
	{
		val sbHead = StringBuilder(
			"<thead><tr><th>$nameHeader</th><th>$nameHeader</th></tr></thead>")

		val sbBody = StringBuilder("<tbody>")
		for (index1 in aCatX.indices)
		{
			val cell1 = aCatX[index1]
			val value = mDataPivot["0_${index1}"]
			val row = "<td>${cell1.replace("\"", "")}</td><td>$value</td>"
			sbBody.append("<tr>$row</tr>")
		}
		sbBody.append("</tbody>")
		return Pair("<table id=\"idTableDataPivot\">$sbHead$sbBody</table>", aCatX.size)
	}

	fun buildDataPivot(
		mDataPivot: LinkedHashMap<String, String>,
		aColumn: ColumnQuery,
		aCatX: List<String>,
		aCatY: List<String>,
		nameHeader: String
	): Pair<String, Int>
	{
		val sbHead = StringBuilder("<thead><tr><th>$nameHeader</th>")
		sbHead.append(aCatY.joinTo(StringBuilder(""), separator = "") {
			"<th>${it.replace("\"", "")}</th>"
		})
		sbHead.append("</tr></thead>")

		val aRows = ArrayList<String>()
		for (indexX in aCatX.indices)
		{
			val categoryX = aCatX[indexX]
			val sbRow = StringBuilder("<td>${categoryX.replace("\"", "")}</td>")
			for (indexY in aCatY.indices)
			{
				var cell = mDataPivot["${indexX}_$indexY"] ?: ""
				if (cell.isNotEmpty())
					cell = cell.clearDecimals()
				cell = cell.formatWithColumn(aColumn)
				sbRow.append("<td>$cell</td>")
			}
			aRows.add("<tr>$sbRow</tr>")
		}
		//aRows.sort()
		val sbBody = StringBuilder("<tbody>")
		for (row in aRows)
		{
			sbBody.append("<tr>$row</tr>")
		}
		sbBody.append("</tbody>")
		return Pair("<table id=\"idTableDataPivot\">$sbHead$sbBody</table>", aCatY.size)
	}

	fun generateDataTableTri(
		aRows: ArrayList<ArrayList<String>>,
		columns: ColumnQuery,
		aCatX: List<String>,
		aCatY: List<String>,
		hasNumber: Boolean
	): Pair< ArrayList<ArrayList<Any>>, LinkedHashMap<String, String> >
	{
		val aDataTable = ArrayList<ArrayList<Any>>()
		val mData = LinkedHashMap<String, String>()

		for (aCells in aRows)
		{
			val valueX = "\"${aCells[1]}\""
			var valueY = "\"${aCells[0]}\""
			val value = aCells[2]

			val iX = aCatX.indexOf(valueX)
			var iY = aCatY.indexOf(valueY)

			if (iY == -1)
			{
				val cellFirst = aCells[1]
				valueY = if(cellFirst.isEmpty() || cellFirst == "0" )
					"\" \""
				else
				{
					val dateFormatted = cellFirst.formatWithColumn(columns,
						currencySymbol = "",
						commaCharacter = "")
					"\"${dateFormatted.replace(".", ",")}\""
				}

				iY = aCatY.indexOf(valueY)
			}

			val dNumber = value.toDoubleNotNull()
			mData["${iX}_$iY"] = if (hasNumber) "$dNumber" else value
			val aNewRow = arrayListOf<Any>(iX, iY, dNumber)
			aDataTable.add(aNewRow)
		}
		return Pair(aDataTable, mData)
	}
}