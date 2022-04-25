package chata.can.chata_ai.screens.notification

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.model.RuleQueryResponse
import chata.can.chata_ai.compose.repository.RuleQueryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardNotificationViewModel @Inject constructor(private val repository: RuleQueryRepository) :
	ViewModel() {
	//repository for simple quest notification query
	val data: MutableState<DataOrException<RuleQueryResponse, Boolean, Exception>> =
		mutableStateOf(DataOrException(null, true, Exception("")))

	fun getRuleQuery(idRule: String) {
		viewModelScope.launch {
			data.value.loading = true
			data.value = repository.getRuleQuery(idRule = idRule)
			data.value.loading = false
		}
	}
}