package chata.can.chata_ai.retrofit.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.Response

interface JwtApiClient {
	@GET("jwt")
	suspend fun getJWT(
		@Header("Authorization") beaverToken: String,
		@Query("display_name") displayName: String,
		@Query("project_id") projectID: String
	): Response<String>
}