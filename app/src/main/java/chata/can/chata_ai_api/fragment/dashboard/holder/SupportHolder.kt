package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.StringContainer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class SupportHolder(itemView: View): BaseHolder(itemView)
{
	private val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
	private val viewHeader = itemView.findViewById<View>(R.id.viewHeader)

	override fun onPaint()
	{
		super.onPaint()
		tvContent.context?.let {
			tvContent.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
		}

		viewHeader.setBackgroundColor(viewHeader.getParsedColor(R.color.short_line_dashboard))
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			item.queryBase?.run {
				if (referenceId != "1.1.430")
				{
					message = "$message\n\n${StringContainer.errorId} $referenceId"
				}
				tvContent?.text = message
			}
		}
	}
}