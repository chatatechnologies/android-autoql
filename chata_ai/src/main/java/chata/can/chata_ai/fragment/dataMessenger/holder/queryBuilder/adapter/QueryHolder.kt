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
	private var isSelected = false

	override fun onPaint()
	{
		rlParent.run {
			context.run {
				setBackgroundColor(ThemeColor.currentColor.pDrawerBackgroundColor)
				tvQueryExplore.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
				if (isLast)
				{
					ivPlay.visibility = View.INVISIBLE
				}
				ivPlay.setColorFilter(Color.WHITE)
			}
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (isSelected)
		{
			rlParent.setBackgroundColor(
				ThemeColor.currentColor.pDrawerBackgroundColor)
			ivPlay.visibility = View.VISIBLE
		}
		else
		{
			if (!isLast)
			{
				ivPlay.visibility = View.INVISIBLE
			}
		}

		if (item is String)
		{
			tvQueryExplore?.text = item
			rlParent?.setOnClickListener {
				if (!isSelected)
				{
					isSelected = true
					val accentColor = SinglentonDrawer.currentAccent
					rlParent.setBackgroundColor(accentColor)
					ivPlay.visibility = View.VISIBLE
				}
				listener?.onItemClick(item)
			}
		}
	}
}