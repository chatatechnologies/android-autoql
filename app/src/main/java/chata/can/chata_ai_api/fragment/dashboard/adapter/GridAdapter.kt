package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.DashboardView.getRowContent
import chata.can.chata_ai_api.DashboardView.getRowExecute
import chata.can.chata_ai_api.DashboardView.getRowLoading
import chata.can.chata_ai_api.DashboardView.getRowSuggestion
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.DashboardPresenter
import chata.can.chata_ai_api.fragment.dashboard.holder.*
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
						viewType = if (query.isEmpty()) {
							8
						}
						else 1
					}
				}
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
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return when(viewType)
		{
			0 -> ExecuteHolder(getRowExecute(parent.context))
			1 -> LoadingHolder(getRowLoading(parent.context))
			2 -> SupportHolder(getRowContent(parent.context))
			3 -> ContentHolder(getRowContent(parent.context))
			4 -> WebViewHolder(layoutInflater.inflate(R.layout.row_holder_web_view, nullParent))
			5 -> SuggestionHolder(getRowSuggestion(parent.context), presenter)
			8 -> NoQueryHolder(getRowExecute(parent.context))
			else -> ExecuteHolder(getRowExecute(parent.context))
		}
	}
}