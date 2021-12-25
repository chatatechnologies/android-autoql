package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.adapter

import android.view.ViewGroup
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.BaseModelList

class OptionAdapter(
	model: BaseModelList<*>,
	listener: OnItemClickListener? = null
): BaseAdapter(model, listener)
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		return OptionHolder(
			//layoutInflater.inflate(R.layout.row_option, nullParent)
			OptionDesign.getRowOption(parent.context)
		)
	}
}