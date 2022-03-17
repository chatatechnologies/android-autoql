package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.ValidateQueryRepository
import chata.can.chata_ai.retrofit.data.model.exploreQueries.ValidateQueryData

class GetValidateQueryUseCase {
	private val repository = ValidateQueryRepository()

	suspend fun validateQuery(query: String): ValidateQueryData = repository.validateQuery(query)
}