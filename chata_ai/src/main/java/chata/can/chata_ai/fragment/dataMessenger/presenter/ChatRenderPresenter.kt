package chata.can.chata_ai.fragment.dataMessenger.presenter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.speech.RecognizerIntent
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class ChatRenderPresenter(private val context: Context, private var contract: ChatContract.View?)
{
	fun setData()
	{
		contract?.setData(backgroundBorder())
	}

	private fun backgroundBorder(): Pair<GradientDrawable, GradientDrawable>
	{
		val blue = context.getParsedColor(ThemeColor.currentColor.drawerAccentColor)
		val circleDrawable = GradientDrawable().apply {
			shape = GradientDrawable.OVAL
			setColor(blue)
		}

		val white = context.getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
		val gray = context.getParsedColor(ThemeColor.currentColor.drawerTextColorPrimary)

		val queryDrawable = DrawableBuilder.setGradientDrawable(white,64f,1, gray)
		return Pair(circleDrawable, queryDrawable)
	}

	fun initSpeechInput(): Intent
	{
		val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
		with(speechIntent)
		{
			putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
			putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
			putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
			putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1)
		}
		return speechIntent
	}
}