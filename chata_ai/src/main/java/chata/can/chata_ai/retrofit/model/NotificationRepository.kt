package chata.can.chata_ai.retrofit.model

import androidx.lifecycle.MutableLiveData
import chata.can.chata_ai.retrofit.NotificationEntity

interface NotificationRepository {
	fun getNotifications(): MutableLiveData<List<NotificationEntity>>
	fun callNotificationsAPI()
}