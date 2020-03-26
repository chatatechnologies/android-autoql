package chata.can.chata_ai.holder

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.tool.DrawableBuilder

open class BaseHolder(view: View): Holder(view)
{
	val tvContent: TextView = view.findViewById(R.id.tvContent)

	override fun onPaint()
	{
		val textColor = ContextCompat.getColor(tvContent.context, R.color.chata_drawer_color_primary)
		tvContent.setTextColor(textColor)

		val white = ContextCompat.getColor(tvContent.context, R.color.chata_drawer_background_color)
		val gray = ContextCompat.getColor(tvContent.context, R.color.chata_drawer_color_primary)
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
						if (it.isSimpleText)
						{
							content = it.simpleText
						}
					}
				}

				tvContent.text = content
			}
		}
	}
}