package chata.can.chata_ai.fragment.dataMessenger.holder

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.widget.TextView

object Interpretation {
	fun setUnderLine(text: String, tvSource: TextView)
	{
		val ssBuilder = SpannableStringBuilder(text)
		//region define range for underline
		val limiter = '\''
		val pScope = ArrayList<Pair<Int, Int>>()
		var index = 0
		while(index < text.length)
		{
			if (text[index] == limiter)
			{
				pScope.add(Pair(index, index + 2))
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
				UnderlineSpan(), pair.first, pair.second, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		}
		//endregion
		tvSource.text = ssBuilder
	}
}