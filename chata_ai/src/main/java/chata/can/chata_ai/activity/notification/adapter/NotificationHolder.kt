package chata.can.chata_ai.activity.notification.adapter

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.notification.model.Notification
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener

class NotificationHolder(itemView: View): Holder(itemView)
{
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
				tvDate.text = "${notification.createdAt}"
			}
		}
	}

	override fun onPaint()
	{

	}
}