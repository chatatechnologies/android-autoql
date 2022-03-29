package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class NoQueryHolder(itemView: View): DashboardHolder(itemView) {
	private val ll1 = itemView.findViewById<View>(R.id.ll1)
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)

	private val iView = itemView.findViewById<View>(R.id.iView)
	private val tvExecute = itemView.findViewById<TextView>(R.id.tvExecute)
	private val tvExecute2: TextView = itemView.findViewById(R.id.tvExecute2)

	init {
		ll1.backgroundWhiteGray()
		tvTitle.setTextColor(SinglentonDrawer.currentAccent)

		iView.let {
			(it.layoutParams as? LinearLayout.LayoutParams)?.let { layout ->
				layout.height = 1
				iView.layoutParams = layout
			}
		}
		tvExecute.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
		tvExecute2.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
	}

	override fun onRender(dashboard: Dashboard) {
		if (dashboard.splitView) {
			iView.setBackgroundColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
		} else {
			tvExecute2.visibility = View.GONE
			iView.setBackgroundColor(ThemeColor.currentColor.pDrawerBackgroundColor)
		}

		if (dashboard.query.isEmpty())
		{
			tvExecute?.let {
				val noQueryTitle = it.context.getString(R.string.no_query_title)
				it.text = noQueryTitle
			}
		}
	}
}