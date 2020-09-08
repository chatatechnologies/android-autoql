package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.adapter

import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
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

	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is String)
		{
			ivForwardExplore?.setColorFilter(
				ivForwardExplore.context.getParsedColor(ThemeColor.currentColor.drawerColorPrimary),
				PorterDuff.Mode.SRC_ATOP)

			tvQueryRoot?.text = item
			rlParent?.setOnClickListener {
				listener?.onItemClick(item)
			}
		}
	}
}