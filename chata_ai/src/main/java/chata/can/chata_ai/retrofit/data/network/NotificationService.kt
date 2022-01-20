package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.core.RetrofitHelperDynamic
import chata.can.chata_ai.retrofit.data.model.NotificationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationService {
	private val retrofit = RetrofitHelperDynamic.getRetrofit()

	suspend fun getNotifications(): List<NotificationModel> {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(NotificationApiClient::class.java)
					.getNotifications("", "", "")
				response.body() ?: emptyList()
			} catch (ex: Exception) {
				emptyList()
			}
		}
	}
}