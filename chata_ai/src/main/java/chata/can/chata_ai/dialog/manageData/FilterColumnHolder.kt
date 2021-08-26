package chata.can.chata_ai.dialog.manageData

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor

class FilterColumnHolder(
	view: View,
	private val adapterView: FilterColumnView//variable should to change for View
): Holder(view)
{
	companion object {
		fun getView(context: Context): RelativeLayout
		{
			return RelativeLayout(context).apply {
				layoutParams = RelativeLayout.LayoutParams(-1, -2).apply {
					addRule(RelativeLayout.CENTER_VERTICAL)
					addRule(RelativeLayout.START_OF, R.id.cbCheck)
				}
				id = R.id.rlParent
				paddingAll(left = 12f, right = 12f)
				//region TextView
				addView(TextView(context).apply {
					layoutParams = RelativeLayout.LayoutParams(-1, dpToPx(24f))
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

	private val rlParent = itemView.findViewById<View>(R.id.rlParent) ?: null
	private val tvColumnName = itemView.findViewById<TextView>(R.id.tvColumnName) ?: null
	private val cbCheck = itemView.findViewById<CheckBox>(R.id.cbCheck) ?: null

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		(item as? FilterColumn)?.run {
			tvColumnName?.text = nameColumn
			cbCheck?.let {
				it.visibility =
					if (isOnlyText) View.GONE
					else
					{
						it.isChecked = isSelected
						View.VISIBLE
					}
				it.setOnCheckedChangeListener { _, isChecked ->
					if (item.ignoreUpdate)
						item.ignoreUpdate = false
					else
						adapterView.checkGroup(item, isChecked)
				}
			}
			rlParent?.let {
				if (isOnlyText && indexColumn != -1)
				{
					ThemeColor.currentColor.run {
						it.setBackgroundColor(if (isSelected) pDrawerColorSecondary else pDrawerBackgroundColor)
					}
				}
				it.setOnClickListener {
					if (item.allowClick)
					{
						Log.e("Manage", "I can: ${item.indexColumn}")
						//Here to select with simple dialog
					}
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