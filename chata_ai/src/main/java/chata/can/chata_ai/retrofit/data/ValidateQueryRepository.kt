package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.exploreQueries.ValidateQueryData
import chata.can.chata_ai.retrofit.data.model.exploreQueries.emptyValidateQueryData
import chata.can.chata_ai.retrofit.data.network.ValidateQueryService

class ValidateQueryRepository {
	private val api = ValidateQueryService()

	suspend fun validateQuery(query: String): ValidateQueryData {
		val response = api.getValidateQuery(query)
		return response.data ?: emptyValidateQueryData()
	}
}