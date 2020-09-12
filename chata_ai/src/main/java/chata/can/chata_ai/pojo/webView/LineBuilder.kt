package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.clearDecimals

object LineBuilder
{
	fun generateDataChartLine(
		aMapData: LinkedHashMap<String, String>,//		aDataTable: ArrayList<ArrayList<Any>>,
		aCatX: ArrayList<String>,
		aCatY: ArrayList<String>): ArrayList<String>
	{
		val aChartLine = ArrayList<String>()

		for((index2, category) in aCatY.withIndex())
		{
			val aEachY = ArrayList<Double>()
			for (index1 in aCatX.indices)
			{
				aMapData["${index1}_$index2"]?.let {
					it.toDoubleOrNull()?.let { num -> aEachY.add(num) }
				} ?: run { aEachY.add(0.0) }
			}
			val sData = aEachY.joinTo(StringBuilder("["), postfix = "]", separator = ",") {
				"$it".clearDecimals() }
			val item = "{\"data\":$sData,\"name\":$category}"
			aChartLine.add(item)
		}
//		for ((index2, category) in aCatY.withIndex())
//		{
//			val aDataFilter = aMapData.filter { it[1] == index2 }
//			val aData = ArrayList<Double>()
//			for (value in aDataFilter)
//			{
//				(value[2] as? Double)?.let {
//					aData.add(it)
//				}
//			}
//			val sData = aData.joinTo(StringBuilder("["), postfix = "]", separator = ",") { "$it".clearDecimals() }
//			val item = "{\"data\":$sData,\"name\":$category}"
//			aChartLine.add(item)
//		}
		return aChartLine
	}
}