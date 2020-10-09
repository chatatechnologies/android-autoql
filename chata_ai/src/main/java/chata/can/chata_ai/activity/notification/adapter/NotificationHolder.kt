package chata.can.chata_ai.activity.notification.adapter

import android.view.View
import android.webkit.WebView
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

class NotificationHolder(itemView: View): Holder(itemView), NotificationContract
{
	private val rlParent = itemView.findViewById<View>(R.id.rlParent)
	private val ivTop = itemView.findViewById<View>(R.id.ivTop)
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
	private val tvBody = itemView.findViewById<TextView>(R.id.tvBody)
	private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)

	private val rlBottom = itemView.findViewById<View>(R.id.rlBottom)
	private val tvQuery = itemView.findViewById<TextView>(R.id.tvQuery)
	private val rlLoad = itemView.findViewById<View>(R.id.rlLoad)
	private val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
	private val wbQuery = itemView.findViewById<WebView>(R.id.wbQuery)

	private val presenter = RuleQueryPresenter(this)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		item?.let { notification ->
			if (notification is Notification)
			{
				setBottomVisibility(notification)
				ivTop?.setOnClickListener {
					notification.isVisible = rlBottom.visibility == View.GONE
					setBottomVisibility(notification)
					if (notification.isVisible)
					{
						presenter.getRuleQuery(notification.id)
					}
				}

				tvTitle.text = notification.title
				tvBody.text = notification.message
				tvDate.text = toDate(notification.createdAt)
				tvQuery.text = notification.query.capitalize(Locale.US)
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

	override fun showLoading()
	{
		rlLoad.visibility = View.VISIBLE
		tvContent.visibility = View.GONE
		wbQuery.visibility = View.GONE
	}

	override fun showText(text: String)
	{
		rlLoad.visibility = View.GONE
		tvContent.visibility = View.VISIBLE
		tvContent.text = text
	}

	private fun setBottomVisibility(notification: Notification)
	{
		rlBottom.visibility = if (notification.isVisible) View.VISIBLE else View.GONE
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
					val ordinal = getOrdinalDate(recordDate)
					val format = SimpleDateFormat("MMMM dd$ordinal, yyyy", Locale.US)
					format.format(recordDate) + " at $hour"
				}
			}
		}
		catch (ex: Exception) { "" }
	}

	private fun getOrdinalDate(date: Date): String
	{
		val calendar = Calendar.getInstance()
		calendar.time = date
		val day = calendar.get(Calendar.DATE)
		return if(!((day>10) && (day<19)))
		{
			when(day % 10)
			{
				1 -> "'st'"
				2 -> "'nd'"
				3 -> "'rd'"
				else -> "'th'"
			}
		}
		else
			"'th'"
	}
}