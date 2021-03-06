package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.R
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.BaseModelList

class QueryAdapter(
	private val model: BaseModelList<*>,
	listener: OnItemClickListener? = null
): BaseAdapter(model, listener)
{
	private var currentview = -1

	override fun getItemViewType(position: Int): Int
	{
		return if (position == model.countData() - 1) 1 else 0
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return QueryHolder(layoutInflater.inflate(R.layout.row_query, nullParent), viewType == 1)
	}

	fun checkBefore(position: Int)
	{
		if (position != currentview)
		{
			if (currentview != -1)
			{
				notifyItemChanged(currentview)
			}
			currentview = position
		}
	}
}