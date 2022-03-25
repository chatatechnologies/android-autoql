package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.AutocompleteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AutocompleteApiClient {
	@GET("query/autocomplete")
	suspend fun getAutocomplete(
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Query("text") content: String,
		@Query("key") key: String
	): Response<AutocompleteResponse>
}