package com.carlos.buruel.textviewspinner

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class SpinnerTextView: RelativeLayout
{
	private fun init()
	{
		tvContent = TextView(context).apply {
			layoutParams = LayoutParams(-1, -2)
			gravity = Gravity.CENTER
		}

		addView(tvContent)
	}

	fun setText(text: String)
	{
		val span1 = SpannableString("uno dos tres")
		span1.setSpan(getClickable(), 0, 3, 0 )
		span1.setSpan(getClickable(), 4, 7, 0 )
		tvContent.text = span1
		tvContent.movementMethod = LinkMovementMethod.getInstance()

		var ssb = SimpleSpanBuilder("Uno ")
		ssb += SimpleSpanBuilder.Span(" dos ")
		ssb += SimpleSpanBuilder.Span(" tres ")
		//tvContent.text = ssb.build()

		//val spannable = SpannableString(text)
		//spannable.setSpans(getClickable(), arrayListOf(Pair(0, 5), Pair(6, 15)))
		//tvContent.text = spannable
		//tvContent.movementMethod = LinkMovementMethod.getInstance()
	}

	fun generateSpannable()
	{
		val spannable = SpannableString("Hola ")
		spannable.setSpan(getClickable(), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
	}

	private fun getClickable(): ClickableSpan
	{
		return object: ClickableSpan()
		{
			override fun onClick(widget: View)
			{
				Toast.makeText(context, "hola", Toast.LENGTH_SHORT).show()
			}

			override fun updateDrawState(textPaint: TextPaint)
			{
				textPaint.color = Color.parseColor("#0000EE")
				textPaint.isUnderlineText = true
			}
		}
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

	private lateinit var tvContent: TextView
	private lateinit var spSelect: Spinner
}