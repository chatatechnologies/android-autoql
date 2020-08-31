package chata.can.chata_ai_api.fragment.dashboard.holder

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.RelativeLayout
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.drillDown.JavascriptInterface

class WebViewHolder(itemView: View): BaseHolder(itemView)
{
	private val iView = itemView.findViewById<View>(R.id.iView) ?: null
	private val rlWebView = itemView.findViewById<RelativeLayout>(R.id.rlWebView)
	private val webView = itemView.findViewById<WebView>(R.id.webView)
	private val rlLoad = itemView.findViewById<View>(R.id.rlLoad)

//	private val rvSplitView = itemView.findViewById<RelativeLayout>(R.id.rvSplitView)
//	private val webView2 = itemView.findViewById<WebView>(R.id.webView2)
//	private val rlLoad2 = itemView.findViewById<View>(R.id.rlLoad2)

	override fun onPaint()
	{
		super.onPaint()
		iView?.let {
			(it.layoutParams as? LinearLayout.LayoutParams)?.let { layout ->
				layout.height = 1
				iView.layoutParams = layout
			}
		}
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
//			if (item.splitView)
//			{
//				rvSplitView.visibility = View.VISIBLE
//				iView?.setBackgroundColor(drawerColorPrimary)
//			}
//			else
//			{
//				rvSplitView.visibility = View.GONE
//				iView?.setBackgroundColor(drawerBackgroundColor)
//			}
			item.queryBase?.run {
				if (!isLoadingHTML)
				{
					setDataWebView(rlLoad, webView, this, rlWebView)
				}
			}
			item.queryBase2?.run {
				this.toString()
//				if (!isLoadingHTML)
//				{
//					rvSplitView.visibility = View.VISIBLE
//					setDataWebView(rlLoad2, webView2,this, rvSplitView)
//				}
//				else
//					rvSplitView.visibility = View.GONE
			}
		}
		if (item is QueryBase)
		{
//			if (item.isSplitView)
//			{
//				if (!item.isLoadingHTML)
//				{
//					rvSplitView.visibility = View.VISIBLE
//					setDataWebView(rlLoad2, webView2, item, rvSplitView)
//				}
//				else
//					rvSplitView.visibility = View.GONE
//			}
//			else
//			{
//				//Main set data to webView
//				setDataWebView(rlLoad, webView, item, rlWebView)
//			}
			setDataWebView(rlLoad, webView, item, rlWebView)
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