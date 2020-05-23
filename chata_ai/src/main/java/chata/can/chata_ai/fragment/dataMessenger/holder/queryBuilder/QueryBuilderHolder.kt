package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder

import android.app.Activity
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
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

	private val tmpClick = view.findViewById<View>(R.id.tmpClick)
	private val tmpClick2 = view.findViewById<View>(R.id.tmpClick2)

	override fun onPaint()
	{
		llContent?.run {
			backgroundGrayWhite()
			val animation = AnimationUtils.loadAnimation(context, R.anim.scale)
			startAnimation(animation)
		}
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
						bgColor = Color.parseColor("#FFFFFF")
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

		tmpClick.setOnClickListener {
//			it.visibility = View.GONE
			tmpClick2.visibility = View.VISIBLE

			val anim = TranslateAnimation(0f, -500f, 0f, 0f)
			anim.duration = 1000
			anim.setAnimationListener(object: Animation.AnimationListener {
				override fun onAnimationStart(animation: Animation?) {}

				override fun onAnimationRepeat(animation: Animation?) {}

				override fun onAnimationEnd(animation: Animation?)
				{
					(it.layoutParams as? LinearLayout.LayoutParams)?.let {
						layoutParams ->
						layoutParams.leftMargin = -500
						tmpClick.layoutParams = layoutParams
					}
				}
			})
			it.startAnimation(anim)
		}
		tmpClick2.setOnClickListener {
			tmpClick.visibility = View.VISIBLE
			it.visibility = View.GONE
		}
	}
}