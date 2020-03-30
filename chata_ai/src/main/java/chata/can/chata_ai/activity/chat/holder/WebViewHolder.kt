package chata.can.chata_ai.activity.chat.holder

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class WebViewHolder(view: View): Holder(view)
{
	private val rvParent = view.findViewById<View>(R.id.rvParent) ?: null
	private val wbQuery = view.findViewById<WebView>(R.id.wbQuery) ?: null
	private var rlLoad = view.findViewById<View>(R.id.rlLoad) ?: null

	override fun onPaint()
	{
		rvParent?.let {
			val white = ContextCompat.getColor(it.context, R.color.chata_drawer_background_color)
			val gray = ContextCompat.getColor(it.context, R.color.chata_drawer_color_primary)
			val queryDrawable = DrawableBuilder.setGradientDrawable(white,18f,1, gray)
			it.background = queryDrawable
		}
	}


	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{
		if (item is ChatData)
		{
			item.simpleQuery?.let {
				if (it is QueryBase)
				{
					processQueryBase(it)
				}
			}
		}

		if (item is QueryBase)
		{
			processQueryBase(item)
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	fun processQueryBase(simpleQuery: QueryBase)
	{
		if (simpleQuery.contentHTML.isNotEmpty())
		{
			rlLoad?.visibility = View.VISIBLE
			wbQuery?.let {
				wbQuery ->
				with(wbQuery)
				{
					clearCache(true)
					clearHistory()
					//requestLayout()

					settings.javaScriptEnabled = true
					loadDataWithBaseURL(null, simpleQuery.contentHTML,"text/html","UTF-8", null)
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
	}
}