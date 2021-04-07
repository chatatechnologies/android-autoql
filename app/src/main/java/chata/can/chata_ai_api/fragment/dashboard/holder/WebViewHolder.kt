package chata.can.chata_ai_api.fragment.dashboard.holder

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.RelativeLayout
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.fragment.dashboard.drillDown.JavascriptInterface

class WebViewHolder(itemView: View): BaseHolder(itemView)
{
	private val iView = itemView.findViewById<View>(R.id.iView) ?: null
	private val rlWebView = itemView.findViewById<RelativeLayout>(R.id.rlWebView)
	private val webView = itemView.findViewById<WebView>(R.id.webView)
	private val rlLoad = itemView.findViewById<View>(R.id.rlLoad)

	override fun onPaint()
	{
		super.onPaint()
		iView?.let {
			(it.layoutParams as? LinearLayout.LayoutParams)?.let { layout ->
				layout.height = 1
				iView.layoutParams = layout
			}
		}
		rlLoad.setBackgroundColor(ThemeColor.currentColor.pDrawerBackgroundColor)
		webView?.visibility = View.GONE
		rlLoad?.visibility = View.VISIBLE
	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		super.onBind(item, listener)
		if (item is Dashboard)
		{
			item.queryBase?.run {
				if (!isLoadingHTML)
				{
					setDataWebView(this)
				}
			}
			item.queryBase2?.run {
				this.toString()
			}
		}
		if (item is QueryBase)
		{
			setDataWebView(item)
		}
	}

	@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
	private fun setDataWebView(queryBase: QueryBase)
	{
		changeHeightParent(rlWebView, queryBase)
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
					webView.visibility = View.VISIBLE
					Handler(Looper.getMainLooper()).postDelayed({
						rlLoad?.visibility = View.GONE
					}, 200)
				}
			}
			setOnTouchListener { view, _ ->
				view.parent.requestDisallowInterceptTouchEvent(true)
				false
			}
		}
	}

	private val aChartSupport = arrayListOf(
		"bar",
		"line",
		"column",
		"pie",
		"heatmap",
		"bubble",
		"stacked_column",
		"stacked_line",
		"stacked_bar")
	private fun changeHeightParent(rlWebView: RelativeLayout, queryBase: QueryBase)
	{
		rlWebView.let {
			val numRows = queryBase.rowsTable
			val tmpRows = if (numRows == 0) 180 else numRows
			var customHeight = it.dpToPx(30f * tmpRows) + 60
			if (customHeight > 900 || queryBase.displayType in aChartSupport)
			{
				customHeight = 900
			}
			it.layoutParams = LinearLayout.LayoutParams(-1, customHeight)
		}
	}
}