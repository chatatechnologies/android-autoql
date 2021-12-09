package chata.can.chata_ai.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.BaseModelList

open class BaseAdapter(
	private val model: BaseModelList<*>,
	private val listener: OnItemClickListener? = null
) :RecyclerView.Adapter<Holder>()
{
	override fun onBindViewHolder(holder: Holder, position: Int)
	{
		with(holder)
		{
			onPaint()
			model.onBindAtPosition(this, position, listener)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		return BaseHolder(BaseRow.getRowBase(parent.context))
	}

	override fun getItemCount(): Int
	{
		return model.countData()
	}
}