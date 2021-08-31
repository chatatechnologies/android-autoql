package chata.can.chata_ai.dialog.manageData

import android.view.ViewGroup
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList

class FilterColumnAdapter(
	private val model: BaseModelList<FilterColumn>,
	private val dialogView: ManageDialogView,
	private val aCurrency1: ArrayList<FilterColumn>,
	private val aQuality1: ArrayList<FilterColumn>
): BaseAdapter(model), FilterColumnView
{
	override fun getItemViewType(position: Int): Int
	{
		var viewType = 0
		val last = aCurrency1.last()
		val item = model[position]
		if (item == last)
		{
			viewType = 1
		}
		else
		{
			val last2 = aQuality1.last()
			if (item == last2)
			{
				viewType = 1
			}
		}
		return viewType
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val view = FilterColumnHolder.getView(parent.context)
		return FilterColumnHolder(view, viewType, this)
	}

	override fun checkGroup(filterColumn: FilterColumn)
	{
		val indexCurrency = aCurrency1.indexOf(filterColumn)
		val indexQuality = aQuality1.indexOf(filterColumn)
		if (indexCurrency != -1)
		{
			val found = aCurrency1[indexCurrency]
			val newValue = !found.isSelected
			if (hasSelected(aCurrency1) && !newValue) //return
				dialogView.statusApply(false)
			else dialogView.statusApply(true)
			found.isSelected = newValue
			notifyItemChanged(indexCurrency + 1)
			if (newValue && aQuality1.any { it.isSelected })
			{
				updateList(aQuality1, aCurrency1.size + 2)
			}
		}

		if (indexQuality != -1)
		{
			val found = aQuality1[indexQuality]
			val newValue = !found.isSelected
			if (hasSelected(aQuality1) && !newValue) // return
				dialogView.statusApply(false)
			else dialogView.statusApply(true)
			found.isSelected = newValue
			notifyItemChanged(indexQuality + aCurrency1.size + 2)
			if (newValue && aCurrency1.any { it.isSelected })
			{
				updateList(aCurrency1, 1)
			}
		}
	}

	private fun updateList(list: ArrayList<FilterColumn>, startIndex: Int)
	{
		for (index in list.indices)
		{
			list[index].run {
				isSelected = false
			}
			notifyItemChanged(index + startIndex)
		}
	}

	private fun hasSelected(aList: ArrayList<FilterColumn>) =
		aList.filter { it.isSelected }.size == 1
}