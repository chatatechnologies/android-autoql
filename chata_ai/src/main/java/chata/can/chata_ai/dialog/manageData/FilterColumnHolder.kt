package chata.can.chata_ai.dialog.manageData

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class FilterColumnHolder(
	view: View,
	private val isLast: Int,
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
				paddingAll(left = 12f, top = 6f, right = 12f)
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
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					id = R.id.cbBorder
					//region
					addView(TextView(context).apply {
						layoutParams = RelativeLayout.LayoutParams(-1, -1)
						id = R.id.cbColumn
						marginAll(1f)
					})
					//endregion
				})
				//endregion
				addView(View(context).apply {
					layoutParams = RelativeLayout.LayoutParams(-1, dpToPx(0.5f)).apply {
						addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
					}
					margin(0f, 4f, 0f, 4f)
					id = R.id.vBorder
				})
			}
		}
	}

	private val rlParent = itemView.findViewById<View>(R.id.rlParent) ?: null
	private val tvColumnName = itemView.findViewById<TextView>(R.id.tvColumnName) ?: null
	private val cbBorder = itemView.findViewById<View>(R.id.cbBorder) ?: null
	private val cbColumn = itemView.findViewById<TextView>(R.id.cbColumn) ?: null
	private val vBorder = itemView.findViewById<View>(R.id.vBorder) ?: null

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		(item as? FilterColumn)?.run {
			ThemeColor.currentColor.run {
				rlParent?.let { parent ->
					val blue = parent.context.getParsedColor(R.color.blue_chata_circle)

					if (isOnlyText && indexColumn != -1)
						parent.setBackgroundColor(if (isSelected) {
							pDrawerColorSecondary
						} else {
							pDrawerBackgroundColor
						})

					cbBorder?.let {
						it.visibility = if (isOnlyText) View.GONE else View.VISIBLE
						it.background = DrawableBuilder.setGradientDrawable(blue, 6f)
					}

					cbColumn?.let {
						//icon check gone
						val pData = if (isSelected) Pair(blue, "\u2713")else Pair(pDrawerBackgroundColor, "")
						it.setBackgroundColor(pData.first)
						it.text = pData.second
						it.setOnClickListener {
							adapterView.checkGroup(item)
						}
					}

					tvColumnName?.let {
						val color = if (isOnlyText) pDrawerTextColorPrimary else pHighlightColor
						it.setTextColor(color)
						it.text = nameColumn
						it.paddingAll(left = if (!isOnlyText && !allowClick) 8f else 0f)
					}

					vBorder?.let {
						it.setBackgroundColor(pDrawerTextColorPrimary)
						it.visibility = if (isOnlyText || isLast == 1)
							View.GONE
						else View.VISIBLE

					}

					parent.setOnClickListener {
						if (item.allowClick)
						{
							val name = item.nameColumn
							val indexColumn = item.indexColumn
							Log.e("Manage", "I can: ${item.indexColumn}")
							//Here to select with simple dialog
						}
					}
				}
			}
		}
	}

	override fun onPaint()
	{
		cbColumn?.let {
			it.gravity = Gravity.CENTER
			it.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
		}
	}
}