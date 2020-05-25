package chata.can.chata_ai.fragment.dataMessenger

import android.graphics.drawable.GradientDrawable
import chata.can.chata_ai.pojo.chat.SimpleQuery

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
		fun addChatMessage(typeView: Int, message: String)
		fun addNewChat(typeView: Int, queryBase: SimpleQuery)
		//fun scrollToPosition()
		fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>)
		fun isLoading(isVisible: Boolean)
	}

	interface ServiceView
	{
		fun setDataAutocomplete(aMatches: ArrayList<String>)
	}

	interface View: RenderView, ServiceView
}