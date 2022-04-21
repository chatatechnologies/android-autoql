package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

//region topic response
class TopicResponse(
	@SerializedName("items")
	val items: List<ItemTopicResponse>?
)

fun emptyTopicResponse(): TopicResponse = TopicResponse(
	items = listOf()
)

class ItemTopicResponse(
	@SerializedName("topic")
	val topic: String,
	@SerializedName("queries")
	val queries: List<String>
)

fun emptyItemTopicResponse() = ItemTopicResponse(
	topic = "",
	queries = listOf()
)
//endregion