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
		aColumn: ColumnQuery,
		aCatX: List<String>,
		nameHeader: String
	): Pair<String, Int>
	{
		val sbHead = StringBuilder(
			"<thead><tr><th>$nameHeader</th><th></th></tr></thead>")

		val sbBody = StringBuilder("<tbody>")
		for (index1 in aCatX.indices)
		{
			val cell1 = aCatX[index1]
			var value = mDataPivot["0_${index1}"] ?: ""
			if (value.isNotEmpty())
				value = value.clearDecimals()
			value = value.formatWithColumn(aColumn)

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
	): Triple<String, Int, ArrayList<Int>>
	{
		val sbHead = StringBuilder("<thead><tr><th>$nameHeader</th>")
		sbHead.append(aCatX.joinTo(StringBuilder(""), separator = "") {
			"<th>${it.replace("\"", "")}</th>"
		})
		sbHead.append("</tr></thead>")

		val sbFoot = StringBuilder("<tfoot><tr><th>$nameHeader</th>")
		sbFoot.append(aCatX.joinTo(StringBuilder(""), separator = "") {
			"<th>${it.replace("\"", "")}</th>"
		})
		sbFoot.append("</tr></tfoot>")

		val aIndexZero = ArrayList<Int>()
		val aRows = ArrayList<String>()

		for (indexY in aCatY.indices)
		{
			var onlyZero = true
			val categoryY = aCatY[indexY]
			val sbRow = StringBuilder("<td>${categoryY.replace("\"", "")}</td>")
			for (indexX in aCatX.indices)
			{
				var cell = mDataPivot["${indexX}_$indexY"] ?: ""
				//region
				if (cell != "0.0" && cell.isNotEmpty())
					onlyZero = false
				//endregion
				if (cell.isNotEmpty())
					cell = cell.clearDecimals()
				cell = cell.formatWithColumn(aColumn)
				sbRow.append("<td>$cell</td>")
			}
			if (onlyZero)
				aIndexZero.add(indexY)
			aRows.add("<tr>$sbRow</tr>")
		}
		val sbBody = StringBuilder("<tbody>")
		for (row in aRows) sbBody.append(row)
		sbBody.append("</tbody>")
		return Triple("<table id=\"idTableDataPivot\">$sbHead$sbFoot$sbBody</table>", aCatY.size, aIndexZero)
	}

	class DataTableTri(
		val aRows: ArrayList<ArrayList<String>>,
		val columns: ColumnQuery,
		val aCatX: List<String>,
		val aCatY: List<String>,
		val hasNumber: Boolean,
		val isReverse: Boolean)

	fun generateDataTableTri(dataTableTri: DataTableTri): Pair< ArrayList<ArrayList<Any>>, LinkedHashMap<String, String> >
	{
		dataTableTri.run {
			val aDataTable = ArrayList<ArrayList<Any>>()
			val mData = LinkedHashMap<String, String>()

			for (aCells in aRows)
			{
				val valueX = "\"${aCells[1]}\""
				var valueY = "\"${aCells[0]}\""
				val value = aCells[2]

				val iX = if (isReverse) aCatX.indexOf(valueX) else aCatY.indexOf(valueX)
				var iY = if (isReverse) aCatY.indexOf(valueY) else aCatX.indexOf(valueY)

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
				mData["${iY}_$iX"] = if (hasNumber) "$dNumber" else value
				val aNewRow = arrayListOf<Any>(iY, iX, dNumber)
				aDataTable.add(aNewRow)
			}
			return Pair(aDataTable, mData)
		}
	}
}