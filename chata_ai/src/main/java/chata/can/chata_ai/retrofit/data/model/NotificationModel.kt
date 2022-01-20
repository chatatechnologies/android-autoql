package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

data class NotificationModel (
	@SerializedName("created_at")
	val createdAt: Int,
	@SerializedName("id")
	val id: String,
	@SerializedName("message")
	val message: String,
	@SerializedName("title")
	val title: String,
	@SerializedName("data_return_query")
	val query: String,
	@SerializedName("state")
	val state: String
) {
	var isVisible = false
}