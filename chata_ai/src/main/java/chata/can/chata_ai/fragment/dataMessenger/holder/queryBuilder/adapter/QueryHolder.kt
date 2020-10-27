package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.adapter

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor

class QueryHolder(view: View): Holder(view)
{
	private var rlParent = view.findViewById<View>(R.id.rlParent)
	private var tvQueryExplore = view.findViewById<TextView>(R.id.tvQueryExplore)
	private var isSelected = false

	override fun onPaint()
	{
		rlParent.run {
			context.run {
				val white = getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
				setBackgroundColor(white)
				val gray = getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary)
				tvQueryExplore.setTextColor(gray)
			}
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (isSelected)
		{
			rlParent.setBackgroundColor(
				rlParent.context.getParsedColor(ThemeColor.currentColor.drawerBackgroundColor))
		}

		if (item is String)
		{
			tvQueryExplore?.text = item
			rlParent?.setOnClickListener {
				if (!isSelected)
				{
					isSelected = true
					rlParent.setBackgroundColor(rlParent.context.getParsedColor(R.color.blue_chata_circle))
				}
				listener?.onItemClick(item)
			}
		}
	}
}