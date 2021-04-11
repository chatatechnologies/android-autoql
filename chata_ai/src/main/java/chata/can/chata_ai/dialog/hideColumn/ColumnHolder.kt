package chata.can.chata_ai.dialog.hideColumn

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor

class ColumnHolder(itemView: View): Holder(itemView)
{
	private val tvColumnName = itemView.findViewById<TextView>(R.id.tvColumnName) ?: null
	private val cbCheck = itemView.findViewById<CheckBox>(R.id.cbCheck) ?: null

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		item?.let {
			if (it is Pair<*,*>)
			{
				(it.first as? String)?.let { s ->
					tvColumnName?.text = s
				}
				(it.second as? Boolean)?.let { b ->
					cbCheck?.isChecked = b
				}
			}
		}
	}

	override fun onPaint()
	{
		ThemeColor.currentColor.run {
			tvColumnName?.setTextColor(pDrawerTextColorPrimary)
		}
	}
}