package chata.can.chata_ai.retrofit

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.data.model.notification.NotificationModel
import java.text.SimpleDateFormat
import java.util.*

fun NotificationModel.notificationModelToEntity() {
	NotificationEntity(
		createdAt,
		id,
		message,
		title,
		query,
		state
	)
}

data class NotificationEntity (
	val createdAt: Int,
	val id: String,
	val message: String,
	val title: String,
	val query: String,
	val state: String
) {
	var isVisible = true
	var notContent = true
	var contentWebView = ""

	fun createdAtFormatted(): String {
		return "\uD83D\uDCC5 ${toDate(createdAt)}"
	}

	fun bottomVisibility(): Int {
		return if (isVisible) View.VISIBLE else View.GONE
	}

	fun isVisibleMessage() = if (message.isNotEmpty())
		View.VISIBLE
	else
		View.GONE

	fun getColorTitle() = if (state == "DISMISSED")
		ThemeColor.currentColor.pDrawerTextColorPrimary
	else
		SinglentonDrawer.currentAccent

	fun getDrawableLeftView(): GradientDrawable {
		val color = if (state == "DISMISSED") Color.RED else SinglentonDrawer.currentAccent

		return DrawableBuilder.setGradientDrawable(
			color,
			aCornerRadius = floatArrayOf(15f, 15f, 0f, 0f, 0f, 0f, 15f, 15f)
		)
	}

	private fun toDate(iDate: Int): String {
		return try {
			val recordDate = Date(iDate * 1000L)
			val currentDate = Date()

			val formatHour = SimpleDateFormat("hh:mma", Locale.US)
			val hour = formatHour.format(recordDate).lowercase(Locale.US)

			when((currentDate.time - recordDate.time).toInt() / (1000 * 60 * 60 * 24))
			{
				0 -> "Today $hour"
				1 -> "Yesterday $hour"
				else ->
				{
					val format = SimpleDateFormat("MMMM d°, yyyy", Locale.US)
					format.format(recordDate) + " at $hour"
				}
			}
		}
		catch (ex: Exception) { "" }
	}

	fun isVisibleLoading(): Int {
		println("VALUE FOR NOT CONTENT: $notContent")
		return if (notContent)
			View.VISIBLE
		else
			View.GONE
	}

	fun isVisibleWebView(): Int = if(contentWebView.isNotEmpty()) View.VISIBLE else View.GONE
}