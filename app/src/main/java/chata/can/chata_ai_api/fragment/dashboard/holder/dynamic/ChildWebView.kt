package chata.can.chata_ai_api.fragment.dashboard.holder.dynamic

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai_api.fragment.dashboard.drillDown.JavascriptInterface

object ChildWebView {
	fun onBind(view: View, queryBase: QueryBase ?= null, tData: Triple<Int, Int, Int>)
	{
		val rlWebView = view.findViewById<RelativeLayout>(tData.first)?: null
		val webView = view.findViewById<WebView>(tData.second)?: null
		val rlLoad = view.findViewById<View>(tData.third)?: null

		queryBase?.let {
			rlWebView?.let { rlWebView ->
				webView?.let { webView ->
					rlLoad?.let { rlLoad ->
						if (!queryBase.isLoadingHTML)
						{
							setDataWebView(rlLoad, webView, queryBase, rlWebView)
						}
					}
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
			addJavascriptInterface(JavascriptInterface(context, queryBase), "Android")
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
//		val lastNum = when(this@WebViewHolder.lastId)
//		{
//			"#idTableBasic" -> rowsTable
//			"#idTableDataPivot" -> rowsPivot
//			"#container" -> factorHeight
//			else -> 0
//		}
		rlWebView.let {
			//val tmpRows = if (numRows == 0) 180 else numRows
			val tmpRows = 180
			var customHeight = it.dpToPx(30f * tmpRows) + 60
			if (customHeight > 900)
			{
				customHeight = 900
			}
			it.layoutParams = RelativeLayout.LayoutParams(-1, customHeight)
		}
	}
}