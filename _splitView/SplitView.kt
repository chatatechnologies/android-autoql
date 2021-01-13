package mx.bangapp.viewresize

import android.annotation.SuppressLint
import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import mx.bangapp.viewresize.SplitViewConst.MAXIMIZED_VIEW_TOLERANCE_DIP
import mx.bangapp.viewresize.SplitViewConst.SINGLE_TAP_MAX_TIME
import mx.bangapp.viewresize.SplitViewConst.TAP_DRIFT_TOLERANCE
import kotlin.math.max
import kotlin.math.min

class SplitView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs), View.OnTouchListener
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

	init {
		val viewAttrs = context.obtainStyledAttributes(attrs, R.styleable.SplitView)
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

	@SuppressLint("ClickableViewAccessibility")
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

//	fun getHandle(): View?
//	{
//		return mHandle
//	}

	private fun getPrimaryContentSize(): Int
	{
		return mPrimaryContent?.run {
			if (orientation == VERTICAL) measuredHeight
			else measuredWidth
		} ?: run {0}
	}

	private fun setPrimaryContentSize(newSize: Int): Boolean
	{
		return if (orientation == VERTICAL)
			setPrimaryContentHeight(newSize)
		else setPrimaryContentWidth(newSize)
	}

	private fun setPrimaryContentHeight(newHeight: Int): Boolean
	{
		var newHeight1 = max(0, newHeight)
		newHeight1 = min(newHeight1, measuredHeight - (mHandle?.measuredHeight?: 0))
		val params = mPrimaryContent?.layoutParams as? LayoutParams
		if ((mSecondaryContent?.measuredHeight ?: 0) < 1 && newHeight1 > (params?.height ?:0))
		{
			return false
		}
		if (newHeight1 >= 0)
		{
			params?.height = newHeight1
			params?.weight = 0f
		}
		unMinimizeSecondaryContent()
		mPrimaryContent?.layoutParams = params
		return true
	}

	private fun setPrimaryContentWidth(newWidth: Int): Boolean
	{
		var newWidth1 = max(0, newWidth)
		newWidth1 = min(newWidth1, measuredWidth - (mHandle?.measuredWidth ?: 0))
		val params = mPrimaryContent?.layoutParams as? LayoutParams
		if ((mSecondaryContent?.measuredWidth ?: 0 ) < 1 && newWidth1 > (params?.width ?: 0))
		{
			return false
		}
		if (newWidth1 >= 0)
		{
			params?.width = newWidth1
			params?.weight = 0f
		}
		unMinimizeSecondaryContent()
		mPrimaryContent?.layoutParams = params
		return true
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
					mDragStartY < (motionEvent.y + TAP_DRIFT_TOLERANCE) &&
					mDragStartY > (motionEvent.y - TAP_DRIFT_TOLERANCE) &&
					((SystemClock.elapsedRealtime() - mDraggingStarted) < SINGLE_TAP_MAX_TIME)
				)
				{
					if (isPrimaryContentMaximized() || isSecondaryContentMaximized())
						setPrimaryContentSize(mLastPrimaryContentSize)
					else
						maximizeSecondaryContent()
				}
			}
			MotionEvent.ACTION_MOVE ->
			{
				if (orientation == VERTICAL)
					setPrimaryContentHeight((motionEvent.rawY - mPointerOffset).toInt())
				else setPrimaryContentWidth((motionEvent.rawX - mPointerOffset).toInt())
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

//	fun maximizePrimaryContent()
//	{
//		arrayListOf(mPrimaryContent, mSecondaryContent).whenAllNotNull {
//			maximizeContentPane(it[0], it[1])
//		}
//	}

	private fun maximizeSecondaryContent()
	{
		arrayListOf(mSecondaryContent, mPrimaryContent).whenAllNotNull {
			maximizeContentPane(it[0], it[1])
		}
	}

	private fun maximizeContentPane(toMaximize: View, toUnMaximize: View)
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

	private fun unMinimizeSecondaryContent()
	{
		val secondaryParams = mSecondaryContent?.layoutParams as? LayoutParams
		secondaryParams?.weight = 1f
		mSecondaryContent?.layoutParams = secondaryParams
	}
}