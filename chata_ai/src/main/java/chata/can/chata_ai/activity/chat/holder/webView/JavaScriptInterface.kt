package chata.can.chata_ai.activity.chat.holder.webView

import android.util.Log
import android.webkit.JavascriptInterface

class JavaScriptInterface
{
	@JavascriptInterface
	fun boundMethod(content:String)
	{
		Log.e("TYPE", content)
	}
}