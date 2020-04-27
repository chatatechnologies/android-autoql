package chata.can.chata_ai_api.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai_api.R

class GridAdapter(model: BaseModelList<*>): BaseAdapter(model)
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return HolderSingle(
			layoutInflater.inflate(
				R.layout.row_holder_single,
				nullParent
			)
		)
	}
}