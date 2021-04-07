package chata.can.chata_ai_api.test

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai_api.R

class BubbleFrame: FrameLayout, View.OnTouchListener
{
	fun setEventClick(listener: () -> Unit)
	{
		eventClick = listener
	}

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
					eventClick()
				}
				else
					goToWall()
			}
		}
		return true
	}

	private fun goToWall()
	{
		viewChild?.let { viewChild ->
			val width = measuredWidthFixed()
			val middle = width / 2
			val nearestXWall = if (viewChild.x >= middle) width.toFloat() else 0f
			viewChild.animateChild(nearestXWall, viewChild.y)
		}
	}

	private fun View.animateChild(valX: Float, valY: Float)
	{
		var newY = valY
		if (valY < 0)
		{
			newY = 0f
		}
		if (valY > measuredHeightFixed())
		{
			newY = measuredHeightFixed().toFloat()
		}
		animate()
			.x(valX)
			.y(newY)
			.setDuration(0)
			.start()
	}

	private fun measuredHeightFixed() = measuredHeight - sizeChild
	private fun measuredWidthFixed() = measuredWidth - sizeChild

	private fun init()
	{
		viewChild = TextView(context).apply {
			sizeChild = dpToPx(64f)
			layoutParams = LayoutParams(sizeChild, sizeChild).apply {

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
	private var sizeChild = 0
	private lateinit var eventClick: () -> Unit

	private var initialX = 0f
	private var initialY = 0f
	private var _xDelta = 0f
	private var _yDelta = 0f
	private val touchTimeThreshold = 200
	private var lastTouchDown = 0L
	//endregion
}