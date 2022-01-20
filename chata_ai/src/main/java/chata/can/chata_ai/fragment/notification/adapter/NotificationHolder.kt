package chata.can.chata_ai.fragment.notification.adapter

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.core.view.ViewCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.textSize
import chata.can.chata_ai.fragment.notification.model.Notification
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.data.model.NotificationModel
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import java.text.SimpleDateFormat
import java.util.*

class NotificationHolder(
	itemView: View,
	private val view: chata.can.chata_ai.fragment.notification.NotificationContract
): Holder(itemView), NotificationContract
{
	private val rlParent = itemView.findViewById<View>(R.id.rlParent)
	private val iView = itemView.findViewById<View>(R.id.iView)
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

	private var white = 0
	private var gray = 0
	private var blue = 0

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		item?.let { notification ->
			if (notification is NotificationModel)
			{
				setBottomVisibility(notification)
				ivTop?.setOnClickListener {
					notification.isVisible = rlBottom.visibility == View.GONE
					setBottomVisibility(notification)
					if (notification.isVisible)
						presenter.getRuleQuery(notification.id)
					else
						iView.layoutParams = getRelativeLayoutParams(
							iView.context.dpToPx(4f), ivTop.measuredHeight)
				}
				val color = if (notification.state == "DISMISSED")
				{
					tvTitle.setTextColor(gray)
					Color.TRANSPARENT
				}
				else
				{
					tvTitle.setTextColor(blue)
					blue
				}

				iView.background = DrawableBuilder.setGradientDrawable(
					color,
					aCornerRadius = floatArrayOf(15f, 15f, 0f, 0f, 0f, 0f, 15f, 15f))
				//unicode for calendar
				val sDate = "\uD83D\uDCC5 ${toDate(notification.createdAt)}"
				tvTitle.text = notification.title
				tvBody.visibility = if (notification.message.isNotEmpty())
				{
					tvBody.text = notification.message
					View.VISIBLE
				}
				else View.GONE
				tvDate.text = sDate
				tvQuery.text = notification.query.replaceFirstChar {
					if (it.isLowerCase()) it.titlecase(
						Locale.US
					) else it.toString()
				}
			}
		}
	}

	override fun onPaint()
	{
		rlParent.run {
			context.run {
				ThemeColor.currentColor.run {
					white = pDrawerBackgroundColor
					gray = pDrawerTextColorPrimary
					blue = SinglentonDrawer.currentAccent
					tvBody.setTextColor(gray)
					tvDate.setTextColor(gray)
					tvQuery.setTextColor(gray)
					tvContent.setTextColor(gray)
					rlParent.background =
						DrawableBuilder.setGradientDrawable(white,18f,0, gray)
					iView.layoutParams = getRelativeLayoutParams(dpToPx(4f), ivTop.measuredHeight)
				}
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

	override fun showText(text: String, textSize: Float, intRes: Int)
	{
		rlLoad.visibility = View.GONE
		tvContent.run {
			visibility = View.VISIBLE
			textSize(textSize)
			setText(if (intRes != 0) context.getString(intRes) else text)
		}
		view.showItem(adapterPosition)
		iView.layoutParams = getRelativeLayoutParams(
			iView.context.dpToPx(4f), rlParent.measuredHeight)
	}

	private fun setBottomVisibility(notification: NotificationModel)
	{
		rlBottom.visibility = if (notification.isVisible) View.VISIBLE else View.GONE
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