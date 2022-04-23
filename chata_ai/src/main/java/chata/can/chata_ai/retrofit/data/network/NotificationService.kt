package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.compose.di.RetrofitHelperDynamic
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication.bearerToken
import chata.can.chata_ai.retrofit.data.model.notification.NotificationResponseModel
import chata.can.chata_ai.retrofit.data.model.notification.emptyNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationService {
	private val retrofit = RetrofitHelperDynamic()

	suspend fun getNotifications(offset: Int = 0): NotificationResponseModel {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.getRetrofit().create(NotificationApiClient::class.java)
					.getNotifications(
						bearerToken(),
						AutoQLData.apiKey,
						offset,
						limit = 10)
				response.body() ?: emptyNotification()
			} catch (ex: Exception) {
				emptyNotification()
			}
		}
	}
}