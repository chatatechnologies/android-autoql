package chata.can.chata_ai.retrofit.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.retrofit.data.model.NotificationModel
import chata.can.chata_ai.retrofit.domain.GetNotificationUseCase
import kotlinx.coroutines.launch

class NotificationViewModel: ViewModel() {
	val notificationList = MutableLiveData<List<NotificationModel>>()

	var getNotificationUseCase = GetNotificationUseCase()

	fun onCreate() {
		viewModelScope.launch {
			val result = getNotificationUseCase()
			notificationList.postValue(result)
		}
	}
}