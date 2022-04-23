package chata.can.chata_ai.compose.repository

import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.model.NotificationResponse
import chata.can.chata_ai.compose.network.NotificationApi
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val api: NotificationApi) {
	private val dataOrException = DataOrException<NotificationResponse, Boolean, Exception>()

	suspend fun getAllNotification(
		offset: Int = 0
	): DataOrException<NotificationResponse, Boolean, Exception> {
		try {
			dataOrException.loading = true
			dataOrException.data = api.getNotifications(
				beaverToken = Authentication.bearerToken(),
				key = AutoQLData.apiKey,
				offset = offset,
				limit = 10
			)
			dataOrException.loading = false
		} catch (exception: Exception) {
			dataOrException.e = exception
		}
		return dataOrException
	}
}