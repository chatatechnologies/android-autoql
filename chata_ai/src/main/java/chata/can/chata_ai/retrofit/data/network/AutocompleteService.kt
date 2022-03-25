package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData.apiKey
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.request.authentication.Authentication.bearerToken
import chata.can.chata_ai.retrofit.core.RetrofitHelperDynamic
import chata.can.chata_ai.retrofit.data.model.AutocompleteResponse
import chata.can.chata_ai.retrofit.data.model.emptyAutocompleteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AutocompleteService {
	private val retrofit = RetrofitHelperDynamic.getRetrofit()

	suspend fun getAutocomplete(text: String): AutocompleteResponse {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(AutocompleteApiClient::class.java)
					.getAutocomplete(
						beaverToken = bearerToken(),
						acceptLanguage = SinglentonDrawer.languageCode,
						content = text,
						key = apiKey
					)
				response.body() ?: emptyAutocompleteResponse()
			} catch (ex: Exception) {
				emptyAutocompleteResponse()
			}
		}
	}
}