package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.compose.di.RetrofitHelperDynamic
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication.bearerToken
import chata.can.chata_ai.retrofit.data.model.exploreQueries.ValidateQueryModel
import chata.can.chata_ai.retrofit.data.model.exploreQueries.emptyValidateQueryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateQueryService {
	private val retrofit = RetrofitHelperDynamic()

	suspend fun getValidateQuery(query: String): ValidateQueryModel {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.getRetrofit().create(ValidateQueryApiClient::class.java)
					.validateQuery(
						beaverToken = bearerToken(),
						query = query,
						apiKey = AutoQLData.apiKey
					)
				response.body() ?: emptyValidateQueryModel()
			} catch (ex: Exception) {
				emptyValidateQueryModel()
			}
		}
	}
}