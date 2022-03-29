package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.model.StringContainer
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class SupportHolder(itemView: View): DashboardHolder(itemView) {
	private val ll1 = itemView.findViewById<View>(R.id.ll1)
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
	private val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
	private val viewHeader = itemView.findViewById<View>(R.id.viewHeader)

	init {
		ll1.backgroundWhiteGray()
		tvTitle.setTextColor(SinglentonDrawer.currentAccent)

		tvContent.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
		viewHeader.setBackgroundColor(viewHeader.getParsedColor(R.color.short_line_dashboard))
	}

	override fun onRender(dashboard: Dashboard) {
		val titleToShow =
			dashboard.title.ifEmpty {
				dashboard.query.ifEmpty { itemView.context.getString(R.string.untitled) }
			}
		tvTitle.text = titleToShow

		dashboard.queryBase?.run {
			if (referenceId != "1.1.430")
			{
				message = "$message\n\n${StringContainer.errorId} $referenceId"
			}
			tvContent?.text = message
		}
	}
}