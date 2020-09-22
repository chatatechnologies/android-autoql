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
	private val llParent = itemView.findViewById<View>(R.id.llParent)
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
		ViewCompat.setElevation(llParent, 12f)
	}

	private fun toDate(iDate: Int): String
	{
		return try {
			val format = SimpleDateFormat("hh:mm a", Locale.US)
			val date = Date(iDate * 1000L)
			return format.format(date)
		}
		catch (ex: Exception) { ""}
	}
}