package chata.can.chata_ai.fragment.notification.model

class Notification(
	val id: String,
	val ruleId: String,
	val title: String,
	val message: String,
	val query: String,
	val createdAt: Int,
	val state: String
)
{
	var isVisible = false
}