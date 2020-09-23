package chata.can.chata_ai.activity.notification.adapter

import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.notification.model.Notification
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import java.text.SimpleDateFormat
import java.util.*

class NotificationHolder(itemView: View): Holder(itemView)
{
	private val rlParent = itemView.findViewById<View>(R.id.rlParent)
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
	private val tvBody = itemView.findViewById<TextView>(R.id.tvBody)
	private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		item?.let { notification ->
			if (notification is Notification)
			{
				tvTitle.text = notification.title
				tvBody.text = notification.message
				tvDate.text = toDate(notification.createdAt)
			}
		}
	}

	override fun onPaint()
	{
		rlParent.run {
			context.run {
				val white = getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
				val gray = getParsedColor(ThemeColor.currentColor.drawerColorPrimary)
				rlParent.background =
					DrawableBuilder.setGradientDrawable(white,18f,0, gray)
			}
		}

		ViewCompat.setElevation(rlParent, 12f)
	}

	private fun toDate(iDate: Int): String
	{
		return try {
			val recordDate = Date(iDate * 1000L)
			val currentDate = Date()

			val formatHour = SimpleDateFormat("hh:mma", Locale.US)
			val hour = formatHour.format(recordDate).toLowerCase(Locale.US)

			when((currentDate.time - recordDate.time).toInt() / (1000 * 60 * 60 * 24))
			{
				0 -> "Today $hour"
				1 -> "Yesterday $hour"
				else ->
				{
					val format = SimpleDateFormat("MMMM dd,yyyy", Locale.US)
					format.format(recordDate) + " at $hour"
				}
			}
		}
		catch (ex: Exception) { "" }
	}
}