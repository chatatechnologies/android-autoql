package chata.can.chata_ai.compose.network

import chata.can.chata_ai.compose.model.ValidateQueryResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ValidateQueryApi {
	@GET("query/validate")
	suspend fun validateQuery(
		@Header("Authorization") beaverToken: String,
		@Query("text") query: String,
		@Query("key") apiKey: String
	): ValidateQueryResponse
}