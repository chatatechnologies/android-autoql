package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class NoQueryHolder(itemView: View): BaseHolder(itemView)
{
	private val ll1 = itemView.findViewById<View>(R.id.ll1) ?: null
	private val tvExecute = itemView.findViewById<TextView>(R.id.tvExecute) ?: null

	override fun onPaint()
	{
		super.onPaint()
		ll1?.setBackgroundColor(drawerBackgroundColor)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			if (item.query.isEmpty())
			{
				val text = "No query was supplied for this title"
				tvExecute?.text = text
			}
		}
	}
}