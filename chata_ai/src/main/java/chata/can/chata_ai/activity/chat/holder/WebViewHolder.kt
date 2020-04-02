package chata.can.chata_ai.activity.chat.holder

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class WebViewHolder(view: View): Holder(view)
{
	private val rvParent = view.findViewById<View>(R.id.rvParent) ?: null
	private val wbQuery = view.findViewById<WebView>(R.id.wbQuery) ?: null
	private var rlLoad = view.findViewById<View>(R.id.rlLoad) ?: null

	private val llCharts = view.findViewById<View>(R.id.llCharts) ?: null
	private val ivBar = view.findViewById<ImageView>(R.id.ivBar) ?: null
	private val ivColumn = view.findViewById<ImageView>(R.id.ivColumn) ?: null
	private val ivLine = view.findViewById<ImageView>(R.id.ivLine) ?: null
	private val ivPie = view.findViewById<ImageView>(R.id.ivPie) ?: null

	override fun onPaint()
	{
		rvParent?.let {
			it.background = backgroundGrayWhite(it)
		}

		llCharts?.let {
			it.background = backgroundGrayWhite(it)
		}
		ivBar?.setColorFilter()
		ivColumn?.setColorFilter()
		ivLine?.setColorFilter()
		ivPie?.setColorFilter()
	}

	private fun ImageView.setColorFilter()
	{
		setColorFilter(ContextCompat.getColor(
			context,
			ThemeColor.currentColor.drawerColorPrimary
		))
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

	private fun isChart(numColumns: Int)
	{
		llCharts?.let {
			it.visibility =
			if (numColumns == 2)
			{
				View.VISIBLE
			}
			else
			{
				View.GONE
			}
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	fun processQueryBase(simpleQuery: QueryBase)
	{
		isChart(simpleQuery.numColumns)
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

	private fun backgroundGrayWhite(view: View): GradientDrawable
	{
		val white = ContextCompat.getColor(
			view.context,
			ThemeColor.currentColor.drawerBackgroundColor)
		val gray = ContextCompat.getColor(
			view.context,
			ThemeColor.currentColor.drawerColorPrimary)
		return  DrawableBuilder.setGradientDrawable(white,18f,1, gray)
	}
}