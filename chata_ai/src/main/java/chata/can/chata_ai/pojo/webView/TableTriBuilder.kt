package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.toDoubleNotNull
import chata.can.chata_ai.pojo.chat.ColumnQuery

object TableTriBuilder
{
	fun generateDataTableTri(
		aRows: ArrayList<ArrayList<String>>,
		columns: ColumnQuery,
		aCatX: List<String>,
		aCatY: List<String>) : ArrayList<ArrayList<Any>>
	{
		val aDataTable = ArrayList<ArrayList<Any>>()
		for (aCells in aRows)
		{
			//val text = row.replaceCommaSharp()
			//val aCells = ArrayList<String>(text.split(","))
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

			//val aNewRow = arrayListOf<Any>(indexX, indexY, value.toDoubleNotNull())
			val aNewRow = arrayListOf<Any>(indexY, indexX, value.toDoubleNotNull())
			aDataTable.add(aNewRow)
		}
		return aDataTable
	}
}