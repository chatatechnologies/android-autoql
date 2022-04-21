package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.RelatedQueriesTestRepository
import chata.can.chata_ai.retrofit.data.model.relatedQueries.RelatedQueryData

class GetRelatedQueryTestUseCase {
	private val repository = RelatedQueriesTestRepository()

	suspend fun getRelatedQueryTest(): RelatedQueryData? = repository.getRelatedQueryTest()
}