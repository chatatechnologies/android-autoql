package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.adapter

import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor

class OptionHolder(view: View): Holder(view)
{
	private val rlParent = view.findViewById<View>(R.id.rlParent)
	private val tvQueryRoot = view.findViewById<TextView>(R.id.tvQueryRoot)
	private val ivForwardExplore = view.findViewById<ImageView>(R.id.ivForwardExplore) ?: null

	override fun onPaint()
	{
		rlParent.run {
			context.run {
				setBackgroundColor(ThemeColor.currentColor.pDrawerBackgroundColor)

				val gray = ThemeColor.currentColor.pDrawerTextColorPrimary
				tvQueryRoot.setTextColor(gray)

				ivForwardExplore?.setColorFilter(gray, PorterDuff.Mode.SRC_ATOP)
			}
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is String)
		{
			tvQueryRoot?.text = item
			rlParent?.setOnClickListener {
				listener?.onItemClick(item)
			}
		}
	}
}