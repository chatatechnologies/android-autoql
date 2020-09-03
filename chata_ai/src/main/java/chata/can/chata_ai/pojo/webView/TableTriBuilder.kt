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

	fun buildDataPivot(
		mDataPivot: LinkedHashMap<String, String>, aCatX: ArrayList<String>, aCatY: ArrayList<String>
	): Pair<String, Int>
	{
		val sbHead = StringBuilder("<thead><tr><th></th>")
		sbHead.append(aCatY.joinTo(StringBuilder(""), separator = "") {
			"<th>${it.replace("\"", "")}</th>"
		})
		sbHead.append("</tr></thead>")

		val sbBody = StringBuilder("<tbody>")
		for (indexX in aCatX.indices)
		{
			val categoryX = aCatX[indexX]
			val sbRow = StringBuilder("<td>${categoryX.replace("\"", "")}</td>")
			for (indexY in aCatY.indices)
			{
				var cell = mDataPivot["${indexX}_$indexY"] ?: ""
				if (cell.isNotEmpty())
					cell = cell.clearDecimals()
				sbRow.append("<td>$cell</td>")
			}
			sbBody.append("<tr>$sbRow</tr>")
		}
		sbBody.append("</tbody>")
		return Pair("<table id=\"idTableDataPivot\">$sbHead$sbBody</table>", aCatY.size)
	}

	fun generateDataTableTri(
		aRows: ArrayList<ArrayList<String>>,
		columns: ColumnQuery,
		aCatX: List<String>,
		aCatY: List<String>) : ArrayList<ArrayList<Any>>
	{
		val aDataTable = ArrayList<ArrayList<Any>>()
		for (aCells in aRows)
		{
			val valueX = "\"${aCells[0]}\""
			var valueY = "\"${aCells[1]}\""
			val value = aCells[2]

			val indexX = aCatX.indexOf(valueX)
			var indexY = aCatY.indexOf(valueY)

			if (indexY == -1)
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

				indexY = aCatY.indexOf(valueY)
			}

//			val aNewRow = arrayListOf(indexY, indexX, value.toDoubleNotNull())
			val aNewRow = arrayListOf<Any>(indexX, indexY, value.toDoubleNotNull())
			aDataTable.add(aNewRow)
		}
		return aDataTable
	}
}