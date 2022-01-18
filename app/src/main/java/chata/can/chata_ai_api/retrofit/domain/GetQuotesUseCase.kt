package chata.can.chata_ai_api.retrofit.domain

import chata.can.chata_ai_api.retrofit.data.QuoteRepository
import chata.can.chata_ai_api.retrofit.data.model.QuoteModel

class GetQuotesUseCase {
	private val repository = QuoteRepository()

	suspend operator fun invoke(): List<QuoteModel> = repository.getAllQuotes()
}