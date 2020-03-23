package chata.can.chata_ai.activity.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.holder.RightHolder
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.nullValue

class ChatAdapter(private val model: BaseModelList<*>): BaseAdapter(model)
{
	override fun getItemViewType(position: Int): Int
	{
		val chat = model[position]
		return if (chat is ChatData) chat.typeView else -1
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
			else -> BaseHolder(layoutInflater.inflate(R.layout.row_base, nullValue))
		}
	}
}