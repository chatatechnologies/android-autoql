package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.pojo.chat.ColumnQuery

class Category(
	val aRows: ArrayList<ArrayList<String>>,
	val column: ColumnQuery,
	val position: Int,
	val isFormatted: Boolean,
	val hasQuotes: Boolean,
	val allowRepeat: Boolean,
	val aIndicesIgnore: ArrayList<Int> = arrayListOf())