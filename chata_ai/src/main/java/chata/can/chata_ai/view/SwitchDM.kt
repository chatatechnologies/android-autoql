package chata.can.chata_ai.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class SwitchDM: RelativeLayout, View.OnClickListener
{
	companion object {
		val TEXT_ON = 1
		val TEXT_OFF = 0
	}
	private var _isActive = false
	private val aText = arrayOf("Q&A On", "Q&A Off")
	private lateinit var _view: View
	private lateinit var _tv: TextView

	constructor(context: Context): super(context) { init() }

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) { init() }

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr) { init() }

	override fun onClick(v: View?)
	{
		hasActive()
	}

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
				_view = View(context).apply {
					id = R.id.toggleSwitch
					layoutParams = LayoutParams(dpToPx(21f), dpToPx(21f)).apply {
						addRule(CENTER_VERTICAL)
					}
					background = DrawableBuilder.setOvalDrawable(
						context.getParsedColor(R.color.blue_chata_circle)
					)
					margin(start = 8f, end = 8f)
					setOnClickListener(this@SwitchDM)
				}
				addView(_view)
				//endregion
				//region
				_tv = TextView(context).apply {
					id = R.id.statusSwitch
					layoutParams = LayoutParams(-2, -2).apply {
						addRule(ALIGN_START, R.id.toggleSwitch)
					}
					margin(4f)
					//setText("Q&A On")
					paddingAll(left = 21f, right = 21f, top = 3f, bottom = 3f)
					setTextColor(context.getParsedColor(R.color.white))
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
					text = aText[0]
				}
				addView(_tv)
				//endregion
			}
		)
	}

	fun setText(text: String, STATUS_SWITCH: Int)
	{
		aText[STATUS_SWITCH] = text
		updateText()
	}

	private fun hasActive()
	{
		_isActive = !_isActive
		println("Value is: $_isActive")
		updateText()
	}

	private fun updateText()
	{
		_tv.text = if (_isActive) aText[0] else aText[1]
	}
}