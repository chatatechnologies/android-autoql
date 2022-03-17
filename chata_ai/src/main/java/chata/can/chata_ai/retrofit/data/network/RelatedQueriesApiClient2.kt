package chata.can.chata_ai.retrofit.data.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RelatedQueriesApiClient2 {
	@GET("query/related-queries")
	fun getRelatedQuery(
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Query("key") apiKey: String,
		@Query("search") search: String,
		@Query("page_size") pageSize: Int,
		@Query("page") page: Int
	): Call<JsonObject>
}