package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.model.ValidateQueryResponse
import chata.can.chata_ai.compose.repository.ValidateQueryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreQueriesViewModel @Inject constructor(private val repository: ValidateQueryRepository) :
	ViewModel() {

	val data: MutableState<DataOrException<ValidateQueryResponse, Boolean, Exception>> =
		mutableStateOf(DataOrException(null, true, Exception("")))

	fun validateQuery(query: String) {
		viewModelScope.launch {
			data.value.loading = true
			data.value = repository.validateQuery(query = query)
			data.value.loading = false
		}
	}
}