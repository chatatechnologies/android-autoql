package chata.can.chata_ai.dialog.hideColumn

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.R
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.nullValue

class ColumnAdapter(
	private val model: BaseModelList<*>
): BaseAdapter(model)
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return ColumnHolder(layoutInflater.inflate(R.layout.row_column, nullValue))
	}
}