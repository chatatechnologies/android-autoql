package chata.can.chata_ai.activity.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.notification.OnBottomReachedListener
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.nullValue

class NotificationAdapter(private val model: BaseModelList<*>): BaseAdapter(model)
{
	private var onBottomReachedListener: OnBottomReachedListener ?= null

	fun setOnBottomReachedListener(onBottomReachedListener: OnBottomReachedListener)
	{
		this.onBottomReachedListener = onBottomReachedListener
	}

	override fun onBindViewHolder(holder: Holder, position: Int)
	{
		if (position == model.countData() - 1)
		{
			onBottomReachedListener?.onBottomReachedListener(position)
		}
		super.onBindViewHolder(holder, position)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		val layoutInflater = LayoutInflater.from(parent.context)
		return NotificationHolder(layoutInflater.inflate(R.layout.row_notification, nullValue))
	}
}