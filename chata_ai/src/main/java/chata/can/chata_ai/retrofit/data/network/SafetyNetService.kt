package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.compose.di.RetrofitHelperDynamic
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData.apiKey
import chata.can.chata_ai.request.authentication.Authentication.bearerToken
import chata.can.chata_ai.retrofit.data.model.SafetyNetResponse
import chata.can.chata_ai.retrofit.data.model.emptySafetyNetResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SafetyNetService {
	private val retrofit = RetrofitHelperDynamic()

	suspend fun getSafetyNet(text: String): SafetyNetResponse {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.getRetrofit().create(SafetyNetApiClient::class.java)
					.getSafetyNet(
						beaverToken = bearerToken(),
						acceptLanguage = SinglentonDrawer.languageCode,
						text = text,
						key = apiKey
					)
				response.body() ?: emptySafetyNetResponse()
			} catch (ex: Exception) {
				emptySafetyNetResponse()
			}
		}
	}
}