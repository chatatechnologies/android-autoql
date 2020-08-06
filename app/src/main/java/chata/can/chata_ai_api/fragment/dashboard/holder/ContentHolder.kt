package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.dialog.drillDown.DrillDownDialog
import chata.can.chata_ai.extension.formatWithColumn
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
				tvContent?.let {
					if (isLoadingHTML)
					{
						isLoadingHTML = false
						aColumn.firstOrNull()?.let { column ->
							contentHTML = simpleText.formatWithColumn(column)
						}
					}
					it.text = contentHTML
					item.queryBase?.let {
						simpleQuery ->
						it.setOnClickListener {
							DrillDownDialog(it.context, simpleQuery).show()
						}
					}
				}
			}
		}
	}
}