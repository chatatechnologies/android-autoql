package chata.can.chata_ai_api.test

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai_api.R

class BubbleFrame: FrameLayout, View.OnTouchListener
{
	private var initialX = 0f
	private var initialY = 0f
	private var _xDelta = 0f
	private var _yDelta = 0f
	private val touchTimeThreshold = 200
	private var lastTouchDown = 0L

	override fun onTouch(view: View, event: MotionEvent): Boolean
	{
		when(event.action)
		{
			MotionEvent.ACTION_DOWN ->
			{
				lastTouchDown = System.currentTimeMillis()
				initialX = view.x
				initialY = view.y
				_xDelta = view.x - event.rawX
				_yDelta = view.y - event.rawY
			}
			MotionEvent.ACTION_MOVE ->
			{
				view.animateChild(event.rawX + _xDelta, event.rawY + _yDelta)
			}
			MotionEvent.ACTION_UP ->
			{
				if (System.currentTimeMillis() - lastTouchDown < touchTimeThreshold)
				{
					view.animateChild(initialX, initialY)
					Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
				}
			}
		}
		return true
	}

	private fun View.animateChild(valX: Float, valY: Float)
	{
		animate()
			.x(valX)
			.y(valY)
			.setDuration(0)
			.start()
	}

	private fun init()
	{
		viewChild = TextView(context).apply {
			layoutParams = LayoutParams(dpToPx(64f),dpToPx(64f)).apply {
				x = 64f
			}
			setBackgroundColor(context.getParsedColor(R.color.blue_chata_circle))
			setOnTouchListener(this@BubbleFrame)
		}
		viewChild?.let { addView(it) }
	}

	constructor(context: Context): super(context) { init() }

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) { init() }

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr) { init() }

	//region View
	private var viewChild: TextView ?= null
	//endregion
}