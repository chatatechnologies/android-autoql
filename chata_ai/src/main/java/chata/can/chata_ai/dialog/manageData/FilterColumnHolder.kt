package chata.can.chata_ai.dialog.manageData

import android.content.Context
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
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
					layoutParams = RelativeLayout.LayoutParams(-1, dpToPx(32f))
					id = R.id.tvColumnName
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
				})
				//endregion
				//region selection view
				addView(RelativeLayout(context).apply {
					layoutParams = RelativeLayout.LayoutParams(dpToPx(28f), dpToPx(28f)).apply {
						val blue = context.getParsedColor(R.color.selected_gray)
						setBackgroundColor(blue)
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					id = R.id.cbBorder
					//region
					addView(TextView(context).apply {
						layoutParams = RelativeLayout.LayoutParams(-1, -1)
						id = R.id.cbColumn
						val gray = context.getParsedColor(R.color.blue_chata_circle)
						setBackgroundColor(gray)
						margin(1f, 1f, 1f, 1f)
					})
					//endregion
				})
				//endregion
			}
		}
	}

	private val rlParent = itemView.findViewById<View>(R.id.rlParent) ?: null
	private val tvColumnName = itemView.findViewById<TextView>(R.id.tvColumnName) ?: null
	private val cbBorder = itemView.findViewById<View>(R.id.cbBorder) ?: null
	private val cbColumn = itemView.findViewById<TextView>(R.id.cbColumn) ?: null

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		(item as? FilterColumn)?.run {
			tvColumnName?.let {
				//var abc = "\u2713" + nameColumn
				//it.setText(Html.fromHtml(abc))
				it.text = nameColumn
				it.paddingAll(left = if (!isOnlyText && !allowClick) 8f else 0f)
			}
			cbBorder?.let {
				it.visibility = if (isOnlyText) View.GONE else View.VISIBLE
			}
			cbColumn?.let {
				val pData =
					if (isSelected) Pair(R.color.blue_chata_circle, "\u2713")
					else Pair(R.color.selected_gray, "")
				val checkColor = it.context.getParsedColor(pData.first)
				it.setBackgroundColor(checkColor)
				it.text = pData.second
				it.setOnClickListener {
					adapterView.checkGroup(this)
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
		cbColumn?.let {
			it.gravity = Gravity.CENTER
			it.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
		}
	}
}