package chata.can.chata_ai.model

import chata.can.chata_ai.holder.HolderContract
import chata.can.chata_ai.listener.OnItemClickListener

open class BaseModelList<Model: Any>
{
	private val aData = ArrayList<Model>()

	fun add(data: Model)
	{
		aData.add(data)
	}

	fun addAll(aData: ArrayList<Model>)
	{
		this.aData.addAll(aData)
	}

	fun clear()
	{
		aData.clear()
	}

	fun removeAt(index: Int)
	{
		aData.removeAt(index)
	}

	open fun countData() = aData.size

	operator fun get(position: Int): Model?
	{
		return if (position < aData.size)
		{
			aData[position]
		}
		else null
	}

	open fun onBindAtPosition(
		view: HolderContract,
		position: Int,
		listener: OnItemClickListener ?= null
	)
	{
		get(position)?.let {
			view.onBind(it, listener)
		}
	}
}