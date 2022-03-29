package chata.can.chata_ai_api.fragment.dashboard.holder

import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class ExecuteHolder(itemView: View): DashboardHolder(itemView)
{
	private val ll1 = itemView.findViewById<View>(R.id.ll1)
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)

	private val iView = itemView.findViewById<View>(R.id.iView)
	private val tvExecute = itemView.findViewById<TextView>(R.id.tvExecute)
	private val tvExecute2 = itemView.findViewById<TextView>(R.id.tvExecute2)

	private val viewHeader = itemView.findViewById<View>(R.id.viewHeader)

	init {
		iView?.let {
			(it.layoutParams as? LinearLayout.LayoutParams)?.let { layout ->
				layout.height = 1
				iView.layoutParams = layout
			}
		}

		with(ThemeColor.currentColor) {
			ll1?.backgroundWhiteGray()
			tvExecute.setTextColor(pDrawerTextColorPrimary)
			tvExecute2.setTextColor(pDrawerTextColorPrimary)
		}

		tvExecute.setTypeface(tvExecute.typeface, Typeface.ITALIC)

		viewHeader.setBackgroundColor(viewHeader.context.getParsedColor(R.color.short_line_dashboard))
		iView.setBackgroundColor(iView.context.getParsedColor(R.color.selected_gray))
	}

	override fun onRender(dashboard: Dashboard) {
		tvTitle?.setTextColor(SinglentonDrawer.currentAccent)
		val titleToShow =
			dashboard.title.ifEmpty {
				dashboard.query.ifEmpty { itemView.context.getString(R.string.untitled) }
			}
		tvTitle?.text = titleToShow

		if (dashboard.splitView) {
			val layoutParams = tvExecute.layoutParams
			layoutParams.height = tvExecute.dpToPx(150f)

			val layoutParams2 = tvExecute2.layoutParams
			layoutParams2.height = tvExecute2.dpToPx(150f)
			tvExecute2.visibility = View.VISIBLE
			iView?.visibility = View.VISIBLE
		} else {
			tvExecute2.visibility = View.GONE
			iView?.visibility = View.GONE
		}
	}
}