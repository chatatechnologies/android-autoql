package chata.can.chata_ai.holder

import chata.can.chata_ai.listener.OnItemClickListener

interface HolderContract
{
	fun onBind(item: Any? = null, listener: OnItemClickListener? = null)
}