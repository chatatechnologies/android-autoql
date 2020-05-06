package chata.can.chata_ai_api.fragment.dashboard.holder

import android.view.View
import android.webkit.WebView
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai_api.R

class HolderWebView(itemView: View): Holder(itemView)
{
	private val ll1 = itemView.findViewById<View>(R.id.ll1) ?: null
	private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle) ?: null
	private val rlWebView = itemView.findViewById<RelativeLayout>(R.id.rlWebView)
	private val webView = itemView.findViewById<WebView>(R.id.webView)
	private val rlLoad = itemView.findViewById<View>(R.id.rlLoad)

	override fun onPaint()
	{

	}

	override fun onBind(item: Any?, listener: OnItemClickListener?)
	{

	}
}