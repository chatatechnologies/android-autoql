package chata.can.chata_ai.activity.dataMessenger.holder

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.dataMessenger.ChatContract
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.activity.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class SuggestionHolder(
	itemView: View,
	private val adapterView: ChatAdapterContract?,
	private val view: ChatContract.View
): BaseHolder(itemView)
{
	private val llContent = itemView.findViewById<View>(R.id.llContent)
	private val llSuggestion = itemView.findViewById<LinearLayout>(R.id.llSuggestion)

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
		}

		val textColor = tvContent.context.getParsedColor(ThemeColor.currentColor.drawerColorPrimary)
		tvContent.setTextColor(textColor)
		llContent.backgroundGrayWhite()

		rlDelete?.let {
			it.backgroundGrayWhite()
			it.setOnClickListener(this)
		}

		val animation = AnimationUtils.loadAnimation(llContent.context, R.anim.scale)
		llContent.startAnimation(animation)
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
						tvContent.context?.let {
								context ->
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
						singleRow.firstOrNull()?.let {
							suggestion ->
							//add new view for suggestion
							val tv = buildSuggestionView(llSuggestion.context, suggestion)
							llSuggestion.addView(tv)
						}
					}
				}
			}
		}
	}

	private fun buildSuggestionView(context: Context, content: String): TextView
	{
		return TextView(context).apply {
			backgroundGrayWhite()
			layoutParams = LinearLayout.LayoutParams(-1, -2)
			margin(5f, 5f, 5f)
			gravity = Gravity.CENTER_HORIZONTAL
			setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
			setPadding(15,15,15,15)
			text = content
			setOnClickListener {
				//view.addChatMessage(2, content)
				view.runTyping(content)
			}
		}
	}

	override fun onClick(v: View?)
	{
		v?.let {
			when(it.id)
			{
				R.id.rlDelete ->
				{
					adapterView?.deleteQuery(adapterPosition)
				}
				else -> {}
			}
		}
	}
}