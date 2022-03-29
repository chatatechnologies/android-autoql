package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.query.QueryDashboardResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface QueryDashboardApiClient {
	@POST("query")
	suspend fun queryDashboard(
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Header("Content-Type") contentType: String,
		@Query("key") key: String
	): Response<QueryDashboardResponse>
}