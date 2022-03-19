package chata.can.chata_ai.retrofit.data.model

import chata.can.chata_ai.retrofit.data.model.relatedQueries.RelatedQueryPagination
import chata.can.chata_ai.retrofit.data.model.relatedQueries.emptyRelatedQueryPagination

object ExploreQueriesProvider {
	val itemList = mutableListOf<String>()
	var pagination: RelatedQueryPagination = emptyRelatedQueryPagination()
}