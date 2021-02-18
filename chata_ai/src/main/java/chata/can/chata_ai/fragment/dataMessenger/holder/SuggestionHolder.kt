package chata.can.chata_ai.fragment.dataMessenger.holder

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.ListPopup
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.fragment.dataMessenger.SuggestionPresenter
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class SuggestionHolder(
	itemView: View,
	private val adapterView: ChatAdapterContract?,
	private val view: ChatContract.View
): BaseHolder(itemView)
{
	private val llContent = itemView.findViewById<View>(R.id.llContent)
	private val llSuggestion = itemView.findViewById<LinearLayout>(R.id.llSuggestion)
	private val presenter = SuggestionPresenter(view)

	override fun onPaint()
	{
		tvContentTop.run {
			context?.run {
				ivReport?.setColorFilter(accentColor)
				ivDelete?.setColorFilter(accentColor)
			}

			val textColor = context.getParsedColor(R.color.chata_drawer_hover_color)
			setTextColor(textColor)

			val accentColor = ThemeColor.currentColor.pDrawerAccentColor
			val queryDrawable = DrawableBuilder.setGradientDrawable(accentColor,18f)
			background = queryDrawable

			//val animationTop = AnimationUtils.loadAnimation(context, R.anim.scale)
			//startAnimation(animationTop)
		}

		tvContent.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
		llContent.backgroundGrayWhite()

		rlDelete?.backgroundGrayWhite()
		ivDelete?.setOnClickListener(this)
		ivReport?.setOnClickListener(this)

		val animation = AnimationUtils.loadAnimation(llContent.context, R.anim.scale)
		//llContent.startAnimation(animation)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is ChatData)
		{
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

				if (it is QueryBase)
				{
					queryBase = it
					tvContentTop.text  = it.query
					if (it.query.isEmpty())
					{
//						if (it.message != "Success")
//						{
//							tvContent.visibility = View.VISIBLE
//							tvContent.text = it.message
//						}
//						else
							tvContent.visibility = View.GONE
					}
					else
					{
						tvContent.visibility = View.VISIBLE
						tvContent.context?.let { context ->
							val introMessageRes = context.getStringResources(R.string.msg_suggestion)
							val message = String.format(introMessageRes, it.message)
							tvContent.text = message
						}
					}

					val rows = it.aRows
					llSuggestion.removeAllViews()
					for (index in 0 until rows.size)
					{
						val singleRow = rows[index]
						singleRow.firstOrNull()?.let { suggestion ->
							if (index == rows.size - 1)
							{
								llSuggestion.addView(buildSuggestionView(
									llSuggestion.context,
									"None of these",
									true,
									it.queryId))
							}
							else
							{
								val tv = buildSuggestionView(llSuggestion.context, suggestion)
								llSuggestion.addView(tv)
							}
						}
					}
				}
			}
		}
	}

	private fun buildSuggestionView(
		context: Context,
		content: String,
		isSuggestion: Boolean = false,
		queryId: String = ""): TextView
	{
		return TextView(context).apply {
			background = DrawableBuilder.setGradientDrawable(
				ThemeColor.currentColor.pDrawerBackgroundColor,
				18f,
				1,
				ThemeColor.currentColor.pDrawerBorderColor)

			layoutParams = LinearLayout.LayoutParams(-1, -2)
			margin(5f, 5f, 5f)
			gravity = Gravity.CENTER_HORIZONTAL
			setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
			setPadding(15,15,15,15)
			text = content
			if (isSuggestion)
			{
				setOnClickListener {
					presenter.setSuggestion(queryId)
				}
			}
			else
			{
				setOnClickListener {
					view.runTyping(content)
				}
			}
			setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
		}
	}

	override fun onClick(v: View?)
	{
		v?.let {
			when(it.id)
			{
				R.id.ivReport ->
				{
					ListPopup.showListPopup(it, queryBase?.queryId ?: "", view)
				}
				R.id.ivDelete ->
				{
					adapterView?.deleteQuery(adapterPosition)
				}
				else -> {}
			}
		}
	}
}