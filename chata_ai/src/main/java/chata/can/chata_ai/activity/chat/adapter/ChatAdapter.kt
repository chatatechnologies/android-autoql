package chata.can.chata_ai.activity.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.ChatContract
import chata.can.chata_ai.activity.chat.ChatServicePresenter
import chata.can.chata_ai.activity.chat.holder.FullSuggestionHolder
import chata.can.chata_ai.activity.chat.holder.RightHolder
import chata.can.chata_ai.activity.chat.holder.SuggestionHolder
import chata.can.chata_ai.activity.chat.holder.WebViewHolder
import chata.can.chata_ai.adapter.BaseAdapter
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
	private val servicePresenter: ChatServicePresenter): BaseAdapter(model)
{
	override fun getItemViewType(position: Int): Int
	{
		val chat = model[position]
		return if (chat is ChatData) chat.typeView else -1
	}

	override fun onBindViewHolder(holder: Holder, position: Int)
	{
		super.onBindViewHolder(holder, position)
		(model[position] as? QueryBase)?.checkData(holder)
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
				WebViewHolder(layoutInflater.inflate(R.layout.row_webview, nullValue))
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
			else -> BaseHolder(layoutInflater.inflate(R.layout.row_base, nullValue))
		}
	}
}