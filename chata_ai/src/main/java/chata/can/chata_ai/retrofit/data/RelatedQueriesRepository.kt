package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.relatedQueries.RelatedQueryData
import chata.can.chata_ai.retrofit.data.model.relatedQueries.emptyRelatedQueryData
import chata.can.chata_ai.retrofit.data.network.RelatedQueriesService

class RelatedQueriesRepository {
	private val api = RelatedQueriesService()

	suspend fun getRelatedQuery(search: String)/*: RelatedQueryData*/ {
		val response = api.getRelatedQuery(search)
		response.toString()
//		response.message
//		return /*response.data ?:*/ emptyRelatedQueryData()
	}
}