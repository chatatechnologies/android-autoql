package com.carlos.buruel.textviewspinner

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.*

class SpinnerTextView: RelativeLayout
{
	private fun init()
	{
		spSelect = Spinner(context).apply {
			adapter = getDataSuggestion()
			background = null
			layoutParams = LayoutParams(-1, -2)
		}

		tvContent = TextView(context).apply {
			setBackgroundColor(Color.WHITE)
			gravity = Gravity.CENTER
			highlightColor = Color.TRANSPARENT
			layoutParams = LayoutParams(-1, -2)
			setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

			viewTreeObserver?.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener
			{
				override fun onGlobalLayout()
				{
					tvContent?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
					val height = tvContent?.measuredHeight ?: 0
					spSelect?.dropDownVerticalOffset = height
				}
			})
		}

		addView(spSelect)
		addView(tvContent)
	}

	private fun getDataSuggestion(): ArrayAdapter<String>
	{
		val aData = arrayListOf("First word", "Second word")
		return ArrayAdapter(context, android.R.layout.simple_spinner_item, aData)
	}

	private fun callSpinnerClick()
	{
		spSelect?.performClick()
	}

	fun setText(text: String = "totel opereting expinses bu accaunt")
	{
		val aData = arrayListOf(Pair(0, 5), Pair(6, 15), Pair(16, 24), Pair(25, 27), Pair(28, 35))
		tvContent?.run {
			val span = SpannableString(text)
			for (pair in aData)
			{
				span.setSpan(ClickableSpan(this) {
					callSpinnerClick()
				}, pair.first, pair.second, 0)
			}
			setText(span)
			movementMethod = LinkMovementMethod.getInstance()
		}
	}

	fun setWindowManager(_windowManager: WindowManager)
	{
		this.windowManager = _windowManager

		val displayMetrics = DisplayMetrics()
		val defaultDisplay = this.windowManager?.defaultDisplay
		defaultDisplay?.getMetrics(displayMetrics)
		val width = displayMetrics.widthPixels
		spSelect?.dropDownWidth = width
	}

	constructor(context: Context): super(context)
	{
		init()
	}

	constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

	constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)
	{
		init()
	}

	private var tvContent: TextView?= null
	private var spSelect: Spinner?= null

	private var windowManager: WindowManager?= null
}