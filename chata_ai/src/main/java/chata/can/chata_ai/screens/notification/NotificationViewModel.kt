package chata.can.chata_ai.screens.notification

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.model.ItemNotification
import chata.can.chata_ai.compose.model.NotificationResponse
import chata.can.chata_ai.compose.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val repository: NotificationRepository) :
	ViewModel() {
	val data: MutableState<DataOrException<NotificationResponse, Boolean, Exception>> =
		mutableStateOf(DataOrException(null, true, Exception("")))
	var item = mutableListOf<ItemNotification>()
	var offset by mutableStateOf(0)

	init {
		getAllNotifications(offset = offset)
	}

	fun getAllNotifications(offset: Int = 0) {
		viewModelScope.launch {
			data.value.loading = true
			data.value = repository.getAllNotification(offset * 10)

			data.value.data?.data?.items?.let {
				item.addAll(it)
				println("item counts ${item.size}")
			}

			data.value.loading = false
		}
	}
}