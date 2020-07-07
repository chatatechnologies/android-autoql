package com.carlos.buruel.textviewspinner

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

class ClickableSpan(
	private val tvSource: TextView,
	private val aSuggestion: ArrayList<String>,
	private val action: (ArrayList<String>) -> Unit
): ClickableSpan()
{
	override fun onClick(view: View)
	{
		action(aSuggestion)
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