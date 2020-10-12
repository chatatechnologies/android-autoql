package chata.can.chata_ai.activity.dataMessenger.holder

import android.view.View
import android.view.animation.AnimationUtils
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.dataMessenger.ChatContract
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.FullSuggestionQuery
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.activity.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.textViewSpinner.model.SpinnerTextView

class FullSuggestionHolder(
	itemView: View,
	private val adapterView: ChatAdapterContract?,
	private val view: ChatContract.View
): BaseHolder(itemView)
{
	private val llContent = itemView.findViewById<View>(R.id.llContent)
	private val stvContent = itemView.findViewById<SpinnerTextView>(R.id.stvContent)
	private val rlRunQuery = itemView.findViewById<View>(R.id.rlRunQuery)

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

		val textColor = tvContent.context.getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary)
		tvContent.setTextColor(textColor)
		llContent.backgroundGrayWhite()
		rlRunQuery.backgroundGrayWhite()

		rlDelete?.let {
			it.backgroundGrayWhite()
			it.setOnClickListener(this)
		}

		val animation = AnimationUtils.loadAnimation(llContent.context, R.anim.scale)
		llContent.startAnimation(animation)

		stvContent.setWindowManager(ScreenData.windowManager)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is ChatData)
		{
			val simpleQuery = item.simpleQuery
			if (simpleQuery is FullSuggestionQuery)
			{
				tvContentTop.text = simpleQuery.query
				tvContent.context?.let {
					context ->
					context.resources?.let {
						resources ->
						val message = resources.getString(R.string.msg_full_suggestion)
						tvContent.text = message
					}

					stvContent.setText(simpleQuery.aSuggestion)

					rlRunQuery.setOnClickListener {
						val query = stvContent.text
						if (query.isNotEmpty())
						{
							view.runTyping(query)
						}
					}
				}
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
					//region delete query
					adapterView?.deleteQuery(adapterPosition)
					//endregion
				}
				else -> {}
			}
		}
	}
}