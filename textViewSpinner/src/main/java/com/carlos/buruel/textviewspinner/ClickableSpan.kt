package com.carlos.buruel.textviewspinner

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.carlos.buruel.textviewspinner.model.Suggestion

class ClickableSpan(
	private val tvSource: TextView,
	private val suggestion: Suggestion,
	private val action: (Suggestion) -> Unit
): ClickableSpan()
{
	override fun onClick(view: View)
	{
		action(suggestion)
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