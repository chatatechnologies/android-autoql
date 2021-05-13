package chata.can.chata_ai.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class SwitchDM: RelativeLayout
{
	constructor(context: Context): super(context) { init() }

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) { init() }

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr) { init() }

	fun init()
	{
		addView(
			RelativeLayout(context).apply {
				layoutParams = LayoutParams(-2, -2)
				background = DrawableBuilder.setGradientDrawable(
					context.getParsedColor(R.color.red_notification),
					56f,
					1,
					context.getParsedColor(R.color.blue_chata_circle)
				)
				//region view
				val view = View(context).apply {
					id = R.id.toggleSwitch
					layoutParams = LayoutParams(dpToPx(16f), dpToPx(16f)).apply {
						addRule(CENTER_VERTICAL)
					}
					background = DrawableBuilder.setOvalDrawable(
						context.getParsedColor(R.color.blue_chata_circle)
					)
					margin(start = 4f, end = 4f)
				}
				addView(view)
				//endregion
				//region
				val tv = TextView(context).apply {
					id = R.id.statusSwitch
					layoutParams = LayoutParams(-2, -2).apply {
						addRule(ALIGN_START, R.id.toggleSwitch)
					}
					margin(4f)
					//setText("Q&A On")
					paddingAll(left = 18f, right = 18f, top = 3f, bottom = 3f)
					setTextColor(context.getParsedColor(R.color.white))
					text = "Q&A Off"
				}
				addView(tv)
				//endregion
			}
		)
	}
}