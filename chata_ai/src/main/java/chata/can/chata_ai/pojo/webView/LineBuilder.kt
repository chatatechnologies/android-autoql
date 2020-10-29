package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.clearDecimals

object LineBuilder
{
	fun generateDataChartLine(
		aMapData: LinkedHashMap<String, String>,
		aCatX: List<String>,
		aCatY: List<String>): ArrayList<String>
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
		return aChartLine
	}
}