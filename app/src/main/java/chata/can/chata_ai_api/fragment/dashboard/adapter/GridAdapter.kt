package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class GridAdapter(private val model: BaseModelList<*>): BaseAdapter(model)
{
	override fun onBindViewHolder(holder: Holder, position: Int)
	{
		super.onBindViewHolder(holder, position)
		//For holders
		//8 For execute; this is else
		//7 For reload queries
		//0 > use views used in holders

//		model[position]?.let {
//			if (it is Dashboard)
//			{
//				it.queryBase?.checkData(holder)
//			}
//		}
	}

	override fun getItemViewType(position: Int): Int
	{
		return model[position]?.let {

			8
		} ?: 8
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return when(viewType)
		{
			7 -> {
				HolderSingle(
					layoutInflater.inflate(
						R.layout.row_holder_single,
						nullParent)
				)
			}
			8 ->
			{
				HolderExecute(
					layoutInflater.inflate(
						R.layout.row_dashboard_execute,
						nullParent)
				)
			}
			else -> HolderSingle(
				layoutInflater.inflate(
					R.layout.row_holder_single,
					nullParent)
			)
		}
	}
}