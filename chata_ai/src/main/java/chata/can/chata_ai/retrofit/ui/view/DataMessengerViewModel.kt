package chata.can.chata_ai.retrofit.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.retrofit.domain.GetAutocompleteUseCase
import kotlinx.coroutines.launch

class DataMessengerViewModel: ViewModel() {

	private val getAutocompleteUseCase = GetAutocompleteUseCase()

	fun getAutocomplete(text: String) {
		viewModelScope.launch {
			val autocompleteResponse = getAutocompleteUseCase.getAutocomplete(text)
			autocompleteResponse
		}
	}
}