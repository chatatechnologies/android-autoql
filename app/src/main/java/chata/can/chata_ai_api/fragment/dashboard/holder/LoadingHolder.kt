package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai_api.R

class LoadingHolder(itemView: View): BaseHolder(itemView) {

	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
	private val viewHeader = itemView.findViewById<View>(R.id.viewHeader)

	override fun onPaint() {
		super.onPaint()

		tvTitle.setTextColor(SinglentonDrawer.currentAccent)
		viewHeader.setBackgroundColor(viewHeader.context.getParsedColor(R.color.short_line_dashboard))
	}
}