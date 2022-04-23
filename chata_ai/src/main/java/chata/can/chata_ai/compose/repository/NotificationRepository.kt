package chata.can.chata_ai.compose.repository

import chata.can.chata_ai.compose.model.NotificationResponse
import chata.can.chata_ai.compose.model.emptyNotificationResponse
import chata.can.chata_ai.compose.network.NotificationApi
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val api: NotificationApi) {
	private var notificationResponse = emptyNotificationResponse()

	suspend fun getAllNotification(offset: Int = 0): NotificationResponse {
		try {
			notificationResponse =
				api.getNotifications(
					beaverToken = Authentication.bearerToken(),
					key = AutoQLData.apiKey,
					offset = offset,
					limit = 10
				)
		} catch (ex: Exception) {
			ex.localizedMessage
		}
		return notificationResponse
	}
}