package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.notification.NotificationResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NotificationApiClient {
	@GET("data-alerts/notifications")
	suspend fun getNotifications(
		@Header("Authorization") beaverToken: String,
		@Query("key") key: String,
		@Query("offset") offset: Int,
		@Query("limit") limit: Int
	): Response<NotificationResponseModel>
}