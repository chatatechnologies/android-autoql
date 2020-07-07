package com.carlos.buruel.textviewspinner

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
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
		}

		addView(spSelect)
		addView(tvContent)
	}

	private fun getDataSuggestion(): ArrayAdapter<String>
	{
		val aData = arrayListOf("First word", "Second word")
		return ArrayAdapter(context, android.R.layout.simple_spinner_item, aData)
	}

	fun setText(text: String)
	{
		tvContent?.run {
			val span1 = SpannableString("totel opereting expinses bu accaunt")
//			span1.setSpan(ClickableSpan(this), 0, 5, 0)
//			span1.setSpan(ClickableSpan(this), 6, 15, 0)
//			span1.setSpan(ClickableSpan(this), 16, 24, 0)
//			span1.setSpan(ClickableSpan(this), 25, 27, 0)
//			span1.setSpan(ClickableSpan(this), 28, 35, 0)
			setText(span1)
			movementMethod = LinkMovementMethod.getInstance()

			setOnClickListener {
				spSelect?.performClick()
			}
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