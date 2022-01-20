package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

data class PaginationModel(
	@SerializedName("total_items")
	val totalItems: Int
)