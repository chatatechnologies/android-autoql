package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

//region topic model
class TopicModel(
	@SerializedName("reference_id")
	val referenceId: String,
	@SerializedName("message")
	val message: String,
//	@SerializedName("data")
//	val data: Any
)

fun emptyTopicModel(): TopicModel = TopicModel(
	referenceId = "",
	message = "",
//	data = emptyTopicDataMode()
)
//endregion
//region topic data
class TopicDataModel(
	@SerializedName("pagination")
	val pagination: PaginationTopic,
	@SerializedName("items")
	val items: List<String>
)

fun emptyTopicDataMode(): TopicDataModel = TopicDataModel(
	pagination = emptyPaginationTopic(),
	items = listOf()
)
//endregion
//region pagination topic
class PaginationTopic(
	@SerializedName("next_url")
	val nextUrl: String,
	@SerializedName("current_page")
	val currentPage: Int,
	@SerializedName("previous_url")
	val previous_url: String = "",
	@SerializedName("total_pages")
	val total_pages: Int,
	@SerializedName("page_size")
	val pageSize: Int,
	@SerializedName("total_items")
	val totalItems: Int
)

fun emptyPaginationTopic(): PaginationTopic = PaginationTopic(
	nextUrl = "",
	currentPage = 0,
	previous_url = "",
	total_pages = 0,
	pageSize = 0,
	totalItems = 0
)
//endregion