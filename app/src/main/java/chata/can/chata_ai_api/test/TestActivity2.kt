package chata.can.chata_ai_api.test

import android.annotation.SuppressLint
import android.webkit.WebView
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.pojo.webView.TestingHTML
import chata.can.chata_ai_api.R
import chata.can.request_native.ExampleRequest

class TestActivity2: BaseActivity(R.layout.test_activity_2)
{
	private lateinit var webView: WebView

	@SuppressLint("SetJavaScriptEnabled")
	override fun onCreateView()
	{
		//webView = findViewById(R.id.webView)

//		webView.run {
//			settings.javaScriptEnabled = true
//			loadDataWithBaseURL(
//				null,
//				TestingHTML.getHtmlTest(),
//				"text/html",
//				"UTF-8",
//				null)
//		}

		ExampleRequest.callRequestSimple(
			this,
			typeJSON,
			//headers = header,
			//parametersAny = mParams,
			//infoHolder = infoHolder
		)
	}
}