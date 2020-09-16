package chata.can.chata_ai.pojo.explore

class ExploreQuery(
	val aItems: ArrayList<String>,
	val currentPage: Int,
	val totalPages: Int,
	val totalItems: Int,
	val pageSize: Int,
	val nextUrl: String,
	val previousUrl: String)