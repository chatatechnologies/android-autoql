package chata.can.chata_ai.fragment.dataMessenger.holder

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.FullSuggestionQuery
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract
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
	private val tvRunQuery = itemView.findViewById<TextView>(R.id.tvRunQuery)
	private val ivRunQuery = itemView.findViewById<ImageView>(R.id.ivRunQuery)

	override fun onPaint()
	{
		tvContentTop.run {
			val textColor = context.getParsedColor(R.color.chata_drawer_hover_color)
			setTextColor(textColor)

			val queryDrawable = DrawableBuilder.setGradientDrawable(ThemeColor.currentColor.pDrawerAccentColor,18f)
			background = queryDrawable

			val animationTop = AnimationUtils.loadAnimation(context, R.anim.scale)
			startAnimation(animationTop)
		}

		val textColor = ThemeColor.currentColor.pDrawerTextColorPrimary
		tvContent.setTextColor(textColor)
		tvRunQuery.setTextColor(textColor)
		ivRunQuery.setColorFilter(textColor)
		llContent.backgroundGrayWhite()
		val colorBase = ThemeColor.currentColor.pDrawerBackgroundColor
		val borderColor = ThemeColor.currentColor.pDrawerBorderColor
		rlRunQuery.background = DrawableBuilder.setGradientDrawable(colorBase, 18f, 3, borderColor)
		rlDelete?.let {
			it.backgroundGrayWhite()
			it.setOnClickListener(this)
		}

		val animation = AnimationUtils.loadAnimation(llContent.context, R.anim.scale)
		llContent.startAnimation(animation)

		stvContent.setWindowManager()
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