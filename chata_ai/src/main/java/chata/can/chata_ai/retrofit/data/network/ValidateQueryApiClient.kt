package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.exploreQueries.ValidateQueryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ValidateQueryApiClient {
	@GET("query/validate")
	suspend fun validateQuery(
		@Header("Authorization") beaverToken: String,
		@Query("text") query: String,
		@Query("key") apiKey: String,
	): Response<ValidateQueryModel>
}