package chata.can.chata_ai_api.fragment.dashboard.holder

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R

class HolderWebView(itemView: View): BaseHolder(itemView)
{
//	private val rlWebView = itemView.findViewById<RelativeLayout>(R.id.rlWebView)
	private val webView = itemView.findViewById<WebView>(R.id.webView)
	private val rlLoad = itemView.findViewById<View>(R.id.rlLoad)

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{

		super.onBind(item, listener)
		if (item is Dashboard)
		{
			item.queryBase?.run {
				rlLoad?.visibility = View.VISIBLE
				if (!isLoadingHTML)
				{
					setDataWebView(this)
				}
			}
		}
		if (item is QueryBase)
		{
			setDataWebView(item)
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	private fun setDataWebView(queryBase: QueryBase)
	{
		webView?.run {
			clearCache(true)
			clearHistory()
			requestLayout()
			settings.javaScriptEnabled = true

			loadDataWithBaseURL(
				null,
				queryBase.contentHTML,
				"text/html",
				"UTF-8",
				null)
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