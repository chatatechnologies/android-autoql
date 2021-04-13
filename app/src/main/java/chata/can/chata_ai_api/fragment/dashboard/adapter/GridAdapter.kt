package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.view.ViewGroup
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.DashboardView.getRowContent
import chata.can.chata_ai_api.DashboardView.getRowExecute
import chata.can.chata_ai_api.DashboardView.getRowLoading
import chata.can.chata_ai_api.DashboardView.getRowSuggestion
import chata.can.chata_ai_api.DashboardView.getRowTwin
import chata.can.chata_ai_api.DashboardView.getRowWebView
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter
import chata.can.chata_ai_api.fragment.dashboard.holder.*
import chata.can.chata_ai_api.fragment.dashboard.holder.dynamic.DynamicHolder
import chata.can.chata_ai_api.fragment.dashboard.holder.suggestion.SuggestionHolder

class GridAdapter(
	private val model: BaseModelList<*>,
	private val presenter: DashboardPresenter
): BaseAdapter(model)
{
	override fun getItemViewType(position: Int): Int
	{
		/**
		 * 0 for start view (execute message)
		 * 1 for loading data (gifView)
		 * 2 for support message
		 * 3 for simple text data
		 * 4 for webView data
		 * 8 for no query data
		 */
		var viewType = 0
		model[position]?.run {
			if (this is Dashboard)
			{
				if (splitView)
				{
					viewType = 10
				}
				//region once QueryBase
				else
				{
					queryBase?.run {
						viewType = when(typeView)
						{
							TypeChatView.LEFT_VIEW -> 3
							TypeChatView.WEB_VIEW -> 4
							TypeChatView.SUGGESTION_VIEW -> 5
							else -> 2
						}
					} ?: run {
						if (isWaitingData)
						{
							viewType = if (query.isEmpty())
								8
							else 1
						}
					}
				}
				//endregion
			}
		}
		return viewType
	}

	override fun onBindViewHolder(holder: Holder, position: Int)
	{
		super.onBindViewHolder(holder, position)
		model[position]?.let {
			if (it is Dashboard)
			{
				it.queryBase?.run {
					checkData(holder)
				}
				it.queryBase2?.run {
					checkData(holder)
				}
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val context = parent.context
		return when(viewType)
		{
			0 -> ExecuteHolder(getRowExecute(context))
			1 -> LoadingHolder(getRowLoading(context))
			2 -> SupportHolder(getRowContent(context))//dynamic
			3 -> ContentHolder(getRowContent(context, true))//dynamic
			4 -> WebViewHolder(getRowWebView(context))
			5 -> SuggestionHolder(getRowSuggestion(context), presenter)//dynamic
			8 -> NoQueryHolder(getRowExecute(context))//dynamic
			10 -> DynamicHolder(getRowTwin(context), presenter)
			else -> ExecuteHolder(getRowExecute(context))
		}
	}
}