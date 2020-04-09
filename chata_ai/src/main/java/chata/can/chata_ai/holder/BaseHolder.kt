package chata.can.chata_ai.holder

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

open class BaseHolder(view: View): Holder(view)
{
	val tvContent: TextView = view.findViewById(R.id.tvContent)

	override fun onPaint()
	{
		val gray = ContextCompat.getColor(
			tvContent.context,
			ThemeColor.currentColor.drawerColorPrimary)
		tvContent.setTextColor(gray)

		val white = ContextCompat.getColor(
			tvContent.context,
			ThemeColor.currentColor.drawerBackgroundColor)
		val queryDrawable = DrawableBuilder.setGradientDrawable(white,18f,1, gray)
		tvContent.background = queryDrawable
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
	private fun processQueryBase(simpleQuery: QueryBase): String
	{
		if (simpleQuery.isSimpleText)
		{
			return if (simpleQuery.contentHTML.isNotEmpty())
			{
				simpleQuery.isLoadingHTML = false
				simpleQuery.contentHTML
			}
			else
				simpleQuery.simpleText
		}
		return "display type not recognized: ${simpleQuery.displayType}"
	}
}