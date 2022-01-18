package chata.can.chata_ai_api.retrofit.data.network

import chata.can.chata_ai_api.retrofit.data.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApiClient {
	@GET("/.json")
	suspend fun getAllQuotes(): Response<List<QuoteModel>>
}