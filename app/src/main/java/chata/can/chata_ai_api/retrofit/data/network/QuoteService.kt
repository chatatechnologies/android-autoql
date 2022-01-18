package chata.can.chata_ai_api.retrofit.data.network

import chata.can.chata_ai_api.retrofit.core.RetrofitHelper
import chata.can.chata_ai_api.retrofit.data.model.QuoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuoteService {
	private val retrofit = RetrofitHelper.getRetrofit()

	suspend fun getQuotes(): List<QuoteModel> {
		return withContext(Dispatchers.IO) {
			val response = retrofit.create(QuoteApiClient::class.java).getAllQuotes()
			response.body() ?: emptyList()
		}
	}
}