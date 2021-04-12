package chata.can.chata_ai.dialog.hideColumn

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.R
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.nullValue

class ColumnAdapter(
	model: BaseModelList<*>,
	private val queryBase: QueryBase,
): BaseAdapter(model), ColumnChanges
{
	private var aColumn = ArrayList<ColumnQuery>()
	init {
		for (column in queryBase.aColumn)
		{
			aColumn.add(column.copy())
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return ColumnHolder(layoutInflater.inflate(R.layout.row_column, nullValue), this)
	}

	override fun changeVisible(position: Int, value: Boolean)
	{
		val isVisible = aColumn[position].isVisible
		if (isVisible != value)
		{
			aColumn[position].isVisible = value
		}
	}

	fun hasChanges(): Boolean
	{
		var hasChanges = false
		val aCompare = queryBase.aColumn
		for (index in 0 until aColumn.size)
		{
			if (aCompare[index].isVisible != aColumn[index].isVisible)
			{
				aCompare[index].isVisible = aColumn[index].isVisible
				hasChanges = true
			}
		}
		return hasChanges
	}
}