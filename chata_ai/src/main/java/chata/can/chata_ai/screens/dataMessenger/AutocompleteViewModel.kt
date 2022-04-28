package chata.can.chata_ai.screens.dataMessenger

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.model.AutocompleteApiResponse
import chata.can.chata_ai.compose.repository.AutocompleteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutocompleteViewModel @Inject constructor(
	private val autocompleteRepository: AutocompleteRepository
) : ViewModel() {
	val autocompleteData: MutableState<DataOrException<AutocompleteApiResponse, Boolean, Exception>> =
		mutableStateOf(DataOrException(null, null, Exception("")))
	var loading: MutableState<Boolean> = mutableStateOf(false)

	fun autocomplete(text: String) {
		viewModelScope.launch {
			loading.value = true
			autocompleteData.value = autocompleteRepository.getAutocomplete(text = text)
			loading.value = false
		}
	}
}