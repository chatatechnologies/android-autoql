package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.view.View
import android.widget.LinearLayout
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.DashboardView.getChildLoading
import chata.can.chata_ai_api.DashboardView.getChildSuggestion
import chata.can.chata_ai_api.DashboardView.getChildWebView
import chata.can.chata_ai_api.DashboardView.getExecute
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter
import chata.can.chata_ai_api.fragment.dashboard.holder.BaseHolder

class DynamicHolder(
	itemView: View,
	private val presenter: DashboardPresenter
): BaseHolder(itemView)
{
	private val tData1 = Triple(R.id.rlWebView, R.id.webView, R.id.rlLoad)
	private val tData2 = Triple(R.id.rvSplitView, R.id.webView2, R.id.rlLoad2)

	private val lls1 = itemView.findViewById<LinearLayout>(R.id.lls1)
	private val lls2 = itemView.findViewById<LinearLayout>(R.id.lls2)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		item?.let { dashboard ->
			if (dashboard is Dashboard)
			{
				dashboard.queryBase?.let { queryBase ->
					when(queryBase.typeView)
					{
						TypeChatView.LEFT_VIEW ->
						{

						}
						TypeChatView.SUGGESTION_VIEW ->
						{
							var vSuggestion = lls1.searchView(R.id.llMainSuggestion)
							if (vSuggestion == null)
							{
								vSuggestion = getChildSuggestion(lls1.context)
								addView(lls1, vSuggestion)
							}
							ChildSuggestion(vSuggestion, dashboard, presenter)
						}
						else -> { }
					}
				} ?: run {
					if (dashboard.isWaitingData)
					{
						if (dashboard.query.isEmpty())
						{
							var vExecute = lls1.searchView(R.id.tvNoQuery)
							if (vExecute == null)
							{
								vExecute = getExecute(lls1.context, R.id.tvNoQuery)
								addView(lls1, vExecute)
							}
							ChildNoQuery(vExecute, dashboard, true)
						}
						else
						{
							addView(lls1, getChildLoading(lls1.context))
						}
					}
					else
					{
						lls1.searchView(R.id.tvExecute)?:run {
							val vExecute = getExecute(lls1.context, R.id.tvExecute)
							addView(lls1, vExecute)
						}
					}
				}

				dashboard.queryBase2?.let {

				} ?: run {
					if (dashboard.isWaitingData2)
					{
						if (dashboard.query.isEmpty())
						{
							var vExecute2 = lls2.searchView(R.id.tvNoQuery)
							if (vExecute2 == null)
							{
								vExecute2 = getExecute(lls2.context, R.id.tvNoQuery)
								addView(lls2, vExecute2)
							}
							ChildNoQuery(vExecute2, dashboard, false)
						}
						else
						{
							addView(lls2, getChildLoading(lls2.context))
						}
					}
					else
					{
						lls2.searchView(R.id.tvExecute)?:run {
							val vExecute2 = getExecute(lls2.context, R.id.tvExecute)
							addView(lls2, vExecute2)
						}
					}
				}
			}

			if (item is QueryBase)
			{
				when(item.typeView)
				{
					TypeChatView.WEB_VIEW ->
					{
						val tData = if (item.isSecondaryQuery)
						{
							Triple(lls2, R.id.rvSplitView, tData2)
						}
						else
						{
							Triple(lls1, R.id.rlWebView, tData1)
						}
						tData.run {
							var childWebView = first.searchView(second)
							if (childWebView == null)
							{
								childWebView = getChildWebView(first.context, second)
								addView(first, childWebView)
							}
							ChildWebView(childWebView, item, tData.third)
						}
					}
				}
			}
		}
	}

	private fun View.searchView(id: Int): View?
	{
		return findViewById(id)
	}

	private fun addView(llRoot: LinearLayout, newView: View)
	{
		llRoot.run {
			removeAllViews()
			addView(newView)
		}
	}
//	when(typeView)
//	{
//		TypeChatView.LEFT_VIEW -> 3
//		TypeChatView.WEB_VIEW -> 4
//		TypeChatView.SUGGESTION_VIEW -> 5
//		else -> 2
//	}
}