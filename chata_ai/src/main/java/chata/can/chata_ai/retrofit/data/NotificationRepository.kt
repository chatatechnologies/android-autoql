package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.NotificationModel
import chata.can.chata_ai.retrofit.data.network.NotificationService

class NotificationRepository {
	private val api = NotificationService()

	suspend fun getNotification(): List<NotificationModel> {
		val response = api.getNotifications()
		return response.data.items
	}
}