package chata.can.chata_ai_api.fragment.dashboard.drillDown

import android.webkit.JavascriptInterface

class JavascriptInterface()
{
	@JavascriptInterface
	fun boundMethod(content: String)
	{
		println("Dashboard log: $content")
	}
}