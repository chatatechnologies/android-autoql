package chata.can.chata_ai.retrofit

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.data.model.notification.NotificationModel
import chata.can.chata_ai.retrofit.data.model.ruleQuery.TypeEnum
import chata.can.chata_ai.retrofit.data.model.ruleQuery.TypeRuleQuery
import chata.can.chata_ai.retrofit.data.model.ruleQuery.emptyTypeRuleQuery
import java.text.SimpleDateFormat
import java.util.*

fun NotificationModel.notificationModelToEntity() = NotificationEntity(
		createdAt,
		id,
		message,
		title,
		query,
		state
	)

data class NotificationEntity (
	val createdAt: Int,
	val id: String,
	val message: String,
	val title: String,
	val query: String,
	val state: String
) {
	var isBottomVisible = false
	var isVisibleLoading = true
	var contentTextView: TypeRuleQuery = emptyTypeRuleQuery
	var contentWebView: TypeRuleQuery = emptyTypeRuleQuery

	fun setData(typeRuleQuery: TypeRuleQuery) {
		when(typeRuleQuery.typeEnum) {
			TypeEnum.TEXT -> contentTextView = typeRuleQuery
			TypeEnum.WEB -> contentWebView = typeRuleQuery
		}
	}

	fun createdAtFormatted(): String {
		return "\uD83D\uDCC5 ${toDate(createdAt)}"
	}

	fun isVisibleMessage() = message.isNotEmpty()

	fun hasData() = contentTextView.isNotEmpty() || contentWebView.isNotEmpty()

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

	fun isVisibleTextView() = contentTextView.isNotEmpty()

	fun isVisibleWebView() = contentWebView.isNotEmpty()
}