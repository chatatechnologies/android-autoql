package chata.can.chata_ai_api.test

import android.content.Context
import android.graphics.Rect
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
	private var _xDelta = 0
	private var _yDelta = 0
//	private var lastTouchDown = 0L

	override fun onTouch(view: View, event: MotionEvent): Boolean
	{
		val x = event.x.toInt()
		val y = event.y.toInt()

		viewChild?.run {
			val rect = Rect()
			getLocalVisibleRect(rect)
			println("Left ${rect.left}")//
			println("Top ${rect.top}")//
//			println("Right ${rect.right}")
//			println("Bottom ${rect.bottom}")
		}

		when(event.action)
		{
			MotionEvent.ACTION_DOWN ->
			{
				(view.layoutParams as? FrameLayout.LayoutParams)?.run {
					_xDelta = x - leftMargin
					_yDelta = y - topMargin
				}
			}
			MotionEvent.ACTION_MOVE ->
			{
				(view.layoutParams as? FrameLayout.LayoutParams)?.run {
					leftMargin = x - _xDelta
					topMargin = y - _yDelta
					view.layoutParams = this
				}
			}
			MotionEvent.ACTION_UP ->
			{
				//Read click
			}
		}
		invalidate()
		return true
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