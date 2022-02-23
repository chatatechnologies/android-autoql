package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.clearDecimals

object StackedArea {
	fun getStackedArea(
		mData: LinkedHashMap<String, String>,
		aCatX: List<String>,
		aCatY: List<String>
	){
		//region generate stacked 1
		val aChartLine = ArrayList<String>()
		for ((indexY, categoryY) in aCatY.withIndex()) {
			val category = "category: '${categoryY.clearQuotes()}',"

			val aByCategory = ArrayList<String>()
			for ((indexX, categoryX) in aCatX.withIndex()) {
				val value = mData["${indexY}_$indexX"]?.let {
					it.toDoubleOrNull() ?: run { 0.0 }
				} ?: run { 0.0 }
				aByCategory.add("${categoryX.categoryToProperty()}: ${"$value".clearDecimals()}")
			}

			val item = "$category ${aByCategory.joinToString(",", "", "")}"
			aChartLine.add(item)
		}
		//endregion
		val stacked1 = aChartLine.joinToString(", \n", "[", "]") { "{$it}" }
		stacked1
	}

	private fun String.clearQuotes() = this.replace("\"", "")
	private fun String.categoryToProperty(): String {
		return this.clearQuotes().replace(" ", "_")
	}
}