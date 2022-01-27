package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.textSize
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter

object ChildSuggestion {
	fun onBind(view: View, dashboard: Dashboard, presenter: DashboardPresenter, isPrimary: Boolean)
	{
		dashboard.run {
			if (isPrimary)
			{
				queryBase?.let { queryBase ->
					setSuggestion(view, dashboard, queryBase, presenter, isPrimary)
				}
			}
			else
			{
				queryBase2?.let { queryBase ->
					setSuggestion(view, dashboard, queryBase, presenter, isPrimary)
				}
			}
		}
	}

	private fun setSuggestion(
		view: View,
		dashboard: Dashboard,
		queryBase: QueryBase,
		presenter: DashboardPresenter,
		isPrimary: Boolean)
	{
		view.findViewById<TextView>(R.id.tvContent)?.let { tvContent ->
			tvContent.setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)

			val introMessageRes = tvContent.context.getStringResources(R.string.msg_suggestion)
			val message = String.format(introMessageRes, queryBase.message)
			tvContent.text = message
		}

		val rows = queryBase.aRows
		view.findViewById<LinearLayout>(R.id.llSuggestion)?.let { llSuggestion ->
			llSuggestion.removeAllViews()
			for (index in 0 until rows.size)
			{
				val singleRow = rows[index]
				singleRow.firstOrNull()?.let { suggestion ->
					//add new view for suggestion
					val tv = buildSuggestionView(
						llSuggestion.context,
						suggestion,
						dashboard,
						presenter,
						isPrimary)
					llSuggestion.addView(tv)
				}
			}
		}
	}

	private fun buildSuggestionView(
		context: Context,
		content: String,
		dashboard: Dashboard,
		presenter: DashboardPresenter,
		isPrimary: Boolean): TextView
	{
		return TextView(context).apply {
			background = DrawableBuilder.setGradientDrawable(
				ThemeColor.currentColor.pDrawerBackgroundColor,
				18f,
				1, ThemeColor.currentColor.pDrawerBorderColor)

			layoutParams = getLinearLayoutParams(-1, -2)
			margin(5f, 5f, 5f)
			gravity = Gravity.CENTER_HORIZONTAL
			textSize(16f)
			setPadding(15,15,15,15)
			setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
			text = content
			setOnClickListener {
				val index = SinglentonDashboard.indexDashboard(dashboard)
				if (index != -1)
				{
					presenter.notifyQueryByIndex(index)
					presenter.callQuery(
						dashboard.apply {
							if (isPrimary)
							{
								query = content
								title = content
								isWaitingData = true
								queryBase = null
							}
							else
							{
								secondQuery = content
								isWaitingData2 = true
								queryBase2 = null
							}
						}
					)
				}
			}
		}
	}
}