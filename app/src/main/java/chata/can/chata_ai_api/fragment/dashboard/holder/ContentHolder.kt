package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.dialog.drillDown.DrillDownDialog
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class ContentHolder(itemView: View): BaseHolder(itemView)
{
	private val tvContent = itemView.findViewById<TextView>(R.id.tvContent)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			item.queryBase?.run {
				tvContent?.run {
					text = contentHTML
					item.queryBase?.let {
						simpleQuery ->
						setOnClickListener {
							DrillDownDialog(context, simpleQuery).show()
						}
					}
				}
			}
		}
	}
}