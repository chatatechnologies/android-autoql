package chata.can.chata_ai.fragment.exploreQueries

data class RelatedQuery(
	val aItems: ArrayList<String>,
	val currentPage: Int,
	val totalPages: Int,
	val totalItems: Int
)