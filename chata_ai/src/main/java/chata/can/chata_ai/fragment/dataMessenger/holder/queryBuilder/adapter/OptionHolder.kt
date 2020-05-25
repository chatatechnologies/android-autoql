package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener

class OptionHolder(view: View): Holder(view)
{
	private val rlParent = view.findViewById<View>(R.id.rlParent)
	private val tvQueryRoot = view.findViewById<TextView>(R.id.tvQueryRoot)
	private val ivBackExplore = view.findViewById<ImageView>(R.id.ivBackExplore)

	override fun onPaint()
	{

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