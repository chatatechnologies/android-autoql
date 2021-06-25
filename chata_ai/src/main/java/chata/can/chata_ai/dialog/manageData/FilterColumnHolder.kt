package chata.can.chata_ai.dialog.manageData

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener

class FilterColumnHolder(view: View): Holder(view)
{
	companion object {
		fun getView(context: Context): RelativeLayout
		{
			return RelativeLayout(context).apply {
				layoutParams = RelativeLayout.LayoutParams(-1, -2).apply {
					addRule(RelativeLayout.CENTER_VERTICAL)
					addRule(RelativeLayout.START_OF, R.id.cbCheck)
				}
				paddingAll(left = 12f, right = 12f)
				//region TextView
				addView(TextView(context).apply {
					layoutParams = RelativeLayout.LayoutParams(-1, -2)
					id = R.id.tvColumnName
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
				})
				//endregion
				//region CheckBox
				addView(CheckBox(ContextThemeWrapper(context, R.style.checkBoxStyle)).apply {
					layoutParams = RelativeLayout.LayoutParams(-2, -2).apply {
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					id = R.id.cbCheck
				})
				//endregion
			}
		}
	}

	private val tvColumnName = itemView.findViewById<TextView>(R.id.tvColumnName) ?: null
	private val cbCheck = itemView.findViewById<CheckBox>(R.id.cbCheck) ?: null

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		(item as? FilterColumn)?.run {
			tvColumnName?.text = nameColumn
		}
	}

	override fun onPaint() {}
}