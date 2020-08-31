package chata.can.chata_ai_api.fragment.dashboard.holder

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.DashboardView.getChildSuggestion
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter

class DynamicHolder(
	itemView: View,
	private val presenter: DashboardPresenter
): BaseHolder(itemView)
{
	private val lls1 = itemView.findViewById<LinearLayout>(R.id.lls1)
	private val lls2 = itemView.findViewById<LinearLayout>(R.id.lls2)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		item?.let {
			if (it is Dashboard)
			{
				it.queryBase?.let { queryBase ->
					when(queryBase.typeView)
					{
						TypeChatView.SUGGESTION_VIEW ->
						{
							val viewSuggestion = getChildSuggestion(lls1.context)
							ChildSuggestion(viewSuggestion, it, presenter)
							lls1.addView(viewSuggestion)
						}
						else ->
						{

						}
					}
				}
			}
		}
	}

	//region presenter suggestion
	class ChildSuggestion(
		view: View,
		private val dashboard: Dashboard,
		private val presenter: DashboardPresenter
	)
	{
		private val tvContent = view.findViewById<TextView>(R.id.tvContent)?: null
		private val llSuggestion = view.findViewById<LinearLayout>(R.id.llSuggestion)?: null

		init {
			onPaint()
			onBind()
		}

		//Is necessary method for set data on tvTitle

		private fun onPaint()
		{
			tvContent?.let {
				val drawerColorPrimary =
					ContextCompat.getColor(it.context, ThemeColor.currentColor.drawerColorPrimary)
				it.setTextColor(drawerColorPrimary)
			}
		}

		private fun onBind()
		{
			dashboard.queryBase?.let {
				tvContent?.context?.let { context ->
					val introMessageRes = context.getStringResources(R.string.msg_suggestion)
					val message = String.format(introMessageRes, it.message)
					tvContent.text = message
				}

				val rows = it.aRows
				llSuggestion?.let {
					it.removeAllViews()
					for (index in 0 until rows.size)
					{
						val singleRow = rows[index]
						singleRow.firstOrNull()?.let { suggestion ->
							//add new view for suggestion
							val tv = buildSuggestionView(it.context, suggestion, dashboard)
							it.addView(tv)
						}
					}
				}
			}
		}

		private fun buildSuggestionView(
			context: Context, content: String, dashboard: Dashboard): TextView
		{
			return TextView(context).apply {
				backgroundGrayWhite()
				layoutParams = LinearLayout.LayoutParams(-1, -2)
				margin(5f, 5f, 5f)
				gravity = Gravity.CENTER_HORIZONTAL
				setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
				setPadding(15,15,15,15)
				text = content
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
	//endregion
//	when(typeView)
//	{
//		TypeChatView.LEFT_VIEW -> 3
//		TypeChatView.WEB_VIEW -> 4
//		TypeChatView.SUGGESTION_VIEW -> 5
//		else -> 2
//	}
}