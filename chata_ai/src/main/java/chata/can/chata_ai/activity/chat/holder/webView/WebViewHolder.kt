package chata.can.chata_ai.activity.chat.holder.webView

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.adapter.ChatAdapterContract
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class WebViewHolder(
	itemView: View,
	private val view: ChatAdapterContract
): Holder(itemView), View.OnClickListener
{
	private val rvParent = itemView.findViewById<View>(R.id.rvParent) ?: null
	private val wbQuery = itemView.findViewById<WebView>(R.id.wbQuery) ?: null
	private var rlLoad = itemView.findViewById<View>(R.id.rlLoad) ?: null

	private val llCharts = itemView.findViewById<View>(R.id.llCharts) ?: null
	private val ivTable = itemView.findViewById<ImageView>(R.id.ivTable) ?: null
	private val ivColumn = itemView.findViewById<ImageView>(R.id.ivColumn) ?: null
	private val ivBar = itemView.findViewById<ImageView>(R.id.ivBar) ?: null
	private val ivLine = itemView.findViewById<ImageView>(R.id.ivLine) ?: null
	private val ivPie = itemView.findViewById<ImageView>(R.id.ivPie) ?: null
	private val ivPivot = itemView.findViewById<ImageView>(R.id.ivPivot) ?: null
	private val ivHeat = itemView.findViewById<ImageView>(R.id.ivHeat) ?: null
	private val ivBubble = itemView.findViewById<ImageView>(R.id.ivBubble) ?: null

	private val rlDelete = itemView.findViewById<View>(R.id.rlDelete) ?: null
	private val ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete) ?: null

	private var ivActionHide: ImageView ?= null
	private var queryBase: QueryBase ?= null

	override fun onPaint()
	{
		rvParent?.let {
			it.background = backgroundGrayWhite(it)
		}

		llCharts?.let {
			it.background = backgroundGrayWhite(it)
		}
		ivTable?.setColorFilter()
		ivTable?.visibility = View.GONE
		ivBar?.setColorFilter()
		ivColumn?.setColorFilter()
		ivLine?.setColorFilter()
		ivPie?.setColorFilter()
		ivPivot?.setColorFilter()
		ivHeat?.setColorFilter()
		ivBubble?.setColorFilter()

		rlDelete?.let {
			it.background = backgroundGrayWhite(it)
		}
		ivDelete?.setColorFilter()
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

	private fun processQueryBase(simpleQuery: QueryBase)
	{
		queryBase = simpleQuery
		ivTable?.setOnClickListener(this)
		ivBar?.setOnClickListener(this)
		ivColumn?.setOnClickListener(this)
		ivLine?.setOnClickListener(this)
		ivPie?.setOnClickListener(this)
		rlDelete?.setOnClickListener(this)
		ivPivot?.setOnClickListener(this)
		ivHeat?.setOnClickListener(this)
		ivBubble?.setOnClickListener(this)

		isChart(simpleQuery.numColumns)
		if (simpleQuery.contentTable.isNotEmpty())
		{
			rlLoad?.visibility = View.VISIBLE
			ivActionHide = ivTable

			wbQuery?.let {
				wbQuery ->
				with(wbQuery)
				{
					loadDataForWebView(simpleQuery.contentTable, simpleQuery.rowsTable)

					setOnTouchListener { view, _ ->
						view.parent.requestDisallowInterceptTouchEvent(true)
						false
					}
				}
			}
		}
	}

	override fun onClick(v: View?)
	{
		v?.let {
			when(it.id)
			{
				R.id.ivTable, R.id.ivPivot->
				{
					if (it is ImageView)
					{
						loadActionAndData(it)
					}
				}
				R.id.ivBar ->
				{

				}
				R.id.ivColumn ->
				{

				}
				R.id.ivLine ->
				{

				}
				R.id.ivPie ->
				{

				}
				R.id.rlDelete ->
				{
					//region delete query
					view.deleteQuery(adapterPosition)
					//endregion
					//region copy to clipboard
//					if (it.context != null)
//					{
//						val clipboard =
//							it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//						val clip = ClipData.newPlainText("", queryBase?.sql ?: "")
//						clipboard.setPrimaryClip(clip)
//					}
					//endregion
					//region report problem
//					val presenter = WebViewPresenter()
//					presenter.putReport(queryBase?.queryId ?: "")
					//endregion
				}
				else -> {}
			}
		}
	}

	private fun loadActionAndData(iv: ImageView?)
	{
		queryBase?.let {
			queryBase ->
			iv?.let {
				val rows = when(iv.id)
				{
					R.id.ivTable -> queryBase.rowsTable
					R.id.ivPivot -> queryBase.rowsPivot
					else -> 0
				}

				wbQuery?.loadDataForWebView(queryBase.contentTable, rows)
				hideShowAction(iv)
			}
		}
	}

	private fun hideShowAction(ivToHide: ImageView?)
	{
		ivActionHide?.visibility = View.VISIBLE
		ivActionHide = ivToHide
		ivActionHide?.visibility = View.GONE
	}

	@SuppressLint("SetJavaScriptEnabled")
	private fun WebView.loadDataForWebView(data: String, numRows: Int)
	{
		var customHeight = rvParent?.dpToPx(30f * numRows) ?: 900
		if (customHeight > 900)
		{
			customHeight = 900
		}

		rlLoad?.visibility = View.VISIBLE
		rvParent?.let {
			it.layoutParams = RelativeLayout.LayoutParams(-1, customHeight)
			it.margin(12f, 24f, 12f, 1f)
		}

		clearCache(true)
		clearHistory()
		requestLayout()

		settings.javaScriptEnabled = true
		//addJavascriptInterface(JavaScriptInterface(), "Android")
		loadDataWithBaseURL(
			null,
			data,
			"text/html",
			"UTF-8",
			null)
		webViewClient = object: WebViewClient()
		{
			override fun onPageFinished(view: WebView?, url: String?)
			{
				rlLoad?.visibility = View.GONE
				visibility = View.VISIBLE
				//loadUrl("javascript:changeChart()")
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