package chata.can.chata_ai.fragment.dataMessenger.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.R
import chata.can.chata_ai.fragment.dataMessenger.holder.webView.WebViewHolder
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.ChatServicePresenter
import chata.can.chata_ai.fragment.dataMessenger.holder.*
import chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.QueryBuilderHolder
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.nullValue

class ChatAdapter(
	private val model: BaseModelList<*>,
	private val view: ChatContract.View,
	private val servicePresenter: ChatServicePresenter,
	private val pagerActivity: Activity
): BaseAdapter(model), ChatAdapterContract
{
	override fun getItemViewType(position: Int): Int
	{
		val chat = model[position]
		return if (chat is ChatData) chat.typeView else -1
	}

	override fun onBindViewHolder(holder: Holder, position: Int)
	{
		super.onBindViewHolder(holder, position)
		model[position]?.let {
			if (it is ChatData)
			{
				it.simpleQuery?.let {
						simpleQuery ->
					if (simpleQuery is QueryBase)
					{
						simpleQuery.checkData(holder)
					}
				}
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return when(viewType)
		{
			TypeChatView.RIGHT_VIEW ->
			{
				RightHolder(layoutInflater.inflate(R.layout.row_to_right, nullValue))
			}
			TypeChatView.WEB_VIEW ->
			{
				WebViewHolder(
					layoutInflater.inflate(R.layout.row_webview, nullValue), this, view)
			}
			TypeChatView.SUGGESTION_VIEW ->
			{
				SuggestionHolder(
					layoutInflater.inflate(R.layout.row_suggestion, nullValue),
					view,
					servicePresenter)
			}
			TypeChatView.FULL_SUGGESTION_VIEW ->
			{
				FullSuggestionHolder(
					layoutInflater.inflate(R.layout.row_full_suggestion, nullValue),
					view,
					servicePresenter)
			}
			TypeChatView.HELP_VIEW ->
			{
				HelpHolder(layoutInflater.inflate(R.layout.row_help, nullValue))
			}
			TypeChatView.QUERY_BUILDER ->
			{
				QueryBuilderHolder(
					layoutInflater.inflate(R.layout.row_query_builder, nullValue),
					pagerActivity
				)
			}
			else -> BaseHolder(layoutInflater.inflate(R.layout.row_base, nullValue), this, view)
		}
	}

	override fun deleteQuery(position: Int)
	{
		with(model)
		{
			removeAt(position)
			notifyItemChanged(position)
		}
	}
}