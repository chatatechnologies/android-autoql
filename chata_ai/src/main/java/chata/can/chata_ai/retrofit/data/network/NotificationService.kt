package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.core.RetrofitHelperDynamic
import chata.can.chata_ai.retrofit.data.model.notification.NotificationResponseModel
import chata.can.chata_ai.retrofit.data.model.notification.emptyNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationService {
	private val retrofit = RetrofitHelperDynamic.getRetrofit()

	suspend fun getNotifications(offset: Int = 0, limit: Int = 10): NotificationResponseModel {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(NotificationApiClient::class.java)
					.getNotifications(
						"Bearer ${AutoQLData.JWT}",
						AutoQLData.apiKey,
						offset, limit)
				response.body() ?: emptyNotification()
			} catch (ex: Exception) {
				emptyNotification()
			}
		}
	}
}