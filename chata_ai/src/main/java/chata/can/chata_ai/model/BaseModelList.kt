package chata.can.chata_ai.model

import chata.can.chata_ai.holder.HolderContract
import chata.can.chata_ai.listener.OnItemClickListener

open class BaseModelList<Model: Any>
{
	private val aData = ArrayList<Model>()

	fun addData(data: Model)
	{
		aData.add(data)
	}

	fun addAllData(aData: ArrayList<Model>)
	{
		this.aData.addAll(aData)
	}

	fun clearData()
	{
		aData.clear()
	}

	open fun countData() = aData.size

	open fun getData(position: Int): Model?
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
		getData(position)?.let {
			view.onBind(it, listener)
		}
	}
}