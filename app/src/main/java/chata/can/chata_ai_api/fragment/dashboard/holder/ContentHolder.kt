package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.dialog.drillDown.DrillDownDialog
import chata.can.chata_ai.dialog.sql.DisplaySQLDialog
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class ContentHolder(itemView: View): BaseHolder(itemView)
{
	private val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
	private val ivPoints = itemView.findViewById<ImageView>(R.id.ivOption)

	override fun onPaint()
	{
		super.onPaint()
		tvContent.context?.let {
			tvContent.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
			ivPoints?.setColorFilter(SinglentonDrawer.currentAccent)
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			item.queryBase?.run {
				tvContent?.let {
					if (isLoadingHTML && contentHTML.isEmpty())
					{
						isLoadingHTML = false
						aColumn.firstOrNull()?.let { column ->
							contentHTML = simpleText.formatWithColumn(column)
						}
					}
					it.text = contentHTML
					it.setOnClickListener { view ->
						if (SinglentonDrawer.mIsEnableDrillDown)
						{
							DrillDownDialog(view.context, this).show()
						}
					}
				}
				ivPoints?.let {
					it.setOnClickListener { view ->
						DisplaySQLDialog(view.context, sql).show()
					}
				}
			}
		}
	}
}