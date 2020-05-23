package chata.can.chata_ai.fragment.dataMessenger.holder

import android.app.Activity
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.pager.PagerActivity
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener

class QueryBuilderHolder(
	view: View,
	private val pagerActivity: Activity): Holder(view)
{
	private var llContent = view.findViewById<View>(R.id.llContent) ?: null
	private var tvMsg = view.findViewById<TextView>(R.id.tvMsg) ?: null
	private var tvLink = view.findViewById<TextView>(R.id.tvLink) ?: null

	override fun onPaint()
	{
		llContent?.backgroundGrayWhite()
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		val linkMessage = "Use Explore Queries to further explore the possibilities."
		val spannable = SpannableString(linkMessage)
		val clickable = object: ClickableSpan()
		{
			override fun onClick(widget: View)
			{
				(pagerActivity as? PagerActivity)?.run {
					selectPage(1)
				}
			}

			override fun updateDrawState(textPaint: TextPaint)
			{
				textPaint.run {
					try {
						color = Color.parseColor("#0000EE")
					}
					finally {
						isUnderlineText = false
					}
				}
			}
		}
		spannable.setSpan(clickable, 4, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		tvLink?.run {
			text = spannable
			movementMethod = LinkMovementMethod.getInstance()
		}
	}
}