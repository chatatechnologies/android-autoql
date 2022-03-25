package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.SafetyNetResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SafetyNetApiClient {
	@GET("query/validate")
	suspend fun getSafetyNet(
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Query("text") text: String,
		@Query("key") key: String
	): Response<SafetyNetResponse>
}