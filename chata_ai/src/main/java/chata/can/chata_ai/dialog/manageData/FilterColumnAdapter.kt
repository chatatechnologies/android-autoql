package chata.can.chata_ai.dialog.manageData

import android.view.ViewGroup
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList

class FilterColumnAdapter(
	private val model: BaseModelList<FilterColumn>,
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
		val indexCurrency = aCurrency1.indexOf(filterColumn)
		val indexQuality = aQuality1.indexOf(filterColumn)
		if (indexCurrency != -1)
		{
			val found = aCurrency1[indexCurrency]
			val newValue = !found.isSelected
			found.isSelected = newValue
			notifyItemChanged(indexCurrency + 1)
			if (newValue && aQuality1.any { it.isSelected })
			{
				toString()
//				updateList(aQuality1, aCurrency1.size + 2)
			}
//			aCurrency1.toString()
		}

		if (indexQuality != -1)
		{
			val found = aQuality1[indexQuality]
			val newValue = !found.isSelected
			found.isSelected = newValue
			notifyItemChanged(aCurrency1.size + 2)
			if (newValue && aCurrency1.any { it.isSelected })
			{
				toString()
//				updateList(aCurrency1, 1)
			}
//			aQuality1.toString()
		}
	}

	private fun updateList(list: ArrayList<FilterColumn>, startIndex: Int)
	{
		for (index in list.indices)
		{
			list[index].run {
//				ignoreUpdate = !isSelected
				isSelected = false
			}
			notifyItemChanged(index + startIndex)
			println("index updated->${index + startIndex}")
		}
	}
}