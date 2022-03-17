package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.relatedQueries.RelatedQueryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RelatedQueriesApiClient {
	@GET("query/related-queries")
	fun getRelatedQuery(
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Query("key") apiKey: String,
		@Query("search") search: String,
		@Query("page_size") pageSize: Int,
		@Query("page") page: Int
	): Response<RelatedQueryModel>
}