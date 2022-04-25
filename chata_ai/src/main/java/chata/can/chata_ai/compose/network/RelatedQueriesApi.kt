package chata.can.chata_ai.compose.network

import chata.can.chata_ai.compose.model.RelatedQueriesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RelatedQueriesApi {
	@GET("query/related-queries")
	suspend fun getRelatedQuery(
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Query("key") apiKey: String,
		@Query("search") search: String,
		@Query("page_size") pageSize: Int,
		@Query("page") page: Int
	): RelatedQueriesResponse
}