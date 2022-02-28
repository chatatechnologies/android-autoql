package chata.can.chata_ai.retrofit.data.model

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

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

	fun createdAtFormatted(): String {
		return "\uD83D\uDCC5 ${toDate(createdAt)}"
	}

	fun bottomVisibility(): Int {
		return if (isVisible) View.VISIBLE else View.GONE
	}

	fun getDrawableLeftView(): GradientDrawable {
		val color = if (state == "DISMISSED") Color.RED else SinglentonDrawer.currentAccent

		return DrawableBuilder.setGradientDrawable(
			color,
			aCornerRadius = floatArrayOf(15f, 15f, 0f, 0f, 0f, 0f, 15f, 15f)
		)
	}

	private fun toDate(iDate: Int): String
	{
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
}