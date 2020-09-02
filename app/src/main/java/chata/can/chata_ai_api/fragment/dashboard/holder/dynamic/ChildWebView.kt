package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.RelativeLayout
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.drillDown.JavascriptInterface

class ChildWebView(
	view: View,
	private val queryBase: QueryBase ?= null,
	private val tData: Triple<Int, Int, Int>
)
{
	private val rlWebView = view.findViewById<RelativeLayout>(tData.first)?: null
	private val webView = view.findViewById<WebView>(tData.second)?: null
	private val rlLoad = view.findViewById<View>(tData.third)?: null

	init {
		onBind()
	}

	fun onBind()
	{
		queryBase?.let {
			rlWebView?.let { rlWebView ->
				if (!queryBase.isLoadingHTML)
				{
					setDataWebView(rlLoad, webView, queryBase, rlWebView)
				}
			}
		}
	}

	@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
	private fun setDataWebView(rlLoad: View?, webView: WebView?, queryBase: QueryBase, rlWebView: RelativeLayout)
	{
		rlLoad?.visibility = View.VISIBLE
		changeHeightParent(rlWebView, queryBase.rowsTable)
		webView?.run {
			clearCache(true)
			clearHistory()
			requestLayout()
			settings.javaScriptEnabled = true

			if (queryBase.hasDrillDown)
			{
				addJavascriptInterface(JavascriptInterface(context, queryBase), "Android")
			}
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

	private fun changeHeightParent(rlWebView: RelativeLayout, numRows: Int)
	{
		rlWebView.let {
			val tmpRows = if (numRows == 0) 180 else numRows
			var customHeight = it.dpToPx(30f * tmpRows) + 60
			if (customHeight > 900)
			{
				customHeight = 900
			}
			it.layoutParams = LinearLayout.LayoutParams(-1, customHeight)
		}
	}
}