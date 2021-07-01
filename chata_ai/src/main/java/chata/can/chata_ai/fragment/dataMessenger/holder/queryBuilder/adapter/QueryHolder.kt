package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor

class QueryHolder(view: View, private val isLast: Boolean): Holder(view)
{
	private var rlParent = view.findViewById<View>(R.id.rlParent)
	private var tvQueryExplore = view.findViewById<TextView>(R.id.tvQueryExplore)
	private var ivPlay = view.findViewById<ImageView>(R.id.ivPlay)
	//private var isSelected = false

	override fun onPaint()
	{
		rlParent.run {
			context.run {
				setBackgroundColor(ThemeColor.currentColor.pDrawerBackgroundColor)
				tvQueryExplore.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
				ivPlay.setColorFilter(Color.WHITE)
			}
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is OptionData)
		{
			tvQueryExplore?.text = item.text
			ivPlay.visibility = if (item.isSelected) View.VISIBLE else View.INVISIBLE
			rlParent.setBackgroundColor(
				if (item.isSelected) SinglentonDrawer.currentAccent
				else ThemeColor.currentColor.pDrawerBackgroundColor)

			rlParent?.setOnClickListener {
				if (!item.isSelected)
				{
					item.isSelected = true
					rlParent.setBackgroundColor(SinglentonDrawer.currentAccent)
					ivPlay.visibility = View.VISIBLE
				}
				listener?.onItemClick(item)
			}
		}
	}
}