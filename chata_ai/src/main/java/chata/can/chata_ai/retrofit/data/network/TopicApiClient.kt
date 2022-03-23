package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.TopicResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.Response

interface TopicApiClient {
	@GET("topics")
	suspend fun getTopics(
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Header("Integrator-Domain") integratorDomain: String,
		@Query("key") key: String,
		@Query("project_id") projectId: String
	): Response<TopicResponse>
}