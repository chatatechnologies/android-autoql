package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

data class NotificationDataModel(
	@SerializedName("items")
	val items: List<NotificationModel>,
	@SerializedName("pagination")
	val pagination: PaginationModel
)