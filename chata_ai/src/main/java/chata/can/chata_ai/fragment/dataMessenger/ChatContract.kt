package chata.can.chata_ai.fragment.dataMessenger

import android.graphics.drawable.GradientDrawable
import chata.can.chata_ai.pojo.chat.SimpleQuery
import chata.can.chata_ai.pojo.suggestion.RequestSuggestion

interface ChatContract
{
	interface VoiceView
	{
		fun setRecorder()
		fun setStopRecorder()
		fun setSpeech(message: String)
	}

	interface RenderView: VoiceView
	{
		fun addSimpleText(message: String)
		fun addChatMessage(typeView: Int, message: String, query: String)
		fun addNewChat(typeView: Int, queryBase: SimpleQuery)
		fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>)
		fun runTyping(text: String)
		fun runTyping(requestSuggestion: RequestSuggestion)
		fun isLoading(isVisible: Boolean)
		fun showAlert(message: String, intRes: Int)
		fun scrollToPosition()

		fun showToast()
	}

	interface ServiceView
	{
		fun setDataAutocomplete(aMatches: ArrayList<String>)
	}

	interface View: RenderView, ServiceView
}