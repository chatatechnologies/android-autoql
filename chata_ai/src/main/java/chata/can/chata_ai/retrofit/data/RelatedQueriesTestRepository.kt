package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.relatedQueries.RelatedQueryData
import chata.can.chata_ai.retrofit.data.network.RelatedQueriesTestService

class RelatedQueriesTestRepository {
	private val api = RelatedQueriesTestService()

	suspend fun getRelatedQueryTest(): RelatedQueryData? {
		val response = api.getRelatedQuery(search = "test")
		return response.data
	}
}