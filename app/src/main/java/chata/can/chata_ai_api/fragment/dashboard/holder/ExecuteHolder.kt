package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class ExecuteHolder(itemView: View): BaseHolder(itemView)
{
	private val tvExecute: TextView = itemView.findViewById(R.id.tvExecute)
	private val tvExecute2: TextView = itemView.findViewById(R.id.tvExecute2)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			if (item.splitView)
			{
				val layoutParams = tvExecute.layoutParams
				layoutParams.height = tvExecute.dpToPx(180f)
				val layoutParams2 = tvExecute2.layoutParams
				layoutParams2.height = tvExecute2.dpToPx(180f)
				ll2?.visibility = View.VISIBLE
			}
			else
			{
				ll2?.visibility = View.GONE
			}
		}
	}
}