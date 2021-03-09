package chata.can.chata_ai.view.dm

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.core.graphics.ColorUtils
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.pojo.BubbleData.heightDefault
import chata.can.chata_ai.pojo.BubbleData.marginLeftDefault
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.circle.CircleImageView

class FloatingView: FrameLayout
{
	fun setEventClick(listener: () -> Unit)
	{
		eventClick = listener
	}

	private fun updatePositionOnScreen(placement: Int = ConstantDrawer.RIGHT_PLACEMENT)
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
		viewChild?.animateChild(_xDelta, _yDelta)
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

	private fun factorSizeMargin(factor: Int) = (factor - sizeChild - (dpToPx(marginLeftDefault) * 2)).toFloat()
	private fun measuredHeightFixed() = factorSizeMargin(heightPixels)
	private fun centerY() = measuredHeightFixed() / 2
	private fun measuredWidthFixed() = factorSizeMargin(widthPixels)
	private fun centerX() = measuredWidthFixed() / 2

	private var widthPixels = 0
	private var heightPixels = 0

	private fun init()
	{
//		setBackgroundColor(R.color.red_notification)

		viewChild = RelativeLayout(context).apply {
			sizeChild = heightDefault
			layoutParams = LayoutParams(-2, -2)
			val alphaColor = ColorUtils.setAlphaComponent(
				ThemeColor.lightColor.pDrawerTextColorPrimary, (0.25f * 255).toInt())
			val drawable = DrawableBuilder.setOvalDrawable(alphaColor)
			background = drawable
			setOnClickListener {
				if (::eventClick.isInitialized)
					eventClick()
			}
		}

		val circleImageView = CircleImageView(context).apply {
			layoutParams = LayoutParams(sizeChild, sizeChild)
			margin(marginLeftDefault, marginLeftDefault, marginLeftDefault, marginLeftDefault)
			setImageResource(R.drawable.ic_bubble_chata)
			setCircleBackgroundColorResource(R.color.blue_chata_circle)
		}
		viewChild?.let {
			it.addView(circleImageView)
			addView(it)
		}
		post {
			heightPixels = measuredHeight
			widthPixels = measuredWidth
			updatePositionOnScreen()
		}
	}
	//region property
	var placement = ConstantDrawer.RIGHT_PLACEMENT
	set(value) {
		if (placement != value && value > 0)
		{
			field = value
			updatePositionOnScreen(value)
		}
	}
	//endregion
	constructor(context: Context): super(context) { init() }

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) { init() }

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr) { init() }
	//region View
	private var viewChild: RelativeLayout ?= null
	private var sizeChild = 0
	private lateinit var eventClick: () -> Unit

	private var _xDelta = 0f
	private var _yDelta = 0f
	//endregion
}