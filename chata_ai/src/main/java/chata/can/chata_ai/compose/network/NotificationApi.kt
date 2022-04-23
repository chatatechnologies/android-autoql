package chata.can.chata_ai.compose.network

import chata.can.chata_ai.compose.model.NotificationResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NotificationApi {
	@GET("data-alerts/notifications")
	suspend fun getNotifications(
		@Header("Authorization") beaverToken: String,
		@Query("key") key: String,
		@Query("offset") offset: Int,
		@Query("limit") limit: Int
	): NotificationResponse
}