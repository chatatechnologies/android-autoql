package chata.can.chata_ai_api.second

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai_api.R

class GridAdapter(
	private val model: BaseModelList<*>
): BaseAdapter(model)
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		println("VISTA")
		val layoutInflater = LayoutInflater.from(parent.context)
		return HolderSingle(layoutInflater.inflate( R.layout.row_holder_single, null))
	}
}