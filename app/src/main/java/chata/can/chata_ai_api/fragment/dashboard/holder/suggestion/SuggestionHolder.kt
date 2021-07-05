package chata.can.chata_ai_api.fragment.dashboard.holder.suggestion

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.extension.backgroundWhiteGray
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter
import chata.can.chata_ai_api.fragment.dashboard.holder.BaseHolder

class SuggestionHolder(
	itemView: View,
	private val presenter: DashboardPresenter
): BaseHolder(itemView)
{
	private val tvContent: TextView = itemView.findViewById(R.id.tvContent)
	private val llSuggestion: LinearLayout = itemView.findViewById(R.id.llSuggestion)

	override fun onPaint()
	{
		super.onPaint()
		tvContent.setTextColor(drawerColorPrimary)
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			item.queryBase?.let { queryBase ->
				tvContent.context?.let { context ->
					val introMessageRes = context.getStringResources(R.string.msg_suggestion)
					val message = String.format(introMessageRes, queryBase.message)
					tvContent.text = message
				}

				val rows = queryBase.aRows
				llSuggestion.removeAllViews()
				if (queryBase.typeSuggestion != "table")
				{
					for (index in 0 until rows.size)
					{
						val singleRow = rows[index]
						singleRow.firstOrNull()?.let { suggestion ->
							//add new view for suggestion
							val tv = buildSuggestionView(llSuggestion.context, suggestion, item)
							llSuggestion.addView(tv)
						}
					}
				}
				else
				{
					llSuggestion.addView(TextView(llSuggestion.context).apply {
						setTextColor(drawerColorPrimary)
						text = "Test"
					})
				}
			}
		}
	}

	private fun buildSuggestionView(context: Context, content: String, dashboard: Dashboard): TextView
	{
		return TextView(context).apply {
			backgroundWhiteGray()
			layoutParams = LinearLayout.LayoutParams(-1, -2)
			margin(5f, 5f, 5f)
			gravity = Gravity.CENTER_HORIZONTAL
			setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
			setPadding(15,15,15,15)
			text = content
			setTextColor(drawerColorPrimary)
			setOnClickListener {
				val index = SinglentonDashboard.indexDashboard(dashboard)
				if (index != -1)
				{
					presenter.notifyQueryByIndex(index)
					presenter.callQuery(
						dashboard.apply {
							query = content
							title = content
							isWaitingData = true
							queryBase = null
						}
					)
				}
			}
		}
	}
}