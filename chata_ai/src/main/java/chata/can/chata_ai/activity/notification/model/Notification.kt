package chata.can.chata_ai.activity.notification.model

class Notification(
	val id: String,
	val ruleId: String,
	val title: String,
	val message: String,
	val query: String,
	val createdAt: Int
)
{
	var isVisible = false
}