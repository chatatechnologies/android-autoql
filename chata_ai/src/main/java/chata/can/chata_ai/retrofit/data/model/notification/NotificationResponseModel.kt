package chata.can.chata_ai.retrofit.data.model.notification

import com.google.gson.annotations.SerializedName

//region NotificationResponseModel
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
//endregion

//region NotificationDataModel
data class NotificationDataModel(
	@SerializedName("items")
	val items: List<NotificationModel>,
	@SerializedName("pagination")
	val pagination: PaginationModel
)
//endregion

//region PaginationModel
data class PaginationModel(
	@SerializedName("total_items")
	val totalItems: Int
)
//endregion