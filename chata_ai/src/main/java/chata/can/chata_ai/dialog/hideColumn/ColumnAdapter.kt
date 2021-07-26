package chata.can.chata_ai.dialog.hideColumn

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.R
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.QueryBase

class ColumnAdapter(
	private val model: BaseModelList<ColumnQuery>,
	private val queryBase: QueryBase,
	private val view: ColumnChanges.AllColumn
): BaseAdapter(model), ColumnChanges.SingleColumn
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return ColumnHolder(
			layoutInflater.inflate(R.layout.row_column, parent, false), this)
	}

	override fun changeVisible(position: Int, value: Boolean)
	{
		model[position]?.let {
			if (it.isVisible != value)
			{
				it.isVisible = value
			}
		}
		view.changeAllColumn(hasSameStatus())
	}

	private fun hasSameStatus(): Boolean
	{
		model.getData().filter { it.isVisible }.run {
			return if (count() == model.countData()) first().isVisible else false
		}
	}

	fun hasChanges(): Boolean
	{
		var hasChanges = false
		val aCompare = queryBase.aColumn
		for (index in 0 until model.countData())
		{
			model[index]?.let { value ->
				if (value.isVisible != aCompare[index].isVisible)
				{
					aCompare[index].isVisible = value.isVisible
					hasChanges = true
				}
			}
		}
		return hasChanges
	}
}