package chata.can.chata_ai.compose.repository

import chata.can.chata_ai.compose.model.NotificationResponse
import chata.can.chata_ai.compose.model.emptyNotificationResponse
import chata.can.chata_ai.compose.network.NotificationApi
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val api: NotificationApi) {
	private var notificationResponse = emptyNotificationResponse()

	suspend fun getAllNotification(offset: Int = 0): NotificationResponse {
		notificationResponse = api.getNotifications(beaverToken = "", key = "", offset = 1, limit = 1)
		return notificationResponse
	}
}