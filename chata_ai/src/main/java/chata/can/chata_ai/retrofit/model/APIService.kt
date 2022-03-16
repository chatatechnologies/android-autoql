package chata.can.chata_ai.retrofit.model

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface APIService {
	@GET("data-alerts/notifications")
	fun getNotifications(
		@Header("Authorization") beaverToken: String,
		@Query("key") key: String,
		@Query("offset") offset: Int,
		@Query("limit") limit: Int
	): Call<JsonObject>
}