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

	override fun checkGroup(filterColumn: FilterColumn, isChecked: Boolean)
	{
		aCurrency1.find { it == filterColumn }?.let { found ->
			found.isSelected = isChecked
			if (isChecked && aQuality1.any { it.isSelected })
			{
				updateList(aQuality1, aCurrency1.size + 2)
			}
			aCurrency1.toString()
		} ?: run {
			aQuality1.find { it == filterColumn }?.let { found ->
				found.isSelected = isChecked
				if (isChecked && aCurrency1.any { it.isSelected })
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
			list[index].run {
				isSelected = false
				ignoreUpdate = true
			}
			notifyItemChanged(index + startIndex)
		}
		list.toString()
	}
}