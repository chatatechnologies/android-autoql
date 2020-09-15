package chata.can.chata_ai.activity.exploreQueries.adapter

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener

class QueryHolder(itemView: View): Holder(itemView)
{
	private val tvQuery = itemView.findViewById<TextView>(R.id.tvQuery) ?: null

	override fun onPaint()
	{

	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is String)
		{
			tvQuery?.text = item
		}
	}
}