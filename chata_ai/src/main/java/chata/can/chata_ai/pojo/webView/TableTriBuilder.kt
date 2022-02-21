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
		for ((index1, cell1) in aCatX.withIndex())
		{
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

	data class DataPivot(
		val mDataPivot: LinkedHashMap<String, String>,
		val aColumn: ColumnQuery,
		val aCatX: List<String>,
		val aCatY: List<String>,
		val nameHeader: String,
		val invested: Boolean = false
	)

	fun buildDataPivot(dataPivot: DataPivot): Triple<String, Int, ArrayList<Int>> {
		with(dataPivot) {
			val innerCatX = if (invested) aCatY else aCatX
			val innerCatY = if (invested) aCatX else aCatY

			val sbHead = StringBuilder("<thead><tr><th>$nameHeader</th>")
			sbHead.append(innerCatX.joinTo(StringBuilder(""), separator = "") {
				"<th>${it.replace("\"", "")}</th>"
			})
			sbHead.append("</tr></thead>")

			val sbFoot = StringBuilder("<tfoot><tr><th>$nameHeader</th>")
			sbFoot.append(innerCatX.joinTo(StringBuilder(""), separator = "") {
				"<th>${it.replace("\"", "")}</th>"
			})
			sbFoot.append("</tr></tfoot>")

			val aIndexZero = ArrayList<Int>()
			val aRows = ArrayList<String>()

			for ((indexY, categoryY) in innerCatY.withIndex())
			{
				var onlyZero = true
				val sbRow = StringBuilder("<td>${categoryY.replace("\"", "")}</td>")
				for (indexX in innerCatX.indices)
				{
					val innerIndex = if (invested) "${indexX}_$indexY" else "${indexY}_$indexX"
					var cell = mDataPivot[innerIndex] ?: ""
					//region
					if (cell != "0.0" && cell.isNotEmpty())
						onlyZero = false
					//endregion
					if (cell.isNotEmpty())
						cell = cell.clearDecimals()
					cell = cell.formatWithColumn(aColumn)
					if (cell == "0") cell = ""
					sbRow.append("<td>$cell</td>")
				}
				if (onlyZero)
					aIndexZero.add(indexY)
				aRows.add("<tr>$sbRow</tr>")
			}
			//region reverse; check when is necessary
//		aRows.reverse()
			val sbBody = StringBuilder("<tbody>")
			for (row in aRows) sbBody.append(row)
			sbBody.append("</tbody>")
			val idPivot = if (invested) "idTableDataPivot2" else "idTableDataPivot"
			return Triple("<table id=\"$idPivot\">$sbHead$sbFoot$sbBody</table>", innerCatY.size, aIndexZero)
		}
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

	fun getData3Dimensions(
		mData: LinkedHashMap<String, String>,
		aCatX: ArrayList<String>,
		aCatY: ArrayList<String>,
		invested: Boolean = false): String
	{
		val aRow = ArrayList<String>()
		for ((posX, valueX) in aCatX.withIndex())
		{
			for ((posY, valueY) in aCatY.withIndex())
			{
				val position = if (invested) "${posY}_$posX" else "${posX}_$posY"
				val cell = mData[position] ?: run { "0.0" }
				val value = cell.toDoubleNotNull()
				val sRow = "{\'name\':\'${valueX}\',\'group\':\'${valueY}\',\'value\': ${value}}".replace("\"", "")
				aRow.add(sRow)
			}
		}
//		for (item in aDataValue)
//		{
//			val posX = item[0] as Int
//			val posY = item[1] as Int
//			val value = item[2] as Double
//			val valueX = aCatX[posX]
//			val valueY = aCatY[posY]
//			val sRow = "{\'name\':\'${valueX}\',\'group\':\'${valueY}\',\'value\': ${value}}".replace("\"", "")
//			aRow.add(sRow)
//		}
		return "$aRow"
	}
}