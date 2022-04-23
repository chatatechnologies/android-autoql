package chata.can.chata_ai.compose.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.compose.model.NotificationResponse
import chata.can.chata_ai.compose.model.emptyNotificationResponse
import chata.can.chata_ai.compose.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val repository: NotificationRepository) :
	ViewModel() {
	var data: MutableState<NotificationResponse> = mutableStateOf(emptyNotificationResponse())

	init {
		getAllNotifications()
	}

	private fun getAllNotifications(offset: Int = 0) {
		viewModelScope.launch {
			data.value = repository.getAllNotification(offset)
		}
	}
}