package chata.can.chata_ai.activity.chat.holder

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.ChatContract
import chata.can.chata_ai.activity.chat.ChatServicePresenter
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.request.query.QueryRequest
import chata.can.chata_ai.view.extension.margin

class SuggestionHolder(
	itemView: View,
	private val view: ChatContract.View,
	private val servicePresenter: ChatServicePresenter): BaseHolder(itemView)
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
			item.queryBase?.let {
				if (it is QueryBase)
				{
					tvContent.context?.let {
						context ->
						val introMessageRes = context.getStringResources(R.string.msg_suggestion)
						val message = String.format(introMessageRes, it.message)
						tvContent.text = message
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
			background = buildBackgroundGrayWhite()
			layoutParams = LinearLayout.LayoutParams(-1, -2)
			margin(5f, 5f, 5f)
			gravity = Gravity.CENTER_HORIZONTAL
			setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
			setPadding(15,15,15,15)
			text = content
			setOnClickListener {
				view.addChatMessage(2, content)

				val mInfoHolder = hashMapOf<String, Any>("query" to content)
				QueryRequest.callQuery(content, servicePresenter, mInfoHolder)
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