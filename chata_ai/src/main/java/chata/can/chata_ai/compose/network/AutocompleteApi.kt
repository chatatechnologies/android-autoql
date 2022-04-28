package chata.can.chata_ai.compose.network

import chata.can.chata_ai.compose.model.AutocompleteApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AutocompleteApi {
	@GET("query/autocomplete")
	suspend fun getAutocomplete(
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Query("text") content: String,
		@Query("key") key: String
	): AutocompleteApiResponse
}