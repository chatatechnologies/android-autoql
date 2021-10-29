package chata.can.chata_ai.dialog.manageData

import android.view.ViewGroup
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList

class FilterColumnAdapter(
	private val model: BaseModelList<FilterColumn>,
	private val dialogView: ManageDialogView,
	private val aCurrency1: ArrayList<FilterColumn>,
	private val aQuality1: ArrayList<FilterColumn>,
	private val aCategory: ArrayList<FilterColumn>
): BaseAdapter(model), FilterColumnView
{
	override fun getItemViewType(position: Int): Int
	{
		var viewType = 0
		model[position]?.let { item ->
			aCurrency1.lastOrNull()?.let {
				viewType = isFilterColumn(item, it)
			} ?: run {
				aQuality1.lastOrNull()?.let {
					viewType = isFilterColumn(item, it)
				}
			}
		}
		return viewType
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val view = FilterColumnHolder.getView(parent.context)
		return FilterColumnHolder(view, viewType, this)
	}

	override fun setIndexData(indexRoot: Int, indexCommon: Int)
	{
		dialogView.setIndexData(indexRoot, indexCommon)
	}

	override fun checkGroup(filterColumn: FilterColumn)
	{
		val indexCurrency = aCurrency1.indexOf(filterColumn)
		val indexQuality = aQuality1.indexOf(filterColumn)
		val indexCategory = aCategory.indexOf(filterColumn)
		if (indexCurrency != -1)
		{
			val found = aCurrency1[indexCurrency]
			val newValue = !found.isSelected
			val isEnable = !(hasSelected(aCurrency1) && !newValue)
			dialogView.statusApply(isEnable)
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
			val isEnable = !(hasSelected(aQuality1) && !newValue)
			dialogView.statusApply(isEnable)
			found.isSelected = newValue
			notifyItemChanged(indexQuality + aCurrency1.size + 2)
			if (newValue && aCurrency1.any { it.isSelected })
			{
				updateList(aCurrency1, 1)
			}
		}

		if (indexCategory != -1)
		{
			val found = aCategory[indexCategory]
			val newValue = !found.isSelected
			val isEnable = !(hasSelected(aQuality1) && !newValue)
			dialogView.statusApply(isEnable)

			found.isSelected = newValue
			notifyItemChanged(indexCategory + 1)
		}
	}

	private fun updateList(list: ArrayList<FilterColumn>, startIndex: Int)
	{
		for ((index, item) in list.withIndex())
		{
			item.isSelected = false
			notifyItemChanged(index + startIndex)
		}
	}

	private fun isFilterColumn(item: FilterColumn, compare: FilterColumn) = if (item == compare) 1 else 0

	private fun hasSelected(aList: ArrayList<FilterColumn>) =
		aList.filter { it.isSelected }.size == 1
}