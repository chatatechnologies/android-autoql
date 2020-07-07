package com.carlos.buruel.textviewspinner

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

class ClickableSpan(
	private val tvSource: TextView,
	private val action: () -> Unit
): ClickableSpan()
{
	override fun onClick(view: View)
	{
		action()
		tvSource.highlightColor = Color.TRANSPARENT
	}

	override fun updateDrawState(textPaint: TextPaint)
	{
		textPaint.run {
			color = Color.parseColor("#0000EE")
			isUnderlineText = true
		}
	}
}