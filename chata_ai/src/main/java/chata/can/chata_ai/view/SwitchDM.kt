package chata.can.chata_ai.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class SwitchDM: RelativeLayout, View.OnClickListener
{
	private var _isActive = false
	private val aText = arrayOf("Q&A On", "Q&A Off")
	private lateinit var disableListener: () -> Unit
	private lateinit var enableListener: () -> Unit
	private lateinit var _parent: View
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

	private fun init()
	{
		_parent = RelativeLayout(context).apply {
			layoutParams = LayoutParams(-2, -2)
			background = DrawableBuilder.setGradientDrawable(
				context.getParsedColor(R.color.chata_drawer_background_color_dark),
				56f
			)
			//region view
			_view = View(context).apply {
				id = R.id.toggleSwitch
				layoutParams = LayoutParams(dpToPx(21f), dpToPx(21f)).apply {
					addRule(CENTER_VERTICAL)
				}
				background = DrawableBuilder.setOvalDrawable(
					context.getParsedColor(R.color.white)
				)
				margin(start = 8f, end = 8f)
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
				paddingAll(left = 21f, right = 21f, top = 3f, bottom = 3f)
				setTextColor(context.getParsedColor(R.color.white))
				textSize(18f)
				text = aText[1]
			}
			addView(_tv)
			//endregion
			setOnClickListener(this@SwitchDM)
		}
		addView(_parent)
	}

	fun setCallbackEventChanged(disableListener: () -> Unit, enableListener: () -> Unit)
	{
		this.disableListener = disableListener
		this.enableListener = enableListener
	}

	fun setText(text: String, STATUS_SWITCH: Int)
	{
		aText[STATUS_SWITCH] = text
		updateText()
	}

	private fun hasActive()
	{
		_isActive = !_isActive
		if (_isActive) enableListener() else disableListener()

		val widthTv = _tv.measuredWidth.toFloat()
		val widthView = _view.measuredWidth.toFloat()

		val moveX = if (_isActive) widthTv - widthView - dpToPx(4f) else 0f
		_view.setAnimator(moveX,"translationX")

		val moveX2 = if (_isActive) -widthView else 0f
		_tv.setAnimator(moveX2,"translationX")

		updateText()
		updateColor()
	}

	private fun updateColor()
	{
		val color = if (_isActive) R.color.blue_chata_circle
			else R.color.chata_drawer_background_color_dark
		_parent.background = DrawableBuilder.setGradientDrawable(context.getParsedColor(color), 56f)
	}

	private fun updateText()
	{
		_tv.text = if (_isActive) aText[0] else aText[1]
	}
}
