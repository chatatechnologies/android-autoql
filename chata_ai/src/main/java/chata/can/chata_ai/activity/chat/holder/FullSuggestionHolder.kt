package chata.can.chata_ai.activity.chat.holder

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.FullSuggestionQuery
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class FullSuggestionHolder(itemView: View): BaseHolder(itemView)
{
	private val llContent = itemView.findViewById<View>(R.id.llContent)
	private val llSuggestion = itemView.findViewById<LinearLayout>(R.id.llSuggestion)

	override fun onPaint()
	{
		val textColor = ContextCompat.getColor(tvContent.context, R.color.chata_drawer_color_primary)
		tvContent.setTextColor(textColor)
		llContent.background = buildBackgroundGrayWhite()
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is ChatData)
		{
			val simpleQuery = item.queryBase
			if (simpleQuery is FullSuggestionQuery)
			{
				tvContent.context?.let {
					it.resources?.let {
						val message = it.getString(R.string.msg_full_suggestion)
						tvContent.text = message
					}
				}
			}
		}
	}

	private fun buildBackgroundGrayWhite(): GradientDrawable
	{
		val white = ContextCompat.getColor(tvContent.context, R.color.chata_drawer_background_color)
		val gray = ContextCompat.getColor(tvContent.context, R.color.chata_drawer_color_primary)
		return DrawableBuilder.setGradientDrawable(white,18f,1, gray)
	}
}