package chata.can.chata_ai.holder

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.ListPopup
import chata.can.chata_ai.dialog.ReportProblemDialog
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.request.drillDown.DrillDownPresenter

open class BaseHolder(
	itemView: View,
	private val view: ChatAdapterContract? = null,
	private val chatView: ChatContract.View? = null
): Holder(itemView), View.OnClickListener
{
	val tvContentTop: TextView = itemView.findViewById(R.id.tvContentTop)
	val tvContent: TextView = itemView.findViewById(R.id.tvContent)
	val rlDelete = itemView.findViewById<View>(R.id.rlDelete) ?: null
	protected val ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete) ?: null
	protected val ivReport = itemView.findViewById<ImageView>(R.id.ivReport) ?: null
	private val ivPoints = itemView.findViewById<ImageView>(R.id.ivPoints) ?: null
	protected var queryBase: QueryBase ?= null

	private val blueAccent = R.color.blue_chata_circle

	override fun onPaint()
	{
		tvContentTop.run {
			val textColor = context.getParsedColor(R.color.chata_drawer_hover_color)
			setTextColor(textColor)

			val accentColor = context.getParsedColor(ThemeColor.currentColor.drawerAccentColor)
			val queryDrawable = DrawableBuilder.setGradientDrawable(accentColor,18f)
			background = queryDrawable

			val animationTop = AnimationUtils.loadAnimation(context, R.anim.scale)
			startAnimation(animationTop)

			context?.run {
				ivReport?.setColorFilter(getParsedColor(blueAccent))
				ivDelete?.setColorFilter(getParsedColor(blueAccent))
				ivPoints?.setColorFilter(getParsedColor(blueAccent))
			}
		}

		val gray = tvContent.context.getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary)
		tvContent.setTextColor(gray)
		tvContent.backgroundGrayWhite()

		rlDelete?.backgroundGrayWhite()
		ivDelete?.setOnClickListener(this)
		ivReport?.setOnClickListener(this)
		ivPoints?.setOnClickListener(this)

		val animation = AnimationUtils.loadAnimation(tvContent.context, R.anim.scale)
		tvContent.startAnimation(animation)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		when(item)
		{
			is ChatData ->
			{
				if (item.message.isNotEmpty())
				{
//					if (item.message == "I want to make sure I understood your query. Did you mean:")
//					{
//						llMainBase?.visibility = View.GONE
//					}
//					else
//					{
//						llMainBase?.visibility = View.VISIBLE
//						tvContent.text = item.message
//					}
					tvContent.text = item.message
					item.simpleQuery?.let {
						if (it.query.isNotEmpty())
						{
							tvContentTop.visibility = View.VISIBLE
							tvContentTop.text = it.query
						}
						else
						{
							tvContentTop.visibility = View.GONE
						}

						rlDelete?.visibility = View.VISIBLE
						ivReport?.visibility =
							if (item.message == "I want to make sure I understood your query. Did you mean:")
								View.GONE
							else View.VISIBLE

					} ?: run {
						tvContentTop.visibility = View.GONE
						rlDelete?.visibility = View.GONE
					}
				}
				else
				{
					item.simpleQuery?.let {
						if (it is QueryBase)
						{
							processQueryBase(it)
						}
					}
				}
			}
			is QueryBase ->
			{
				processQueryBase(item)
			}
		}
	}

	override fun onClick(v: View?)
	{
		v?.let {
			when(it.id)
			{
				R.id.ivReport ->
				{
					ListPopup.showListPopup(it, queryBase?.queryId ?: "", chatView)
				}
				R.id.ivDelete ->
				{
					view?.deleteQuery(adapterPosition)
				}
				R.id.ivPoints ->
				{
					ListPopup.showPointsPopup(it, queryBase?.sql ?: "")
				}
				else -> {}
			}
		}
	}

	private fun processQueryBase(simpleQuery: QueryBase)
	{
		if (simpleQuery.query.isNotEmpty())
		{
			tvContentTop.visibility = View.VISIBLE
			tvContentTop.text = simpleQuery.query
		}
		else
		{
			tvContentTop.visibility = View.GONE
		}
		queryBase = simpleQuery
		val message = when
		{
			simpleQuery.isSimpleText ->
			{
				rlDelete?.visibility = View.VISIBLE
				when
				{
					simpleQuery.contentHTML.isNotEmpty() ->
					{
						tvContent.setOnClickListener {
							if (SinglentonDrawer.mIsEnableDrillDown)
							{
								chatView?.isLoading(true)
								DrillDownPresenter(simpleQuery, chatView).postDrillDown()
							}
						}
						simpleQuery.isLoadingHTML = false
						simpleQuery.contentHTML
					}
					else -> simpleQuery.simpleText
				}
			}
			simpleQuery.aRows.size == 0 ->
			{
				if (simpleQuery.message.isNotEmpty())
				{
					rlDelete?.visibility = View.VISIBLE
					reportLink(simpleQuery.message, simpleQuery.queryId)
				}
				else
				{
					"Uh oh.. It looks like you don't have access to this resource. Please double check that all the required authentication fields are provided."
				}
			}
			else -> "display type not recognized: ${simpleQuery.displayType}"
		}

		tvContent.text = message
	}

	private fun reportLink(message: String, queryId: String = ""): CharSequence
	{
		return if (message.contains("<report>"))
		{
			val message1 = message.replace("<report>", "report")
			val spannable = SpannableString(message1)
			if (queryId.isNotEmpty())
			{
				val clickable = object: ClickableSpan()
				{
					override fun onClick(widget: View)
					{
						ReportProblemDialog(tvContent.context, queryId, chatView).show()
					}

					override fun updateDrawState(textPaint: TextPaint)
					{
						textPaint.run {
							try {
								tvContent.context?.let {
									color = it.getParsedColor(R.color.chata_drawer_accent_color)
								}
							} finally {
								tvContent.context?.let {
									bgColor = it.getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
								}
							}
							isUnderlineText = false
						}
					}
				}
				val index = message1.indexOf("report")
				spannable.setSpan(clickable, index, index + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
				tvContent.movementMethod = LinkMovementMethod.getInstance()
				spannable
			}
			else
				message
		}
		else
			message
	}
}