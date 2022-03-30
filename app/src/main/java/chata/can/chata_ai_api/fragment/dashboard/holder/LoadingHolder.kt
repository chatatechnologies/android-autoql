package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.databinding.CardLoadingBinding

class LoadingHolder(itemView: View): DashboardHolder(itemView) {

	private val binding = CardLoadingBinding.bind(itemView)

	init {
		binding.run {
			ll1.backgroundWhiteGray()
			tvTitle.setTextColor(SinglentonDrawer.currentAccent)
			viewHeader.setBackgroundColor(viewHeader.getParsedColor(R.color.short_line_dashboard))
		}
	}

	override fun onRender(dashboard: Dashboard) {
		binding.run {
			val titleToShow =
				dashboard.title.ifEmpty {
					dashboard.query.ifEmpty { itemView.context.getString(R.string.untitled) }
				}
			tvTitle.text = titleToShow
		}
	}
}