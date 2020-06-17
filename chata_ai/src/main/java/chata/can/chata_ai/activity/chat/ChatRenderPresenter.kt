package chata.can.chata_ai.activity.chat

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.speech.RecognizerIntent
import androidx.core.content.ContextCompat
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class ChatRenderPresenter(
	private val context: Context, private var contract: ChatContract.View?)
{
	fun setData()
	{
		contract?.setData(backgroundBorder())
	}

	private fun backgroundBorder(): Pair<GradientDrawable,GradientDrawable>
	{
		val blue = ContextCompat.getColor(
			context,
			ThemeColor.currentColor.drawerAccentColor)
		val circleDrawable = GradientDrawable().apply {
			shape = GradientDrawable.OVAL
			setColor(blue)
		}

		val white = ContextCompat.getColor(context, ThemeColor.currentColor.drawerBackgroundColor)
		val gray = ContextCompat.getColor(context, ThemeColor.currentColor.drawerColorPrimary)

		val queryDrawable = DrawableBuilder.setGradientDrawable(white,64f,1, gray)
		return Pair(circleDrawable, queryDrawable)
	}

	fun initSpeechInput(): Intent
	{
		val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
		with(speechIntent)
		{
			putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en")
			putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
			putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
			putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1)
		}
		return speechIntent
	}

	/*fun onDestroy()
	{
		contract = null
	}*/
}