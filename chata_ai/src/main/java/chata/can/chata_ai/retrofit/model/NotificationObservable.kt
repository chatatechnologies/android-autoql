package chata.can.chata_ai.retrofit.model

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import chata.can.chata_ai.retrofit.NotificationEntity

class NotificationObservable: BaseObservable() {

	private var notificationRepository: NotificationRepository = NotificationRepositoryImpl()

	fun callNotifications() {
		notificationRepository.callNotificationsAPI()
	}

	fun getNotifications(): MutableLiveData<List<NotificationEntity>> {
		return notificationRepository.getNotifications()
	}
}