package chata.can.chata_ai.fragment.dataMessenger.holder.webView

import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.setColorFilter
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase

class WebViewHolder(
	itemView: View,
	private val adapterView: ChatAdapterContract?,
	private val chatView: ChatContract.View?
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
	private val aDefaultActions =
		arrayListOf(ivPivot, ivColumn, ivBar, ivLine, ivPie, ivTable, ivHeat, ivBubble)

	private val rlDelete = itemView.findViewById<View>(R.id.rlDelete) ?: null
	private val ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete) ?: null

	private var ivActionHide: ImageView ?= null
	private var queryBase: QueryBase ?= null
	//private var lastId = "#idTableBasic"
	private var lastId = "#idTableDataPivot"

	//region paint views
	override fun onPaint()
	{
		llCharts?.backgroundGrayWhite()
		setColorFilters()
		rlDelete?.let {
			it.backgroundGrayWhite()
			it.setOnClickListener(this)
		}
		rvParent?.let {
			parent ->
			parent.backgroundGrayWhite()
			val animation = AnimationUtils.loadAnimation(parent.context, R.anim.scale)
			parent.startAnimation(animation)
		}
	}

	private fun addActionViews(configActions: Int)
	{
		if (configActions != 0)
		{
			val aConfigs = when(configActions)
			{
				1 -> ConfigActions.biConfig
				2 -> ConfigActions.triReduceConfig
				3 -> ConfigActions.triConfig
				4 -> ConfigActions.biConfigReduce
				else -> arrayListOf()
			}
			//region find the first item
			aConfigs.firstOrNull()?.let {
				firstConfig ->
				for (view in aDefaultActions)
				{
					if (view != null)
					{
						if (view.id == firstConfig)
						{
							view.setOnClickListener(this)
							ivActionHide = view
							break
						}
					}
				}
			}
			//endregion

			for (index in 1 until aDefaultActions.size)
			{
				aDefaultActions[index]?.let {
					val idView = it.id
					it.visibility = if (idView in aConfigs)
					{
						it.setOnClickListener(this)
						View.VISIBLE
					}
					else
					{
						it.setOnClickListener(null)
						View.GONE
					}
				}
			}
		}
	}
	//endregion

	override fun onClick(v: View?)
	{
		v?.let {
			when(it.id)
			{
				R.id.ivTable, R.id.ivBar, R.id.ivColumn, R.id.ivLine, R.id.ivPie, R.id.ivPivot,
				R.id.ivBubble, R.id.ivHeat ->
				{
					(it as? ImageView)?.let { imageView -> callAction(imageView) }
				}
				R.id.rlDelete ->
				{
					//region delete query
					adapterView?.deleteQuery(adapterPosition)
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

	private fun processQueryBase(simpleQuery: QueryBase)
	{
		queryBase = simpleQuery
		addActionViews(simpleQuery.configActions)

		if (simpleQuery.contentHTML.isNotEmpty())
		{
			rlLoad?.visibility = View.VISIBLE
			wbQuery?.let {
				wbQuery ->
				loadDataForWebView(wbQuery, simpleQuery.contentHTML, simpleQuery.rowsTable)
			}
		}
	}

	private fun setColorFilters()
	{
		ivTable?.setColorFilter()
		ivBar?.setColorFilter()
		ivColumn?.setColorFilter()
		ivLine?.setColorFilter()
		ivPie?.setColorFilter()
		ivPivot?.setColorFilter()
		ivHeat?.setColorFilter()
		ivBubble?.setColorFilter()
		ivDelete?.setColorFilter()
	}

	private fun callAction(iv: ImageView?)
	{
		queryBase?.let {
			queryBase ->
			iv?.let {
				val factorHeight = 180
				val pData = when(iv.id)
				{
					R.id.ivTable ->
					{
						val idHide = lastId
						lastId = "#idTableBasic"
						Pair("'$idHide', '#idTableBasic', ''", queryBase.rowsTable)
					}
					R.id.ivBar ->
					{
						val idHide = lastId
						lastId = "#container"
						if (queryBase.isContrast)
							Pair("'$idHide', '#container', 'contrast_bar'", factorHeight)
						else
						{
							if (queryBase.isTri)
							{
								Pair("'$idHide', '#container', 'stacked_bar'", factorHeight)
							}
							else
							{
								Pair("'$idHide', '#container', 'bar'", factorHeight)
							}
						}
					}
					R.id.ivColumn ->
					{
						val idHide = lastId
						lastId = "#container"
						if (queryBase.isContrast)
							Pair("'$idHide', '#container', 'contrast_column'", factorHeight)
						else
						{
							if (queryBase.isTri)
							{
								Pair("'$idHide', '#container', 'stacked_column'", factorHeight)
							}
							else
							{
								Pair("'$idHide', '#container', 'column'", factorHeight)
							}
						}
					}
					R.id.ivLine ->
					{
						val idHide = lastId
						lastId = "#container"
						if (queryBase.isContrast)
							Pair("'$idHide', '#container', 'contrast_line'", factorHeight)
						else
							Pair("'$idHide', '#container', 'line'", factorHeight)
					}
					R.id.ivPie ->
					{
						val idHide = lastId
						lastId = "#container"
						Pair("'$idHide', '#container', 'pie'", factorHeight)
					}
					R.id.ivBubble ->
					{
						val idHide = lastId
						lastId = "#container"
						Pair("'$idHide', '#container', 'bubble'", factorHeight)
					}
					R.id.ivHeat ->
					{
						val idHide = lastId
						lastId = "#container"
						Pair("'$idHide', '#container', 'heatmap'", factorHeight)
					}
					R.id.ivPivot ->
					{
						val idHide = lastId
						lastId = "#idTableDataPivot"
						Pair("'$idHide', '#idTableDataPivot', ''", queryBase.rowsPivot)
					}
					else -> Pair("", factorHeight)
				}
				changeHeightWebView(pData.second)
				wbQuery?.run {
					requestLayout()
					Handler().postDelayed({
						loadUrl("javascript:hideTables(${pData.first});")
					}, 200)
				}

				ivActionHide?.visibility = View.VISIBLE
				ivActionHide = iv
				ivActionHide?.visibility = View.GONE
			}
		}
	}

	@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
	private fun loadDataForWebView(webView: WebView, data: String, numRows: Int)
	{
		with(webView)
		{
			changeHeightWebView(numRows)

			rlLoad?.visibility = View.VISIBLE
			clearCache(true)
			clearHistory()
			requestLayout()

			settings.javaScriptEnabled = true
			queryBase?.let {
				if (it.hasDrillDown)
				{
					addJavascriptInterface(JavaScriptInterface(it, chatView), "Android")
				}
			}
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
				}
			}

			setOnTouchListener { view, _ ->
				view.parent.requestDisallowInterceptTouchEvent(true)
				false
			}
		}
	}

	private fun changeHeightWebView(numRows: Int)
	{
		rvParent?.let {
			var customHeight = rvParent.dpToPx(30f * numRows) + 60
			if (customHeight > 900)
			{
				customHeight = 900
			}

			it.layoutParams = RelativeLayout.LayoutParams(-1, customHeight)
			it.margin(12f, 24f, 12f, 1f)
		}
	}
}