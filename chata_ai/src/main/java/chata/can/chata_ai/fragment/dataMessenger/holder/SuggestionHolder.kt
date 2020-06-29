package chata.can.chata_ai.fragment.dataMessenger.holder

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.request.query.QueryRequest
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.setColorFilter
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.fragment.dataMessenger.presenter.ChatServicePresenter
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class SuggestionHolder(
	itemView: View,
	private val view: ChatContract.View,
	private val adapterView: ChatAdapterContract?,
	private val servicePresenter: ChatServicePresenter
): BaseHolder(itemView)
{
	private val llContent = itemView.findViewById<View>(R.id.llContent)
	private val llSuggestion = itemView.findViewById<LinearLayout>(R.id.llSuggestion)

	override fun onPaint()
	{
		tvContentTop.run {
			val textColor = ContextCompat.getColor(context, R.color.chata_drawer_hover_color)
			setTextColor(textColor)

			val accentColor = ContextCompat.getColor(context, ThemeColor.currentColor.drawerAccentColor)
			val queryDrawable = DrawableBuilder.setGradientDrawable(accentColor,18f)
			background = queryDrawable

			val animationTop = AnimationUtils.loadAnimation(context, R.anim.scale)
			startAnimation(animationTop)
		}

		val textColor = ContextCompat.getColor(
			tvContent.context,
			ThemeColor.currentColor.drawerColorPrimary)
		tvContent.setTextColor(textColor)
		llContent.backgroundGrayWhite()

		rlDelete?.let {
			it.backgroundGrayWhite()
			it.setOnClickListener(this)
		}
		ivDelete?.setColorFilter()

		val animation = AnimationUtils.loadAnimation(llContent.context, R.anim.scale)
		llContent.startAnimation(animation)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is ChatData)
		{
			item.simpleQuery?.let {
				if (it is QueryBase)
				{
					tvContentTop.text  = it.query

					if (it.query.isEmpty())
					{
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
				val mInfoHolder = hashMapOf<String, Any>("query" to content)
				QueryRequest.callQuery(content, servicePresenter, "data_messenger", mInfoHolder)
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