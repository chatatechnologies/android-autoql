package chata.can.chata_ai.dialog.hideColumn

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.color.ThemeColor

class ColumnHolder(
	itemView: View,
	private val view: ColumnChanges.SingleColumn): Holder(itemView)
{
	private val tvColumnName = itemView.findViewById<TextView>(R.id.tvColumnName) ?: null
	private val cbCheck = itemView.findViewById<CheckBox>(R.id.cbCheck) ?: null

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		item?.let {
			(it as? ColumnQuery)?.let { column ->
				tvColumnName?.text = column.displayName
				cbCheck?.isChecked = column.isVisible
				cbCheck?.setOnCheckedChangeListener { _, value ->
					view.changeVisible(adapterPosition, value)
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