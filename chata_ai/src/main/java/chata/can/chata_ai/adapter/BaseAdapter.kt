package chata.can.chata_ai.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.BaseHolder
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.nullValue

open class BaseAdapter(
	private val model: BaseModelList<*>,
	private val listener: OnItemClickListener? = null
) :RecyclerView.Adapter<Holder>()
{
	override fun onBindViewHolder(holder: Holder, position: Int)
	{
		holder.onPaint()
		model.onBindAtPosition(holder, position, listener)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return BaseHolder(layoutInflater.inflate(R.layout.row_base, nullValue))
	}

	override fun getItemCount() = model.countData()
}