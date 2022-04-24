package chata.can.chata_ai.compose.model

import androidx.compose.ui.graphics.Color
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import java.text.SimpleDateFormat
import java.util.*

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
	val created_at: Int,
	val data_alert_id: String,
	val data_alert_type: String,
	val data_return_query: String,
	val id: String,
	val message: String,
	val notification_type: String,
	val outcome: String,
	val state: String,
	val title: String
) {
	private fun toDate(iDate: Int): String {
		return try {
			val recordDate = Date(iDate * 1000L)
			val currentDate = Date()

			val formatHour = SimpleDateFormat("hh:mma", Locale.US)
			val hour = formatHour.format(recordDate).lowercase(Locale.US)

			when ((currentDate.time - recordDate.time).toInt() / (1000 * 60 * 60 * 24)) {
				0 -> "Today $hour"
				1 -> "Yesterday $hour"
				else -> {
					val format = SimpleDateFormat("MMMM d°, yyyy", Locale.US)
					format.format(recordDate) + " at $hour"
				}
			}
		} catch (ex: Exception) {
			""
		}
	}

	fun getTextColorPrimary() = Color(ThemeColor.currentColor.pDrawerTextColorPrimary)

	fun getColorTint() = Color(
		if (state == "DISMISSED")
			ThemeColor.currentColor.pDrawerTextColorPrimary
		else
			SinglentonDrawer.currentAccent
	)

	fun createdAtFormatted(): String {
		return "\uD83D\uDCC5 ${toDate(created_at)}"
	}
}

fun emptyItemNotification() = ItemNotification(
	123,
	"data_alert_id",
	"data_alert_type",
	"data_return_query",
	"id",
	"message",
	"notification_type",
	"outcome",
	"state",
	"title"
)