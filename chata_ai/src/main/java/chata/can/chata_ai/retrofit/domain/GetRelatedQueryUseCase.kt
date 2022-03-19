package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.RelatedQueriesRepository
import chata.can.chata_ai.retrofit.data.model.relatedQueries.RelatedQueryData

class GetRelatedQueryUseCase {
	private val repository = RelatedQueriesRepository()

	suspend fun getRelatedQuery(
		search: String,
    pageSize: Int,
    page: Int
	): RelatedQueryData = repository.getRelatedQuery(search, pageSize, page)
}