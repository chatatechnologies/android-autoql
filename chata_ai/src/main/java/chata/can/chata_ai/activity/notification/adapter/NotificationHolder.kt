package chata.can.chata_ai.activity.notification.adapter

import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.notification.model.Notification
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
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
				val date = toDate(notification.createdAt)
				tvDate.text = parseDate(date)
			}
		}
	}

	override fun onPaint()
	{
		ViewCompat.setElevation(rlParent, 12f)
	}

	private fun toDate(iDate: Int): String
	{
		return try {
			val format = SimpleDateFormat("dd_hh:mma", Locale.US)
			val date = Date(iDate * 1000L)
			return format.format(date).toLowerCase(Locale.US)
		}
		catch (ex: Exception) { "" }
	}

	private fun parseDate(source: String): String
	{
		var out = ""
		val aDate = source.split("_")
		if (aDate.isNotEmpty())
		{
			val format = SimpleDateFormat("dd", Locale.US)
			val current = Date()
			val currentDay = format.format(current)
			val iDay = currentDay.toIntOrNull() ?: 0
			val iRecordDay = aDate[0].toIntOrNull() ?: 0

			val nameDay = when(iDay - iRecordDay)
			{
				0 -> "Today"
				1 -> "Yesterday"
				else -> ""
			}
			out = "$nameDay ${aDate[1]}"
		}
		return out
	}
}