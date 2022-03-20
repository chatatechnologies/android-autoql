package chata.can.chata_ai.retrofit.data.model

import chata.can.chata_ai.retrofit.data.model.relatedQueries.RelatedQueryPagination
import chata.can.chata_ai.retrofit.data.model.relatedQueries.emptyRelatedQueryPagination

object ExploreQueriesProvider {
	var textToSearchAnimated = false
	val itemList = mutableListOf<String>()
	var lastQuery: String = ""
	var pagination: RelatedQueryPagination = emptyRelatedQueryPagination()
}