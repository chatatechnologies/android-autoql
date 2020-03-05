package chata.can.chata_ai.view.bubbles

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.WindowManager
import chata.can.chata_ai.R
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import kotlin.math.min

/**
 * @author https://github.com/txusballesteros
 */
class BubbleLayout: BubbleBaseLayout
{
	private var initialTouchX = 0f
	private var initialTouchY = 0f
	private var initialX = 0
	private var initialY = 0
	private var onBubbleClickListener: OnBubbleClickListener ?= null
	private val touchTimeThreshold = 150
	private var lastTouchDown = 0L
	private var animator: MoveAnimator ?= null

	private var centerX = 0
	private var placementX = 0
	private var centerY = 0
	private var placementY = 0
	private var height1 = 0
	private var width1 = 0
	private var windowManager1: WindowManager ?= null
	private var shouldStickToWall = true

	fun setOnBubbleClickListener(listener: () -> Unit)
	{
		onBubbleClickListener = object: OnBubbleClickListener
		{
			override fun onBubbleClick(bubble: BubbleLayout)
			{
				listener()
			}
		}
	}

	constructor(context: Context): super(context)
	{
		initView()
	}

	constructor(context: Context, attrs: AttributeSet): super(context, attrs)
	{
		initView()
	}

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr)
	{
		initView()
	}

	private fun initView()
	{
		animator = MoveAnimator()
		windowManager1 = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
		initializeView()
	}

	fun setShouldStickToWall(shouldStick: Boolean)
	{
		shouldStickToWall = shouldStick
	}

	private fun initializeView()
	{
		isClickable = true
	}

	override fun onAttachedToWindow()
	{
		super.onAttachedToWindow()
		playAnimation()
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(event: MotionEvent?): Boolean
	{
		when(event?.action)
		{
			MotionEvent.ACTION_DOWN ->
			{
				initialX = getViewParams()?.x ?: 0
				initialY = getViewParams()?.y ?: 0
				initialTouchX = event.rawX
				initialTouchY = event.rawY
				playAnimationClickDown()
				lastTouchDown = System.currentTimeMillis()
				updateSize()
				animator?.stop()
			}
			MotionEvent.ACTION_MOVE ->
			{
				val x = initialX + (event.rawX - initialTouchX).toInt()
				val y = initialY + (event.rawY - initialTouchY).toInt()
				getViewParams()?.x = x
				getViewParams()?.y = y
				getWindowManager()?.updateViewLayout(this, getViewParams())
			}
			MotionEvent.ACTION_UP ->
			{
				goToWall()
				if (System.currentTimeMillis() - lastTouchDown < touchTimeThreshold)
				{
					onBubbleClickListener?.onBubbleClick(this)
				}
			}
		}
		return super.onTouchEvent(event)
	}

	private fun playAnimation()
	{
		if (!isInEditMode)
		{
			(AnimatorInflater.loadAnimator(context, R.animator.bubble_shown_animator) as? AnimatorSet)?.let {
				it.setTarget(this)
				it.start()
			}
		}
	}

	private fun playAnimationClickDown()
	{
		if (!isInEditMode)
		{
			(AnimatorInflater.loadAnimator(context, R.animator.bubble_down_click_animator) as? AnimatorSet)?.let {
				it.setTarget(this)
				it.start()
			}
		}
	}

	fun definePositionInScreen(placement: Int)
	{
		updateSize()
		when(placement)
		{
			BubbleHandle.TOP_PLACEMENT ->
			{
				placementX = centerX
				placementY = 0
			}
			BubbleHandle.BOTTOM_PLACEMENT ->
			{
				placementX = centerX
				placementY = height1
			}
			BubbleHandle.LEFT_PLACEMENT ->
			{
				placementX = 0
				placementY = centerY
			}
			BubbleHandle.RIGHT_PLACEMENT ->
			{
				placementX = width1
				placementY = centerY
			}
		}

		getViewParams()?.x = placementX
		getViewParams()?.y = placementY
		getWindowManager()?.updateViewLayout(this, getViewParams())
	}

	private fun updateSize()
	{
		val metrics = DisplayMetrics()
		windowManager1?.defaultDisplay?.getMetrics(metrics)
		val display = getWindowManager()?.defaultDisplay
		val size = Point()
		display?.getSize(size)

		width1 = size.x - this.width
		height1 = size.y - this.height
		centerX = width1 / 2
		centerY = height1 / 2
	}

	interface OnBubbleClickListener {
		fun onBubbleClick(bubble: BubbleLayout)
	}

	private fun goToWall()
	{
		if (shouldStickToWall)
		{
			val middle = width1 / 2
			val nearestXWall = if ( (getViewParams()?.x ?: 0) >= middle ) width1 else 0
			animator?.start(nearestXWall.toFloat(), getViewParams()?.y?.toFloat() ?: 0f)
		}
	}

	private fun move(deltaX: Float, deltaY: Float)
	{
		getViewParams()?.let {
			it.x += deltaX.toInt()
			it.y += deltaY.toInt()
			windowManager1?.updateViewLayout(this, getViewParams())
		}
	}

	inner class MoveAnimator: Runnable
	{
		private val handler = Handler(Looper.getMainLooper())
		private var destinationX = 0f
		private var destinationY = 0f
		private var startingTime = 0L

		fun start(x: Float, y: Float)
		{
			destinationX = x
			destinationY = y
			startingTime = System.currentTimeMillis()
			handler.post(this)
		}

		override fun run()
		{
			if (rootView != null && rootView.parent != null)
			{
				val progress = min(1f, (System.currentTimeMillis() - startingTime) / 400f)
				val deltaX = (destinationX - (getViewParams()?.x ?: 0)) * progress
				val deltaY = (destinationY - (getViewParams()?.y ?: 0)) * progress
				move(deltaX, deltaY)
				if (progress < 1)
				{
					handler.post(this)
				}
			}
		}

		fun stop()
		{
			handler.removeCallbacks(this)
		}
	}
}