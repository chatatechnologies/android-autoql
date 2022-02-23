package chata.can.chata_ai.pojo.webView

object StackedArea {
	fun getStackedArea(
		mData: LinkedHashMap<String, String>,
		aCatX: List<String>,
		aCatY: List<String>
	): Pair<String, String>
	{
		//region generate stacked 1
		val aChartLine = aCatY.mapIndexed { indexY, categoryY ->
			val category = "category: '${categoryY.clearQuotes()}',"

			val aByCategory = aCatX.mapIndexed { indexX, categoryX ->
				val value = mData["${indexY}_$indexX"]?.let {
					it.toDoubleOrNull() ?: run { 0.0 }
				} ?: run { 0.0 }
				"${categoryX.categoryToProperty()}: ${"$value"}"
			}
			"$category ${aByCategory.joinToString(", ", "", "")}"
		}
		//endregion
		//region generate stacked 2
		val aChartLine2 = aCatX.mapIndexed { indexX, categoryX ->
			val category = "category: '${categoryX.clearQuotes()}',"

			val aByCategory = aCatY.mapIndexed { indexY, categoryY ->
				val value = mData["${indexY}_$indexX"]?.let {
					it.toDoubleOrNull() ?: run { 0.0 }
				} ?: run { 0.0 }
				"${categoryY.categoryToProperty()}: ${"$value"}"
			}
			"$category ${aByCategory.joinToString(", ", "", "")}"
		}
		//endregion
		val data1 = aChartLine.joinToString(", \n", "[", "]") { "{$it}" }
		val data2 = aChartLine2.joinToString(", \n", "[", "]") { "{$it}" }
		return Pair(data1, data2)
	}

	private fun String.clearQuotes() = this.replace("\"", "")
	private fun String.categoryToProperty(): String {
		return this.clearQuotes().replace(" ", "_")
	}
}