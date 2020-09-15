package chata.can.chata_ai.activity.exploreQueries

data class RelatedQuery(
	val aItems: ArrayList<String>,
	val currentPage: Int,
	val totalPages: Int,
	val totalItems: Int
)