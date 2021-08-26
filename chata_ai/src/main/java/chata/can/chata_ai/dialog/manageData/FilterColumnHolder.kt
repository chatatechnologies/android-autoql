package chata.can.chata_ai.dialog.manageData

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.margin
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
				//region selection view
				addView(RelativeLayout(context).apply {
					layoutParams = RelativeLayout.LayoutParams(dpToPx(18f), dpToPx(18f)).apply {
						val blue = context.getParsedColor(R.color.selected_gray)
						setBackgroundColor(blue)
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					id = R.id.cbBorder
					//region
					addView(View(context).apply {
						layoutParams = RelativeLayout.LayoutParams(-1, -1)
						id = R.id.cbColumn
						val gray = context.getParsedColor(R.color.blue_chata_circle)
						setBackgroundColor(gray)
						margin(2f, 2f, 2f, 2f)
					})
					//endregion
				})
				//endregion
				//region CheckBox
//				addView(CheckBox(ContextThemeWrapper(context, R.style.checkBoxStyle)).apply {
//					layoutParams = RelativeLayout.LayoutParams(-2, -2).apply {
//						addRule(RelativeLayout.ALIGN_PARENT_END)
//					}
//					id = R.id.cbCheck
//				})
				//endregion
			}
		}
	}

	private val rlParent = itemView.findViewById<View>(R.id.rlParent) ?: null
	private val tvColumnName = itemView.findViewById<TextView>(R.id.tvColumnName) ?: null
	private val cbBorder = itemView.findViewById<View>(R.id.cbBorder) ?: null
	private val cbColumn = itemView.findViewById<View>(R.id.cbColumn) ?: null
	//private val cbCheck = itemView.findViewById<CheckBox>(R.id.cbCheck) ?: null

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		(item as? FilterColumn)?.run {
			tvColumnName?.text = nameColumn
			cbBorder?.let {
				it.visibility = if (isOnlyText) View.GONE else View.VISIBLE
			}
			cbColumn?.let {
				val checkColor = it.context.getParsedColor(
					if (isSelected) R.color.blue_chata_circle else R.color.selected_gray)
				it.setBackgroundColor(checkColor)
				it.setOnClickListener {
					adapterView.checkGroup(item)
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