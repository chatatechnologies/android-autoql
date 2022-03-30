package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.LinearLayout
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.databinding.CardNoQueryBinding

class NoQueryHolder(itemView: View): DashboardHolder(itemView) {
	private val binding = CardNoQueryBinding.bind(itemView)

	init {
		binding.run {
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
	}

	override fun onRender(dashboard: Dashboard) {
		binding.run {
			val titleToShow =
				dashboard.title.ifEmpty {
					dashboard.query.ifEmpty { itemView.context.getString(R.string.untitled) }
				}
			tvTitle.text = titleToShow

			if (dashboard.splitView) {
				iView.setBackgroundColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
			} else {
				tvExecute2.visibility = View.GONE
				iView.setBackgroundColor(ThemeColor.currentColor.pDrawerBackgroundColor)
			}

			if (dashboard.query.isEmpty()) {
				tvExecute.let {
					val noQueryTitle = it.context.getString(R.string.no_query_title)
					it.text = noQueryTitle
				}
			}
		}
	}
}