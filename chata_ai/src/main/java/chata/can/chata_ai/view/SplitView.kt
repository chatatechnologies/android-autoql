package mx.bangapp.viewresize

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

class SplitView: LinearLayout, View.OnTouchListener
{
	private var mHandleId = 0
	private var mHandle: View ?= null

	private var mPrimaryContentId = 0
	private var mPrimaryContent: View ?= null

	private var mSecondaryContentId = 0
	private var mSecondaryContent: View ?= null

	private var mLastPrimaryContentSize = 0

	private var mDragging = false
	private var mDraggingStarted = 0L
	private var mDragStartX = 0f
	private var mDragStartY = 0f

	private var mPointerOffset = 0f

	private val MAXIMIZED_VIEW_TOLERANCE_DIP = 30
	private val TAP_DRIFT_TOLERANCE = 3
	private val SINGLE_TAP_MAX_TIME = 175

	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
	{
		val viewAttrs = context.obtainStyledAttributes(R.styleable.SplitView)
		var e: RuntimeException ?= null

		mHandleId = viewAttrs.getResourceId(R.styleable.SplitView_handle, 0)
		if (mHandleId == 0) {
			e = IllegalArgumentException(viewAttrs.positionDescription +
				": The required attribute handle must refer to a valid child view")
		}

		mPrimaryContentId = viewAttrs.getResourceId(R.styleable.SplitView_primaryContent, 0)
		if (mPrimaryContentId == 0) {
			e = IllegalArgumentException(viewAttrs.positionDescription +
				": The required attribute primaryContent must refer to a valid child view")
		}

		mSecondaryContentId = viewAttrs.getResourceId(R.styleable.SplitView_secondaryContent, 0)
		if (mSecondaryContentId == 0) {
			e = IllegalArgumentException(viewAttrs.positionDescription +
				": The required attribute secondaryContent must refer to a valid child view")
		}

		viewAttrs.recycle()
		e?.let {
			throw e
		}
	}

	override fun onFinishInflate()
	{
		super.onFinishInflate()
		mHandle = findViewById(mHandleId)
		if (mHandle == null) {
			val name = resources.getResourceEntryName(mHandleId)
			throw RuntimeException("Your Panel must have a child View whose id attribute is 'R.id.'$name")
		}
		mPrimaryContent = findViewById(mPrimaryContentId)
		if (mPrimaryContent == null) {
			val name = resources.getResourceEntryName(mPrimaryContentId)
			throw RuntimeException("Your Panel must have a child View whose id attribute is 'R.id.'$name")
		}

		mLastPrimaryContentSize = getPrimaryContentSize()

		mSecondaryContent = findViewById(mSecondaryContentId)
		if (mSecondaryContent == null) {
			val name = resources.getResourceEntryName(mSecondaryContentId)
			throw RuntimeException("Your Panel must have a child View whose id attribute is 'R.id.$name'")
		}
		mHandle?.setOnTouchListener(this)
	}

	fun getPrimaryContentSize(): Int
	{
		return mPrimaryContent?.run {
			if (orientation == VERTICAL) measuredHeight
			else measuredHeight
		} ?: run {0}
	}

	override fun onTouch(view: View, motionEvent: MotionEvent): Boolean
	{
		if (view != mHandle) {
			return false
		}
		when(motionEvent.action)
		{
			MotionEvent.ACTION_DOWN ->
			{
				mDragging = true
				mDraggingStarted = SystemClock.elapsedRealtime()
				mDragStartX = motionEvent.x
				mDragStartY = motionEvent.y
				mPointerOffset = if (orientation == VERTICAL) {
					motionEvent.rawY - getPrimaryContentSize()
				} else {
					motionEvent.rawX - getPrimaryContentSize()
				}
			}
			MotionEvent.ACTION_UP ->
			{
				mDragging = false
				if (
					mDragStartX < (motionEvent.x + TAP_DRIFT_TOLERANCE) &&
					mDragStartX > (motionEvent.x - TAP_DRIFT_TOLERANCE) &&
					mDragStartX < (motionEvent.y + TAP_DRIFT_TOLERANCE) &&
					mDragStartX > (motionEvent.y - TAP_DRIFT_TOLERANCE) &&
					((SystemClock.elapsedRealtime() - mDraggingStarted) < SINGLE_TAP_MAX_TIME)
				)
				{
					if (isPrimaryContentMaximized() || isSecondaryContentMaximized())
					{
						setPrimaryContentSize(mLastPrimaryContentSize)
					}
					else
					{
						maximizeSecondaryContent()
					}
				}
			}
		}
		return true
	}

	private fun isPrimaryContentMaximized(): Boolean
	{
		return (
			(orientation == VERTICAL &&
				(mSecondaryContent?.measuredHeight ?: 0 < MAXIMIZED_VIEW_TOLERANCE_DIP)) ||
			(orientation == HORIZONTAL &&
				(mSecondaryContent?.measuredWidth ?: 0 < MAXIMIZED_VIEW_TOLERANCE_DIP))
		)
	}

	private fun isSecondaryContentMaximized(): Boolean
	{
		return ((orientation == VERTICAL &&
			(mPrimaryContent?.measuredHeight ?: 0 < MAXIMIZED_VIEW_TOLERANCE_DIP)) ||
			(orientation == HORIZONTAL && (mPrimaryContent?.measuredWidth ?: 0 < MAXIMIZED_VIEW_TOLERANCE_DIP))
		)
	}

	fun maximizePrimaryContent() = maximizeContentPane(mPrimaryContent, mSecondaryContent)

	fun maximizeSecondaryContent() = maximizeContentPane(mPrimaryContent, mSecondaryContent)

	fun maximizeContentPane(toMaximize: View, toUnMaximize: View)
	{
		mLastPrimaryContentSize = getPrimaryContentSize()
		val params = toUnMaximize.layoutParams as LayoutParams
		val secondParams = toMaximize.layoutParams as LayoutParams

		params.weight = 0f
		secondParams.weight = 1f
		if (orientation == VERTICAL) {
			params.height = 1
		} else {
			params.width = 1
		}
		toUnMaximize.layoutParams = params
		toMaximize.layoutParams = secondParams
	}
}