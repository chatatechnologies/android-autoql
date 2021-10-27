package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.clearDecimals

object LineBuilder
{
	fun generateDataChartLine(
		aMapData: LinkedHashMap<String, String>,
		aCatX: List<String>,
		aCatY: List<String>): Pair< Pair<*,*>, Pair<*,*> >
	{
		val aChartLine = ArrayList<String>()
		var max = 0.0
		for((indexY, category) in aCatY.withIndex())
		{
			var maxItem = 0.0
			val group = "name: '$category',"
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
		val aChartLine1 = ArrayList<String>()
		var max1 = 0.0
		for ((indexX, category) in aCatX.withIndex())
		{
			var maxItem = 0.0
			val group = "name: '$category',"
			val aByGroup = ArrayList<String>()
			for ((indexY, categoryY) in aCatY.withIndex())
			{
				val value = aMapData["${indexX}_$indexY"]?.let {
					it.toDoubleOrNull() ?: run { 0.0 }
				} ?: run { 0.0 }
				maxItem += value
				aByGroup.add("\'$categoryY\': ${"$value".clearDecimals()}")
			}
			if (max1 < maxItem) max1 = maxItem
			val item = "$group ${aByGroup.joinToString(",", "", "")}"
			aChartLine1.add(item)
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
		val stacked1 = aChartLine.joinToString(",\n", "[", "]") { "{$it}" }
			.replace("\"", "")
		val stacked2 = aChartLine1.joinToString(",\n", "[", "]") { "{$it}" }
			.replace("\"", "")
		val pStacked1 = Pair(stacked1, max.toInt())
		val pStacked2 = Pair(stacked2, max1.toInt())
		return Pair(pStacked1, pStacked2)
	}
}