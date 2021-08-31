package chata.can.chata_ai.dialog.manageData

import android.content.Context
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

						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					id = R.id.cbBorder
					//region
					addView(TextView(context).apply {
						layoutParams = RelativeLayout.LayoutParams(-1, -1)
						id = R.id.cbColumn
						marginAll(2f)
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
			ThemeColor.currentColor.run {
				rlParent?.let { parent ->
					val blue = parent.context.getParsedColor(R.color.blue_chata_circle)
					val white = parent.context.getParsedColor(R.color.white)
					val black = parent.context.getParsedColor(R.color.black)

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
				}
			}

			rlParent?.let {

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
		cbColumn?.let {
			it.gravity = Gravity.CENTER
			it.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
		}
	}
}