package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.AutocompleteResponse
import chata.can.chata_ai.retrofit.data.network.AutocompleteService

class AutocompleteRepository {
	private val api = AutocompleteService()

	suspend fun getAutocomplete(text: String): AutocompleteResponse {
		return api.getAutocomplete(text)
	}
}