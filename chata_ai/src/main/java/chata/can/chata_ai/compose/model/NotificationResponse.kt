package chata.can.chata_ai.compose.model

data class NotificationResponse(
	val message: String,
	val reference_id: String
)

fun emptyNotificationResponse() = NotificationResponse(message = "", reference_id = "")