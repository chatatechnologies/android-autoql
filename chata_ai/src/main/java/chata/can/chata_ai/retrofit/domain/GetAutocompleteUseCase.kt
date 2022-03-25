package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.AutocompleteRepository
import chata.can.chata_ai.retrofit.data.model.AutocompleteResponse

class GetAutocompleteUseCase {
	private val repository = AutocompleteRepository()

	suspend fun getAutocomplete(text: String): AutocompleteResponse {
		return repository.getAutocomplete(text)
	}
}