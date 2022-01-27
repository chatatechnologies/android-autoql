package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

abstract class BaseHolder(itemView: View): Holder(itemView)
{
	private val ll1 = itemView.findViewById<View>(R.id.ll1) ?: null
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle) ?: null

	protected var drawerBackgroundColor = ThemeColor.currentColor.pDrawerBackgroundColor
	protected var drawerColorPrimary = ThemeColor.currentColor.pDrawerTextColorPrimary

	override fun onPaint()
	{
		ll1?.backgroundWhiteGray()
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		tvTitle?.setTextColor(SinglentonDrawer.currentAccent)
		if (item is Dashboard)
		{
			val titleToShow =
				item.title.ifEmpty {
					item.query.ifEmpty { itemView.context.getString(R.string.untitled) }
				}
			tvTitle?.text = titleToShow
		}
	}
}