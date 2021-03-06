package chata.can.chata_ai.view.textViewSpinner

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.view.textViewSpinner.model.Suggestion

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
			color = tvSource.context.getParsedColor(R.color.blue_chata_circle)
			isUnderlineText = true
		}
	}
}