package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai_api.R

class HolderSingle(itemView: View): Holder(itemView)
{
	private val ll1 = itemView.findViewById<View>(R.id.ll1) ?: null
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle) ?: null
	private val rlWebView = itemView.findViewById<RelativeLayout>(R.id.rlWebView) ?: null
	private val tvContent = itemView.findViewById<TextView>(R.id.tvContent) ?: null
	private val webView = itemView.findViewById<WebView>(R.id.webView) ?: null
	private val rlLoad = itemView.findViewById<View>(R.id.rlLoad) ?: null
	private val tvExecute = itemView.findViewById<TextView>(R.id.tvExecute) ?: null

	override fun onPaint()
	{
		ll1?.let {
			val context = it.context
			val white = ContextCompat.getColor(
				context,
				ThemeColor.currentColor.drawerBackgroundColor)
			val gray = ContextCompat.getColor(context, ThemeColor.currentColor.drawerColorPrimary)

			it.background = DrawableBuilder.setGradientDrawable(white,18f,1, gray)
			tvExecute?.setBackgroundColor(white)
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is Dashboard)
		{
			val titleToShow = if (item.title.isNotEmpty()) item.title else item.query
			tvTitle?.text = titleToShow

			if (item.queryBase != null)
			{
				item.queryBase?.let {
					queryBase ->

					when(queryBase.typeView)
					{
						TypeChatView.LEFT_VIEW ->
						{
							rlWebView?.layoutParams = LinearLayout.LayoutParams(-1, -2)
							tvContent?.visibility = View.VISIBLE
							rlLoad?.visibility = View.GONE
							webView?.visibility = View.GONE
							tvExecute?.visibility = View.GONE

							tvContent?.text = queryBase.contentHTML
						}
						TypeChatView.WEB_VIEW ->
						{
							changeHeightParent(queryBase.rowsTable)
							tvContent?.visibility = View.GONE
							tvExecute?.visibility = View.GONE
							rlLoad?.visibility = View.VISIBLE

							webView?.let {
								with(it)
								{
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
											visibility = View.VISIBLE
										}
									}
									setOnTouchListener { view, _ ->
										view.parent.requestDisallowInterceptTouchEvent(true)
										false
									}
								}
							}
						}
						else -> {}
					}
				}
			}
			else
			{
				webView?.visibility = View.GONE
				rlLoad?.visibility = View.GONE
			}
		}
	}

	private fun changeHeightParent(numRows: Int)
	{
		rlWebView?.let {
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