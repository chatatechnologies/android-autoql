package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai_api.R

abstract class BaseHolder(itemView: View): Holder(itemView)
{
	private val ll1 = itemView.findViewById<View>(R.id.ll1) ?: null
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle) ?: null

	protected var drawerBackgroundColor = 0
	protected var drawerColorPrimary = 0

	override fun onPaint()
	{
		ll1?.let {
			it.context.run {
				drawerBackgroundColor = getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
				drawerColorPrimary = getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary)
			it.background = DrawableBuilder.setGradientDrawable(
				drawerBackgroundColor,18f,1, drawerColorPrimary)
			}
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is Dashboard)
		{
			val titleToShow =
				if (item.title.isNotEmpty()) item.title
				else
				{
					if (item.query.isNotEmpty()) item.query
					else "Untitled"
				}
			tvTitle?.text = titleToShow
		}
	}
}