package chata.can.chata_ai.retrofit.data.model.relatedQueries

import com.google.gson.annotations.SerializedName

class RelatedQueryModel(
	@SerializedName("data")
	val data: RelatedQueryData?,
	@SerializedName("message")
	val message: String,
	@SerializedName("reference_id")
	val referenceId: String
)

fun emptyRelatedModel() = RelatedQueryModel(
	data = emptyRelatedQueryData(),
	message = "",
	referenceId = ""
)

data class RelatedQueryData(
	@SerializedName("items")
	val items: List<String>,
	@SerializedName("pagination")
	val pagination: RelatedQueryPagination
)

fun emptyRelatedQueryData() = RelatedQueryData(
	items = listOf(),
	pagination = emptyRelatedQueryPagination()
)

data class RelatedQueryPagination(
	@SerializedName("total_items")
	val totalItems: Int,
	@SerializedName("next_url")
	val nextUrl: String,
	@SerializedName("previous_url")
	val previousUrl: String,
	@SerializedName("page_size")
	val pageSize: Int,
	@SerializedName("total_pages")
	val totalPages: Int,
	@SerializedName("current_page")
	val currentPage: Int
)

fun emptyRelatedQueryPagination() = RelatedQueryPagination(
	totalItems = 0,
	nextUrl = "",
	previousUrl = "",
	pageSize = 0,
	totalPages = 0,
	currentPage = 0
)