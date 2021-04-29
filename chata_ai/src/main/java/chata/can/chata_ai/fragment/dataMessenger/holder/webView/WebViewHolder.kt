package chata.can.chata_ai.fragment.dataMessenger.holder.webView

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.ListPopup
import chata.can.chata_ai.dialog.listPopup.DataPopup
import chata.can.chata_ai.extension.backgroundGrayWhite
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import java.util.*

class WebViewHolder(
	itemView: View,
	private val adapterView: ChatAdapterContract?,
	private val chatView: ChatContract.View?
): Holder(itemView), View.OnClickListener, WebViewContract
{
	private val rvContentTop: View = itemView.findViewById(R.id.rvContentTop)
	private val tvContentTop: TextView = itemView.findViewById(R.id.tvContentTop)

	private val rvParent = itemView.findViewById<View>(R.id.rvParent) ?: null
	private val wbQuery = itemView.findViewById<WebView>(R.id.wbQuery) ?: null
	private var rlLoad = itemView.findViewById<View>(R.id.rlLoad) ?: null
	private val ivAlert = itemView.findViewById<ImageView>(R.id.ivAlert) ?: null

	private val llCharts = itemView.findViewById<View>(R.id.llCharts) ?: null
	private val ivTable = itemView.findViewById<ImageView>(R.id.ivTable) ?: null
	private val ivColumn = itemView.findViewById<ImageView>(R.id.ivColumn) ?: null
	private val ivBar = itemView.findViewById<ImageView>(R.id.ivBar) ?: null
	private val ivLine = itemView.findViewById<ImageView>(R.id.ivLine) ?: null
	private val ivPie = itemView.findViewById<ImageView>(R.id.ivPie) ?: null
	private val ivPivot = itemView.findViewById<ImageView>(R.id.ivPivot) ?: null
	private val ivHeat = itemView.findViewById<ImageView>(R.id.ivHeat) ?: null
	private val ivBubble = itemView.findViewById<ImageView>(R.id.ivBubble) ?: null
	private var ivStackedBar = itemView.findViewById<ImageView>(R.id.ivStackedBar) ?: null
	private var ivStackedColumn = itemView.findViewById<ImageView>(R.id.ivStackedColumn) ?: null
	private var ivStackedArea = itemView.findViewById<ImageView>(R.id.ivStackedArea) ?: null
	private val aDefaultActions =
		arrayListOf(ivPivot, ivColumn, ivBar, ivLine, ivPie, ivTable,
			ivHeat, ivBubble, ivStackedBar, ivStackedColumn, ivStackedArea)

	private val rlDelete = itemView.findViewById<View>(R.id.rlDelete) ?: null
	private val ivPoints = itemView.findViewById<ImageView>(R.id.ivPoints) ?: null

	private var ivActionHide: ImageView ?= null
	private var queryBase: QueryBase ?= null
	private var lastId = "#idTableBasic"
	private val factorHeight = 180
	private val visible = View.VISIBLE
	private val invisible = View.GONE
	private var isFilter = false

	private var isReduceOptions = false
	private var canChangeHeight = false

	private var accentColor = 0

	override fun onPaint()
	{
		tvContentTop.run {
			val textColor = context.getParsedColor(R.color.chata_drawer_hover_color)
			setTextColor(textColor)

			val queryDrawable = DrawableBuilder.setGradientDrawable(
				SinglentonDrawer.currentAccent,18f)
			background = queryDrawable

			val animationTop = AnimationUtils.loadAnimation(context, R.anim.scale)
			startAnimation(animationTop)

			accentColor = SinglentonDrawer.currentAccent
			ivPoints?.setColorFilter(accentColor)
		}

		rlLoad?.run {
			setBackgroundColor(ThemeColor.currentColor.pDrawerBackgroundColor)
		}

		llCharts?.backgroundGrayWhite()
		rlDelete?.backgroundGrayWhite()

		ivPoints?.setOnClickListener(this)

		rvParent?.let {
			parent ->
			parent.backgroundGrayWhite()
			val animation = AnimationUtils.loadAnimation(parent.context, R.anim.scale)
			parent.startAnimation(animation)
		}
	}

	override fun showFilter()
	{
		queryBase?.run {
			if (isFilter)
			{
				isFilter = false
				rowsTable--
				rowsPivot--
			}
			else
			{
				rowsTable++
				rowsPivot++
				isFilter = true
			}

			val lastNum = when(lastId)
			{
				"#idTableBasic" -> rowsTable
				"#idTableDataPivot" -> rowsPivot
				"#container" -> factorHeight
				else -> 0
			}
			changeHeightWebView(lastNum)
			wbQuery?.loadUrl("javascript:showFilter();")
		}
	}

	private fun addActionViews(configActions: Int)
	{
		if (configActions != 0)
		{
			val aConfigs = when(configActions)
			{
				1 -> ConfigActions.biConfig
				2 ->
				{
					ConfigActions.triReduceConfig
				}
				3 -> ConfigActions.triConfig
				4 ->
				{
					lastId = "#idTableBasic"
					ConfigActions.biConfigReduce
				}
				5 ->
				{
					ConfigActions.triStackedConfig
				}
				6 ->
				{
					ConfigActions.triBiBarColumnConfig
				}
				else ->
				{
					lastId = "#idTableBasic"
					arrayListOf()
				}
			}
			queryBase?.let {
				if (it.isGroupable &&
					(R.id.ivColumn in aConfigs || R.id.ivStackedColumn in aConfigs))
				{
					Collections.swap(aConfigs, 0, if (R.id.ivPivot in aConfigs) 2 else 1)
					lastId = "#container"
				}
				if (R.id.ivPie in aConfigs)
				{
					//check rows (for pie, bar, column is series)
					if (/*it.hasDrillDown && */it.aRows.size > 6)
					{
						aConfigs.remove(R.id.ivPie)
					}
				}
				it.showContainer = lastId
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
							ivActionHide = view
							break
						}
					}
				}
			}
			//endregion

			val tmpConfigs = aConfigs.subList(1, aConfigs.size)
			for (index in 0 until aDefaultActions.size)
			{
				aDefaultActions[index]?.let {
					it.visibility = if (it.id in tmpConfigs)
					{
						it.setOnClickListener(this)
						it.setColorFilter(accentColor)
						visible
					}
					else
					{
						it.setOnClickListener(null)
						invisible
					}
				}
			}
			ivActionHide?.setOnClickListener(this)
		}
	}

	override fun onClick(v: View?)
	{
		v?.let {
			when(it.id)
			{
				R.id.ivTable, R.id.ivBar, R.id.ivColumn, R.id.ivLine, R.id.ivPie, R.id.ivPivot,
				R.id.ivBubble, R.id.ivHeat, R.id.ivStackedBar, R.id.ivStackedColumn, R.id.ivStackedArea ->
				{
					(it as? ImageView)?.let { imageView -> callAction(imageView) }
				}
				R.id.ivDelete ->
				{
					adapterView?.deleteQuery(adapterPosition)
				}
				R.id.ivReport ->
				{
					ListPopup.showListPopup(it, queryBase?.queryId ?: "", chatView)
				}
				R.id.ivPoints ->
				{
					val dataPopup = DataPopup(
						it,
						chatView,
						adapterView,
						adapterPosition,
						queryBase?.queryId ?: "",
						queryBase?.sql ?: "",
						lastId == "#idTableBasic" || lastId == "#idTableDataPivot",
						lastId == "#idTableDataPivot")
					ListPopup.showPointsPopup(it, queryBase?.sql ?: "", dataPopup, queryBase, this)
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
		rvContentTop.visibility = if (simpleQuery.visibleTop) View.VISIBLE else View.INVISIBLE
		if (simpleQuery.query.isNotEmpty())
		{
			tvContentTop.visibility = visible
			tvContentTop.text = simpleQuery.query
		}
		else
		{
			tvContentTop.visibility = invisible
		}

		queryBase = simpleQuery
		addActionViews(simpleQuery.configActions)

		if (simpleQuery.contentHTML.isNotEmpty())
		{
			rlLoad?.visibility = visible
			wbQuery?.let {
				wbQuery ->
				loadDataForWebView(wbQuery, simpleQuery.contentHTML, simpleQuery.rowsTable)
			}
		}
		ivAlert?.let { ivAlert ->
			ivAlert.visibility = if (
				simpleQuery.hasDrillDown &&
				simpleQuery.limitRowNum <= simpleQuery.aRows.size)
			{
				ivAlert.setOnClickListener {
					chatView?.showToast()
				}
				View.VISIBLE
			} else View.GONE
		}
	}

	private fun callAction(iv: ImageView?)
	{
		queryBase?.let {
				queryBase ->
			iv?.let {
				val pData = when(iv.id)
				{
					R.id.ivTable ->
					{
						val idHide = lastId
						lastId = "#idTableBasic"
						//Pair("'$idHide', '#idTableBasic', ''", queryBase.rowsTable)
						Pair("TypeEnum.TABLE", queryBase.rowsTable)
					}
					R.id.ivBar ->
					{
						val idHide = lastId
						lastId = "#container"
						if (queryBase.isContrast)
							Pair("'$idHide', '#container', 'contrast_bar'", factorHeight)
						else
						{
							if (queryBase.isTri && !queryBase.isTriInBi)
							{
								Pair("'$idHide', '#container', 'stacked_bar'", factorHeight)
							}
							else
							{
//								Pair("'$idHide', '#container', 'bar'", factorHeight)
								Pair("TypeEnum.BAR", factorHeight)
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
							if (queryBase.isTri && !queryBase.isTriInBi)
							{
								Pair("'$idHide', '#container', 'stacked_column'", factorHeight)
							}
							else
							{
//								Pair("'$idHide', '#container', 'column'", factorHeight)
								Pair("TypeEnum.COLUMN", factorHeight)
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
//							Pair("'$idHide', '#container', 'line'", factorHeight)
							Pair("TypeEnum.LINE", factorHeight)
					}
					R.id.ivPie ->
					{
						val idHide = lastId
						lastId = "#container"
//						Pair("'$idHide', '#container', 'pie'", factorHeight)
						Pair("TypeEnum.PIE", factorHeight)
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
					R.id.ivStackedBar ->
					{
						val idHide = lastId
						lastId = "#container"
						Pair("'$idHide', '#container', 'stacked_bar'", factorHeight)
					}
					R.id.ivStackedColumn ->
					{
						val idHide = lastId
						lastId = "#container"
						Pair("'$idHide', '#container', 'stacked_column'", factorHeight)
					}
					R.id.ivStackedArea ->
					{
						val idHide = lastId
						lastId = "#container"
						Pair("'$idHide', '#container', 'stacked_area'", factorHeight)
					}
					else -> Pair("", factorHeight)
				}
				canChangeHeight = true
				changeHeightWebView(pData.second)
				queryBase.showContainer = lastId
				wbQuery?.run {
					requestLayout()
					Handler(Looper.getMainLooper()).postDelayed({
						loadUrl("javascript:updateData(${pData.first});")
					}, 200)
				}

				ivActionHide?.run {
					visibility = visible
					setColorFilter(accentColor)
				}
				ivActionHide = iv
				ivActionHide?.visibility = invisible
			}
		}
	}

	@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
	private fun loadDataForWebView(webView: WebView, data: String, numRows: Int)
	{
		with(webView)
		{
			rlLoad?.visibility = visible
			clearCache(true)
			clearHistory()
			requestLayout()
			if (lastId == "#container")
			{
				changeHeightWebView(factorHeight)
			}
			else
			{
				changeHeightWebView(numRows)
			}
			settings.javaScriptEnabled = true
			queryBase?.let {
				if (it.hasDrillDown)
				{
					addJavascriptInterface(
						JavaScriptInterface(webView.context, it, chatView), "Android")
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
					visibility = visible
					Handler(Looper.getMainLooper()).postDelayed({
						rlLoad?.visibility = invisible
					}, 200)
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
		if (queryBase?.canChangeHeight == true)
		{
			queryBase?.canChangeHeight = false
			rvParent?.let {
				var customHeight = it.dpToPx(30f * numRows) + 60
				if (customHeight > 900)
				{
					customHeight = 900
				}

				it.layoutParams = RelativeLayout.LayoutParams(-1, customHeight)
				it.margin(12f, 32f, 12f, 1f)
				chatView?.scrollToPosition()
			}
		}
	}
}