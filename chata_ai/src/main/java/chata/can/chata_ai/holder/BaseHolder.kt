package chata.can.chata_ai.holder

import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.listener.OnItemClickListener

class BaseHolder(view: View): Holder(view)
{
	private val tvContent: TextView = view.findViewById(R.id.tvContent)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{

	}
}