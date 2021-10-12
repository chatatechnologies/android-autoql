package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.clearDecimals

object LineBuilder
{
	fun generateDataChartLine(
		aMapData: LinkedHashMap<String, String>,
		aCatX: List<String>,
		aCatY: List<String>): Pair<String, Int>
	{
		val aChartLine = ArrayList<String>()
		var max = 0.0
		for((indexY, category) in aCatY.withIndex())
		{
			var maxItem = 0.0
			val group = "group: '$category',"
			val aByGroup = ArrayList<String>()
			for ((indexX, categoryX) in aCatX.withIndex())
			{
				val value = aMapData["${indexX}_$indexY"]?.let {
					it.toDoubleOrNull() ?: run { 0.0 }
				} ?: run { 0.0 }
				maxItem += value
				aByGroup.add("\'$categoryX\': ${"$value".clearDecimals()}")
			}
			if (max < maxItem) max = maxItem
			val item = "$group ${aByGroup.joinToString(",", "", "")}"
			aChartLine.add(item)
		}
//		val aChartLine = ArrayList<String>()
//		for((index1_, category) in aCatX.withIndex())
//		{
//			val aEachY = ArrayList<Double>()
//			for (index2 in aCatY.indices)
//			{
//				aMapData["${index1_}_$index2"]?.let {
//					it.toDoubleOrNull()?.let { num -> aEachY.add(num) }
//				} ?: run { aEachY.add(0.0) }
//			}
//			val sData = aEachY.joinTo(StringBuilder("["), postfix = "]", separator = ",") {
//				"$it".clearDecimals() }
//			val item = "{\"data\":$sData,\"name\":$category}"
//			aChartLine.add(item)
//		}
		val forD3 = aChartLine.joinToString(",\n", "[", "]") { "{$it}" }
			.replace("\"", "")
		return Pair(forD3, max.toInt())
	}
}