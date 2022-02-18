package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.clearDecimals

object LineBuilder
{
	fun generateDataChartLine(
		aMapData: LinkedHashMap<String, String>,
		aCatX: List<String>,
		aCatY: List<String>): Pair< Pair<*,*>, Pair<*,*> >
	{
		//region generate stacked 1
		val aChartLine = ArrayList<String>()
		var max = 0.0
		for((indexY, category) in aCatY.withIndex())
		{
			var maxItem = 0.0
			val group = "name: '$category',"
			val aByGroup = ArrayList<String>()
			for ((indexX, categoryX) in aCatX.withIndex())
			{
				val value = aMapData["${indexY}_$indexX"]?.let {
					it.toDoubleOrNull() ?: run { 0.0 }
				} ?: run { 0.0 }
				maxItem += value
				aByGroup.add("\'$categoryX\': ${"$value".clearDecimals()}")
			}
			if (max < maxItem) max = maxItem
			val item = "$group ${aByGroup.joinToString(",", "", "")}"
			aChartLine.add(item)
		}
		//endregion
		//region generate stacked 2
		val aChartLine1 = ArrayList<String>()
		var max1 = 0.0
		for ((indexX, category) in aCatX.withIndex())
		{
			var maxItem = 0.0
			val group = "name: '$category',"
			val aByGroup = ArrayList<String>()
			for ((indexY, categoryY) in aCatY.withIndex())
			{
				val value = aMapData["${indexY}_$indexX"]?.let {
					it.toDoubleOrNull() ?: run { 0.0 }
				} ?: run { 0.0 }
				maxItem += value
				aByGroup.add("\'$categoryY\': ${"$value".clearDecimals()}")
			}
			if (max1 < maxItem) max1 = maxItem
			val item = "$group ${aByGroup.joinToString(",", "", "")}"
			aChartLine1.add(item)
		}
		//endregion
		val stacked1 = aChartLine.joinToString(",\n", "[", "]") { "{$it}" }
			.replace("\"", "")
		val stacked2 = aChartLine1.joinToString(",\n", "[", "]") { "{$it}" }
			.replace("\"", "")
		val pStacked1 = Pair(stacked1, max.toInt())
		val pStacked2 = Pair(stacked2, max1.toInt())
		return Pair(pStacked1, pStacked2)
	}
}