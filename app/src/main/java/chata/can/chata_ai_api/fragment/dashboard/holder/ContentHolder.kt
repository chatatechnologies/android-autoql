package chata.can.chata_ai_api.fragment.dashboard.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import chata.can.chata_ai.dialog.drillDown.DrillDownDialog
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.request.drillDown.DrillDownPresenter
import chata.can.chata_ai_api.R

class ContentHolder(private val context: Context, itemView: View): BaseHolder(itemView)
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
							//DrillDownPresenter(simpleQuery, null).postDrillDown()
							//TODO show the drillDown modal
							DrillDownDialog(context, simpleQuery).show()
						}
					}
				}
			}
		}
	}
}