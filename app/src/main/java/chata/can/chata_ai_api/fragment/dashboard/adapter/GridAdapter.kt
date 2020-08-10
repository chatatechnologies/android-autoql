package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.holder.*

class GridAdapter(
	private val model: BaseModelList<*>): BaseAdapter(model)
{
	override fun getItemViewType(position: Int): Int
	{
		/**
		 * 0 for start view (execute message)
		 * 1 for loading data (gifView)
		 * 2 for support message
		 * 3 for simple text data
		 * 4 for webView data
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
				it.queryBase2?.run {
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
			0 -> ExecuteHolder(layoutInflater.inflate(R.layout.row_holder_execute, nullParent))
			1 -> LoadingHolder(layoutInflater.inflate(R.layout.row_holder_loading, nullParent))
			2 -> SupportHolder(layoutInflater.inflate(R.layout.row_holder_content, nullParent))
			3 -> ContentHolder(layoutInflater.inflate(R.layout.row_holder_content, nullParent))
			4 -> WebViewHolder(layoutInflater.inflate(R.layout.row_holder_web_view, nullParent))
			8 -> NoQueryHolder(layoutInflater.inflate(R.layout.row_holder_execute, nullParent))
			else -> ExecuteHolder(layoutInflater.inflate(R.layout.row_holder_execute, nullParent))
		}
	}
}