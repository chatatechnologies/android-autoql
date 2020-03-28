package chata.can.chata_ai.activity.chat.holder

import android.view.View
import android.webkit.WebView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class WebViewHolder(view: View): Holder(view)
{
	private val rvParent = view.findViewById<View>(R.id.rvParent) ?: null
	private val wbQuery = view.findViewById<WebView>(R.id.wbQuery) ?: null

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

	}
}