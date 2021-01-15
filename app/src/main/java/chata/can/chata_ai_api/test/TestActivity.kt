package chata.can.chata_ai_api.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.addFragment
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.extension.whenAllNotNull
import chata.can.chata_ai.view.resize.SplitViewConst
import chata.can.chata_ai_api.R
import kotlin.math.max
import kotlin.math.min

class TestActivity
	: AppCompatActivity(), View.OnTouchListener
{
	//for move views
	private lateinit var rlMain: View
	private lateinit var vHandle: View
	private lateinit var llMenu: View
	private lateinit var rlLocal: View

	private lateinit var ivChat: ImageView
	private lateinit var ivTips: ImageView
	private lateinit var ivNotify: ImageView

	//region variable
	private var mDraggingStarted = 0L
	private var mDragStartX = 0f
	private var mDragStartY = 0f
	private var mPointerOffset = 0f

	private var limitPrimary = 48f
	private var limitSecondary = 432f
	//endregion

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)

		rlMain = findViewById(R.id.rlMain)
		vHandle = findViewById(R.id.vHandle)
		llMenu = findViewById(R.id.llMenu)
		rlLocal = findViewById(R.id.rlLocal)

		vHandle.setOnTouchListener(this)

		//region no important
		ivChat = findViewById(R.id.ivChat)
		ivTips = findViewById(R.id.ivTips)
		ivNotify = findViewById(R.id.ivNotify)

		ivChat.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
		ivChat.paddingAll(left = 6f, right = 6f)
		(ivChat.layoutParams as? RelativeLayout.LayoutParams)?.run {
			height = dpToPx(56f)
			width = -1
		}
		ivTips.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
		ivTips.paddingAll(left = 6f, right = 6f)
		(ivTips.layoutParams as? RelativeLayout.LayoutParams)?.run {
			height = dpToPx(56f)
			width = -1
		}
		ivNotify.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
		ivNotify.paddingAll(left = 6f, right = 6f)
		(ivNotify.layoutParams as? RelativeLayout.LayoutParams)?.run {
			height = dpToPx(56f)
			width = -1
		}
		//endregion

		addFragment(supportFragmentManager, FragmentA.newInstance())
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean
	{
		view?.let {
			if (it != vHandle) return false
		}
		when(motionEvent.action)
		{
			MotionEvent.ACTION_DOWN ->
			{
				mDraggingStarted = SystemClock.elapsedRealtime()
				mDragStartX = motionEvent.x
				mDragStartY = motionEvent.y
				mPointerOffset = motionEvent.rawX - getPrimaryContentSize()
			}
			MotionEvent.ACTION_UP ->
			{
				if (
					mDragStartX < (motionEvent.x + SplitViewConst.TAP_DRIFT_TOLERANCE) &&
					mDragStartX > (motionEvent.x - SplitViewConst.TAP_DRIFT_TOLERANCE) &&
					mDragStartY < (motionEvent.y + SplitViewConst.TAP_DRIFT_TOLERANCE) &&
					mDragStartY > (motionEvent.y - SplitViewConst.TAP_DRIFT_TOLERANCE) &&
					((SystemClock.elapsedRealtime() - mDraggingStarted) < SplitViewConst.SINGLE_TAP_MAX_TIME)
				)
				{
					if (isPrimaryContentMaximized() || isSecondaryContentMaximized())
						setPrimaryContentSize(getPrimaryContentSize())
					else
						maximizeSecondaryContent()
				}
			}
			MotionEvent.ACTION_MOVE ->
			{
				setPrimaryContentWidth((motionEvent.rawX - mPointerOffset).toInt())
			}
		}

		return true
	}

	private fun maximizeSecondaryContent()
	{
		arrayListOf(rlLocal, llMenu).whenAllNotNull {
			maximizeContentPane(it[0], it[1])
		}
	}

	private fun maximizeContentPane(toMaximize: View, toUnMaximize: View)
	{
		val params = toUnMaximize.layoutParams as RelativeLayout.LayoutParams
		val secondParams = toMaximize.layoutParams as RelativeLayout.LayoutParams

		params.width = 1

		toUnMaximize.layoutParams = params
		toMaximize.layoutParams = secondParams
	}

	private fun getPrimaryContentSize() = llMenu.measuredWidth

	private fun isPrimaryContentMaximized() =
		(rlLocal.measuredWidth < SplitViewConst.MAXIMIZED_VIEW_TOLERANCE_DIP)

	private fun isSecondaryContentMaximized() =
		(llMenu.measuredWidth < SplitViewConst.MAXIMIZED_VIEW_TOLERANCE_DIP)

	private fun setPrimaryContentSize(newSize: Int): Boolean
	{
		return setPrimaryContentWidth(newSize)
	}

	private fun setPrimaryContentWidth(newWidth: Int): Boolean
	{
		var newWidth1 = max(0, newWidth)
		newWidth1 = min(newWidth1, rlMain.measuredWidth - vHandle.measuredWidth)
		val params = llMenu.layoutParams as RelativeLayout.LayoutParams
		if (rlLocal.measuredWidth < 1 && newWidth1 > params.width) return false

		if (newWidth1 >= 0 && newWidth1 > dpToPx(limitPrimary) && newWidth1 < limitSecondary)
		{
			val leftMargin = newWidth1 - llMenu.measuredWidth
			params.leftMargin = if (leftMargin < 6) 0 else leftMargin
		}
		//TODO make method
		//unMinimizeSecondaryContent()
		llMenu.layoutParams = params
		return true
	}
}