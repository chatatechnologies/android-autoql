package chata.can.chata_ai.compose.model

data class NotificationResponse(
	val message: String,
	val reference_id: String,
	val data: DataNotification
)

fun emptyNotificationResponse() =
	NotificationResponse(message = "", reference_id = "", data = emptyDataNotification())

data class DataNotification(
	val items: List<ItemNotification> = listOf()
)

fun emptyDataNotification() = DataNotification(listOf())

data class ItemNotification(
	val created_at: Long,
	val data_alert_id: String,
	val data_alert_type: String,
	val data_return_query: String,
	val id: String,
	val message: String,
	val notification_type: String,
	val outcome: String,
	val state: String,
	val title: String
)