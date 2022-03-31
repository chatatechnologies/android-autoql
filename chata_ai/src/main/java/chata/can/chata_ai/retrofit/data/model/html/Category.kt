package chata.can.chata_ai.retrofit.data.model.html

import chata.can.chata_ai.retrofit.ColumnEntity

class Category(
	val rows: MutableList<List<String>>,
	val column: ColumnEntity,
	val position: Int,
	val isFormatted: Boolean,
	val hasQuotes: Boolean,
	val allowRepeat: Boolean,
	val indicesIgnore: ArrayList<Int> = arrayListOf()
)