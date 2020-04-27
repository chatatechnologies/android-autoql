package chata.can.chata_ai.pojo.dashboard

data class Dashboard(
	val displayType: String,
	val h: Int,
	val i: String,
	val isNewTile : Boolean,
	val key: String,
	val maxH: Int,
	val minH: Int,
	val minW: Int,
	val moved: Boolean,
	val query: String,
	val splitView: Boolean,
	val static: Boolean,
	val title: String,
	val w: Int,
	val x: Int,
	val y: Int)