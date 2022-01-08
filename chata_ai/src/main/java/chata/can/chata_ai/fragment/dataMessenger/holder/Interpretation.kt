package chata.can.chata_ai.fragment.dataMessenger.holder

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor

object Interpretation {
	fun setUnderLine(text: String, tvSource: TextView)
	{
		//region define range for underline
		val quote = '\''
		val stringBuilder = StringBuilder()
		val pScope = ArrayList<Pair<Int, Int>>()
		var index = 0
		while (index < text.length)
		{
			val letter = text[index]
			if (text[index] == quote)
			{
				pScope.add(Pair(stringBuilder.length, stringBuilder.length + 1))
				stringBuilder.append(text[index + 1])
				index += 3
			}
			else {
				index++
				stringBuilder.append(letter)
			}
		}
		//endregion
		val ssBuilder = SpannableString(stringBuilder)
		//region bold style
		ssBuilder.setSpan(
			StyleSpan(Typeface.BOLD),
			text.indexOf(headMessage),
			text.indexOf(headMessage) + headMessage.length - 1,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
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

	private const val headMessage = "Interpreted as:"

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