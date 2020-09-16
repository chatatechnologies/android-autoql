package chata.can.chata_ai.activity.dataMessenger.voice

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import chata.can.chata_ai.activity.dataMessenger.ChatContract

class VoiceRecognition(val view: ChatContract.VoiceView): RecognitionListener
{
	override fun onBeginningOfSpeech() {}

	override fun onBufferReceived(p0: ByteArray?) {}

	override fun onEndOfSpeech()
	{
		view.setStopRecorder()
	}

	override fun onError(errorCode: Int)
	{
		val errorMessage = when(errorCode)
		{
			SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
			SpeechRecognizer.ERROR_CLIENT -> "Client side error"
			SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
			SpeechRecognizer.ERROR_NETWORK -> "Network related error"
			SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network operation timeout"
			SpeechRecognizer.ERROR_NO_MATCH -> "No recognition result matched"
			SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognition service busy"
			SpeechRecognizer.ERROR_SERVER -> "Server sends error status"
			SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
			else -> "ASR error"
		}
		Log.e("SpeechError","Recognizer message : $errorMessage")
		view.setStopRecorder()
	}

	override fun onEvent(p0: Int, p1: Bundle?) {}

	override fun onPartialResults(p0: Bundle?) {}

	override fun onReadyForSpeech(p0: Bundle?)
	{
		view.setRecorder()
	}

	override fun onResults(results: Bundle?)
	{
		results?.let {
			val matches = it.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) ?: return
			if (matches.isNotEmpty())
			{
				val first = matches.first()
				view.setSpeech(first)
			}
		}
	}

	override fun onRmsChanged(p0: Float) {}
}