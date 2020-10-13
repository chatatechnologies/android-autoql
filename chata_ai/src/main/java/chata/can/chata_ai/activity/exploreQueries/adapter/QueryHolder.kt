package chata.can.chata_ai.activity.exploreQueries.adapter

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor

class QueryHolder(itemView: View): Holder(itemView)
{
	private val tvQuery = itemView.findViewById<TextView>(R.id.tvQuery) ?: null

	override fun onPaint()
	{
		tvQuery?.let {
			val primary = ThemeColor.currentColor.drawerTextColorPrimary
			it.setTextColor(it.context.getParsedColor(primary))
		}

	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is String)
		{
			tvQuery?.run {
				text = item
				setOnClickListener {
					listener?.onItemClick(item)
				}
			}
		}
	}
}