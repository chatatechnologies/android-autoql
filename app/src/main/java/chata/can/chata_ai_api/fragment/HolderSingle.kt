package chata.can.chata_ai_api.fragment

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai_api.R

class HolderSingle(itemView: View): Holder(itemView)
{
	private val ll1 = itemView.findViewById<View>(R.id.ll1) ?: null
	private val rlWebView = itemView.findViewById<RelativeLayout>(R.id.rlWebView) ?: null
	private val webView = itemView.findViewById<WebView>(R.id.webView) ?: null
	private val rlLoad = itemView.findViewById<View>(R.id.rlLoad) ?: null

	override fun onPaint()
	{

	}

	@SuppressLint("SetJavaScriptEnabled")
	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		webView?.let {
			with(it)
			{
				clearCache(true)
				clearHistory()
				requestLayout()

				val url = "https://gitlab.com/users/sign_in"
				settings.javaScriptEnabled = true
				webView.loadUrl(url)
				webViewClient = object: WebViewClient()
				{
					override fun onPageFinished(view: WebView?, url: String?)
					{
						rlLoad?.visibility = View.GONE
					}
				}

				setOnTouchListener { view, _ ->
					view.parent.requestDisallowInterceptTouchEvent(true)
					false
				}
			}
		}
	}
}