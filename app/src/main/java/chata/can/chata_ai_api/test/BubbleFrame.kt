package chata.can.chata_ai_api.test

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai_api.R

class BubbleFrame: FrameLayout
{
	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(event: MotionEvent?): Boolean
	{
		when(event?.action)
		{
			MotionEvent.ACTION_DOWN ->
			{

			}
			MotionEvent.ACTION_MOVE ->
			{

			}
			MotionEvent.ACTION_UP ->
			{

			}
		}
		return super.onTouchEvent(event)
	}

	private fun init()
	{
		val view = TextView(context).apply {
			layoutParams = FrameLayout.LayoutParams(dpToPx(64f),dpToPx(64f))
			setBackgroundColor(context.getParsedColor(R.color.blue_chata_circle))
		}
		addView(view)
	}

	private var params: WindowManager.LayoutParams ?= null

	constructor(context: Context): super(context) { init() }

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) { init() }

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr) { init() }
}