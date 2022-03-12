package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

data class NotificationResponseModel (
	@SerializedName("data")
	val data: NotificationDataModel,
	@SerializedName("message")
	val message: String
)

fun emptyNotification() = NotificationResponseModel(
	NotificationDataModel(
		ArrayList(),
		PaginationModel(0)
	),
	""
)