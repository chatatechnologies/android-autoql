package chata.can.chata_ai.fragment.dataMessenger.holder

import android.content.Context
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor

object Interpretation {
	fun setUnderLine(text: String, tvSource: TextView)
	{
		val ssBuilder = SpannableString(text)
		//region define range for underline
		val limiter = '\''
		val pScope = ArrayList<Pair<Int, Int>>()
		var index = 0
		while(index < text.length)
		{
			if (text[index] == limiter)
			{
				pScope.add(Pair(index, index + 3))
				index += 3
			}
			else
				index++
		}
		//endregion
		//region apply underline
		for (pair in pScope)
		{
			ssBuilder.setSpan(
				blueUnderline(tvSource.context),
				pair.first,
				pair.second,
				0)
		}
		//endregion
		tvSource.text = ssBuilder
	}

	private fun blueUnderline(context: Context) = object: ClickableSpan() {
		override fun onClick(view: View) {}

		override fun updateDrawState(textPaint: TextPaint)
		{
			textPaint.run {
				color = context.getParsedColor(R.color.blue_chata_circle)
				isUnderlineText = true
			}
		}
	}
}