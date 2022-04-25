package chata.can.chata_ai.compose.model

data class RelatedQueriesResponse(
	val reference_id: String = "",
	val message: String = "",
	val data: RelatedQueriesData = RelatedQueriesData()
)

data class RelatedQueriesData(
	val pagination: RelatedQueriesPagination = RelatedQueriesPagination(),
	val items: List<String> = listOf()
)

data class RelatedQueriesPagination(
	val page_size: Int = 0,
	val total_items: Int = 0,
	val previous_url: String = "",
	val current_page: Int = 0,
	val next_url: String = "",
	val total_pages: Int = 0
)