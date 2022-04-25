package chata.can.chata_ai.compose.repository

import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.model.ValidateQueryResponse
import chata.can.chata_ai.compose.network.ValidateQueryApi
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication
import javax.inject.Inject

class ValidateQueryRepository @Inject constructor(private val api: ValidateQueryApi) {
	private val dataOrException = DataOrException<ValidateQueryResponse, Boolean, Exception>()

	suspend fun validateQuery(
		query: String
	): DataOrException<ValidateQueryResponse, Boolean, Exception> {
		try {
			dataOrException.loading = true
			dataOrException.data = api.validateQuery(
				beaverToken = Authentication.bearerToken(),
				query = query,
				apiKey = AutoQLData.apiKey
			)
			dataOrException.loading = false
		} catch (exception: Exception) {
			dataOrException.e = exception
		}
		return dataOrException
	}
}