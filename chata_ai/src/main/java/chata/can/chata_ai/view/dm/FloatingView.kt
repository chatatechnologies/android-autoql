package chata.can.chata_ai.view.dm

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.pojo.BubbleData
import chata.can.chata_ai.pojo.BubbleData.heightDefault
import chata.can.chata_ai.pojo.BubbleData.marginLeftDefault
import chata.can.chata_ai.pojo.BubbleData.widthDefault
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.circle.CircleImageView

class FloatingView: FrameLayout, View.OnTouchListener
{
	fun setEventClick(listener: () -> Unit)
	{
		eventClick = listener
	}

	fun updatePositionOnScreen(
		placement: Int = ConstantDrawer.RIGHT_PLACEMENT
	)
	{
		when(placement)
		{
			ConstantDrawer.TOP_PLACEMENT ->
			{
				_xDelta = centerX()
				_yDelta = 0f
			}
			ConstantDrawer.BOTTOM_PLACEMENT ->
			{
				_xDelta = centerX()
				_yDelta = measuredHeightFixed()
			}
			ConstantDrawer.LEFT_PLACEMENT ->
			{
				_xDelta = 0f
				_yDelta = centerY()
			}
			ConstantDrawer.RIGHT_PLACEMENT ->
			{
				_xDelta = measuredWidthFixed()
				_yDelta = centerY()
			}
			ConstantDrawer.NOT_PLACEMENT ->
			{
				_xDelta = -1f
				_yDelta = -1f
			}
		}
		viewChild?.animateChild(
			_xDelta,
			_yDelta)
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
					if (::eventClick.isInitialized)
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
			newY = measuredHeightFixed()
		}
		animate()
			.x(valX)
			.y(newY)
			.setDuration(0)
			.start()
	}

	private fun measuredHeightFixed() = (measuredHeight - sizeChild).toFloat()
	private fun centerY() = ((measuredHeight - sizeChild) / 2).toFloat()
	private fun measuredWidthFixed() = (measuredWidth - sizeChild).toFloat()
	private fun centerX() = ((measuredWidth - sizeChild) / 2).toFloat()

	private fun init()
	{
		viewChild = RelativeLayout(context).apply {
			sizeChild = heightDefault
			layoutParams = LayoutParams(-2, -2)
			val alphaColor = ColorUtils.setAlphaComponent(
				ThemeColor.lightColor.pDrawerTextColorPrimary, (0.25f * 255).toInt())
			val drawable = DrawableBuilder.setOvalDrawable(alphaColor)
			background = drawable
		}
//		viewChild = TextView(context).apply {
//			sizeChild = dpToPx(64f)
//			layoutParams = LayoutParams(sizeChild, sizeChild).apply {
//
//			}
//			setBackgroundColor(context.getParsedColor(R.color.blue_chata_circle))
//			setOnTouchListener(this@FloatingView)
//		}

		val circleImageView = CircleImageView(context).apply {
			layoutParams = LayoutParams(heightDefault, widthDefault)
			margin(marginLeftDefault, marginLeftDefault, marginLeftDefault, marginLeftDefault)
			setImageResource(R.drawable.ic_bubble_chata)
			setCircleBackgroundColorResource(R.color.blue_chata_circle)
			setOnTouchListener(this@FloatingView)
		}
		viewChild?.let {
			it.addView(circleImageView)
			addView(it)
		}
		post {
			updatePositionOnScreen()
		}
	}

	constructor(context: Context): super(context) { init() }

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) { init() }

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr) { init() }

	//region property
	var placement = ConstantDrawer.RIGHT_PLACEMENT
	set(value) {
		if (placement != value && placement > 0)
		{
			field = value
			updatePositionOnScreen(value)
		}
	}
	//endregion

	//region View
	private var viewChild: RelativeLayout ?= null
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