package chata.can.chata_ai.compose.repository

import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.model.AutocompleteApiResponse
import chata.can.chata_ai.compose.network.AutocompleteApi
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication
import javax.inject.Inject

class AutocompleteRepository @Inject constructor(private val api: AutocompleteApi) {
	private val dataOrException = DataOrException<AutocompleteApiResponse, Boolean, Exception>()

	suspend fun getAutocomplete(
		text: String
	): DataOrException<AutocompleteApiResponse, Boolean, Exception> {
		try {
			dataOrException.loading = true
			api.getAutocomplete(
				beaverToken = Authentication.bearerToken(),
				acceptLanguage = SinglentonDrawer.languageCode,
				content = text,
				key = AutoQLData.apiKey
			)
			dataOrException.loading = false
		} catch (exception: Exception) {
			dataOrException.e = exception
		}
		return dataOrException
	}
}