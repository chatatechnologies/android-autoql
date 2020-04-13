package chata.can.chata_ai.holder

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.adapter.ChatAdapterContract
import chata.can.chata_ai.extension.setColorFilter
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

open class BaseHolder(
	itemView: View,
	private val view: ChatAdapterContract? = null
): Holder(itemView), View.OnClickListener
{
	val tvContent: TextView = itemView.findViewById(R.id.tvContent)

	private val rlDelete = itemView.findViewById<View>(R.id.rlDelete) ?: null
	private val ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete) ?: null

	override fun onPaint()
	{
		val gray = ContextCompat.getColor(
			tvContent.context,
			ThemeColor.currentColor.drawerColorPrimary)
		tvContent.setTextColor(gray)
		tvContent.background = backgroundGrayWhite(tvContent)

		rlDelete?.let {
			it.background = backgroundGrayWhite(it)
			it.setOnClickListener(this)
		}
		ivDelete?.setColorFilter()
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is ChatData)
		{
			if (item.message.isNotEmpty())
			{
				tvContent.text = item.message
			}
			else
			{
				var content = ""
				item.simpleQuery?.let {
					if (it is QueryBase)
					{
						content = processQueryBase(it)
					}
				}

				tvContent.text = content
			}
		}

		if (item is QueryBase)
		{
			tvContent.text = processQueryBase(item)
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
					view?.deleteQuery(adapterPosition)
					//endregion
				}
				else -> {}
			}
		}
	}

	private fun processQueryBase(simpleQuery: QueryBase): String
	{
		return when {
			simpleQuery.isSimpleText ->
			{
				rlDelete?.visibility = View.VISIBLE
				when
				{
					simpleQuery.contentHTML.isNotEmpty() ->
					{
						simpleQuery.isLoadingHTML = false
						simpleQuery.contentHTML
					}
					else -> simpleQuery.simpleText
				}
			}
			simpleQuery.aRows.size == 0 -> ""
			else -> return "display type not recognized: ${simpleQuery.displayType}"
		}
	}

	private fun backgroundGrayWhite(view: View): GradientDrawable
	{
		val white = ContextCompat.getColor(
			view.context,
			ThemeColor.currentColor.drawerBackgroundColor)
		val gray = ContextCompat.getColor(
			view.context,
			ThemeColor.currentColor.drawerColorPrimary)
		return  DrawableBuilder.setGradientDrawable(white,18f,1, gray)
	}
}