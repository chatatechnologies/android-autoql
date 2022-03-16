package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.notification.NotificationDataModel
import chata.can.chata_ai.retrofit.data.network.NotificationService

class NotificationRepository {
	private val api = NotificationService()

	suspend fun getNotification(offset: Int = 0): NotificationDataModel {
		val response = api.getNotifications(offset)
		return response.data
	}
}