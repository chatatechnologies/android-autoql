package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.clearDecimals

object LineBuilder
{
	fun generateDataChartLine(
		aDataTable: ArrayList<ArrayList<Any>>,
		aCatX: ArrayList<String>,
		aCatY: ArrayList<String>): ArrayList<String>
	{
		for((index2, catY) in aCatY.withIndex())
		{
			for ((index1, catX) in aCatX.withIndex())
			{
				aDataTable[index1]
			}
		}

		val aChartLine = ArrayList<String>()
		for ((index2, category) in aCatY.withIndex())
		{
			val aDataFilter = aDataTable.filter { it[1] == index2 }
			val aData = ArrayList<Double>()
			for (value in aDataFilter)
			{
				(value[2] as? Double)?.let {
					aData.add(it)
				}
			}
			val sData = aData.joinTo(StringBuilder("["), postfix = "]", separator = ",") { "$it".clearDecimals() }
			val item = "{\"data\":$sData,\"name\":$category}"
			aChartLine.add(item)
		}
		return aChartLine
	}
}