package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.core.RetrofitHelperDynamic
import chata.can.chata_ai.retrofit.data.model.exploreQueries.ValidateQueryModel
import chata.can.chata_ai.retrofit.data.model.exploreQueries.emptyValidateQueryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateQueryService {
	private val retrofit = RetrofitHelperDynamic.getRetrofit()

	suspend fun getValidateQuery(query: String): ValidateQueryModel {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(ValidateQueryApiClient::class.java)
					.validateQuery(
						beaverToken = "Bearer ${AutoQLData.JWT}",
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