package chata.can.chata_ai_api.retrofit.data.model

import com.google.gson.annotations.SerializedName

data class QuoteModel(
	@SerializedName("quote")
	val quote: String,
	@SerializedName("author")
	val author: String
)