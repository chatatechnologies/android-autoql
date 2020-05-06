package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.holder.HolderSingle

class GridAdapter(private val model: BaseModelList<*>): BaseAdapter(model)
{
	override fun onBindViewHolder(holder: Holder, position: Int)
	{
		super.onBindViewHolder(holder, position)
		model[position]?.let {
			if (it is Dashboard)
			{
				it.queryBase?.checkData(holder)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return HolderSingle(
			layoutInflater.inflate(R.layout.row_holder_single, nullParent)
		)
	}
}