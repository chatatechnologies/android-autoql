package chata.can.chata_ai.retrofit.data.model.html

import chata.can.chata_ai.extension.isNull
import chata.can.chata_ai.retrofit.core.formatValue
import chata.can.chata_ai.retrofit.data.model.column.TypeColumn

class Categories {
	fun indexCategoryEmpty(
		rows: MutableList<List<String>>,
		position: Int
	): ArrayList<Int> {
		val indices = ArrayList<Int>()
		rows.forEachIndexed { index, listRows ->
			val cellPosition = listRows[position]
			if (cellPosition.isNull() || cellPosition.isEmpty())
				indices.add(index)
		}
		return indices
	}

	fun buildCategoryByPosition(category: Category): ArrayList<String> {
		return ArrayList<String>().apply {
			category.run {
				for (index in rows.indices) {
					if (index in indicesIgnore) continue

					val row = rows[index]
					val cellPosition = row[position]
					if (cellPosition.isEmpty()) continue

					var parsed = if (isFormatted) {
						cellPosition.formatValue(column)
					} else cellPosition

					if (hasQuotes) {
						when(column.typeColumn) {
							TypeColumn.STRING, TypeColumn.DATE, TypeColumn.DATE_STRING -> {
								parsed = "\"$parsed\""
							}
							else -> {}
						}
					}

					if (allowRepeat) {
						this@apply.add(parsed)
					} else {
						if (parsed !in this@apply) {
							this@apply.add(parsed)
						}
					}

				}
			}
		}
	}

	fun makeCategories(categorieX: ArrayList<String>): String {
		val sb = StringBuilder("[").apply {
			categorieX.forEach { category ->
				if (categorieX.isNotEmpty()) {
					append(categorieX)
				}
			}
		}
		return "${sb.removeSuffix(", ")}]"
	}
}