package chata.can.chata_ai.dialog.manageData

import android.view.ViewGroup
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList

class FilterColumnAdapter(
	model: BaseModelList<FilterColumn>,
	private val aCurrency1: ArrayList<FilterColumn>,
	private val aQuality1: ArrayList<FilterColumn>
): BaseAdapter(model), FilterColumnView
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val view = FilterColumnHolder.getView(parent.context)
		return FilterColumnHolder(view, this)
	}

	override fun checkGroup(filterColumn: FilterColumn)
	{
		aCurrency1.find { it == filterColumn }?.let { found ->
			found.isSelected = true
			if (aQuality1.any { it.isSelected })
			{
				updateList(aQuality1, aCurrency1.size + 2)
			}
		} ?: run {
			aQuality1.find { it == filterColumn }?.let { found ->
				found.isSelected = true
				if (aCurrency1.any { it.isSelected })
				{
					updateList(aCurrency1, 1)
				}
			}
		}
	}

	private fun updateList(list: ArrayList<FilterColumn>, startIndex: Int)
	{
		for (index in list.indices)
		{
			list[index].isSelected = false
			notifyItemChanged(index + startIndex)
		}
	}
}