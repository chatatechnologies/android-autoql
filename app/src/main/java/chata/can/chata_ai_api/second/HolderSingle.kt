package chata.can.chata_ai_api.second

import android.view.View
import android.webkit.WebView
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

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{

	}
}