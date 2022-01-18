package chata.can.chata_ai_api.retrofit.data

import chata.can.chata_ai_api.retrofit.data.model.QuoteModel
import chata.can.chata_ai_api.retrofit.data.model.QuoteProvider
import chata.can.chata_ai_api.retrofit.data.network.QuoteService

class QuoteRepository {
	private val api = QuoteService()

	suspend fun getAllQuotes(): List<QuoteModel> {
		val response = api.getQuotes()
		QuoteProvider.quotes = response
		return response
	}
}