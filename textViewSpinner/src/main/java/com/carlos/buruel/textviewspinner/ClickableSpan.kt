package com.carlos.buruel.textviewspinner

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast

class ClickableSpan(private val tvSource: TextView): ClickableSpan()
{
	override fun onClick(view: View)
	{
		Toast.makeText(tvSource.context, "hola", Toast.LENGTH_SHORT).show()
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