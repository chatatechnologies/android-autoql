package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.NotificationModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationApiClient {
	@GET("data-alerts/notifications")
	suspend fun getNotifications(
		@Query("key") key: String,
		@Query("offset") offset: String,
		@Query("limit") limit: String
	): Response<List<NotificationModel>>
}