package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai_api.R

class HolderSingle(itemView: View): Holder(itemView)
{
	private val ll1 = itemView.findViewById<View>(R.id.ll1) ?: null
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle) ?: null
	private val rlWebView = itemView.findViewById<RelativeLayout>(R.id.rlWebView) ?: null
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
				println("Queery $item")
//				rlLoad?.visibility = View.VISIBLE
//				webView?.let {
//					with(it)
//					{
//						visibility = View.VISIBLE
//
//						clearCache(true)
//						clearHistory()
//						requestLayout()
//
//						val url = "https://gitlab.com/users/sign_in"
//						settings.javaScriptEnabled = true
//						webView.loadUrl(url)
//						webViewClient = object: WebViewClient()
//						{
//							override fun onPageFinished(view: WebView?, url: String?)
//							{
//								rlLoad?.visibility = View.GONE
//								tvExecute?.visibility = View.GONE
//							}
//						}
//
//						setOnTouchListener { view, _ ->
//							view.parent.requestDisallowInterceptTouchEvent(true)
//							false
//						}
//					}
//				}
			}
			else
			{
				webView?.visibility = View.GONE
				rlLoad?.visibility = View.GONE
			}
		}

	}
}