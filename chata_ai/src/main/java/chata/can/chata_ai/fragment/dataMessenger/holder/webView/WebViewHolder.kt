package chata.can.chata_ai.fragment.dataMessenger.holder.webView

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.ListPopup
import chata.can.chata_ai.dialog.listPopup.DataPopup
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract
import chata.can.chata_ai.fragment.dataMessenger.holder.Interpretation
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.listener.OnItemClickListener
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import java.util.*

/*For Data Messenger*/
class WebViewHolder(
	itemView: View,
	private val adapterView: ChatAdapterContract?,
	private val chatView: ChatContract.View?
): Holder(itemView), View.OnClickListener, WebViewContract
{
	private val rvContentTop: View = itemView.findViewById(R.id.rvContentTop)
	private val tvContentTop: TextView = itemView.findViewById(R.id.tvContentTop)

	private val llParent = itemView.findViewById<LinearLayout>(R.id.llParent) ?: null
	private val rvParent = itemView.findViewById<View>(R.id.rvParent) ?: null
	private val wbQuery = itemView.findViewById<WebView>(R.id.wbQuery) ?: null
	private var rlLoad = itemView.findViewById<View>(R.id.rlLoad) ?: null
	private val ivAlert = itemView.findViewById<ImageView>(R.id.ivAlert) ?: null

	private val tvInterpreter = itemView.findViewById<TextView>(R.id.tvInterpreter) ?: null

	private val rlCharts = itemView.findViewById<View>(R.id.rlCharts) ?: null
	private val ivCharts = itemView.findViewById<ImageView>(R.id.ivCharts) ?: null
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

	private var idViewSelected = 0

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
			ivCharts?.setColorFilter(accentColor)
			ivPoints?.setColorFilter(accentColor)
			rotateCharts()
		}

		rlLoad?.run {
			setBackgroundColor(ThemeColor.currentColor.pDrawerBackgroundColor)
		}

		rlCharts?.backgroundGrayWhite()
		llCharts?.backgroundGrayWhite()
		rlDelete?.backgroundGrayWhite()

		ivPoints?.setOnClickListener(this)

		llParent?.backgroundGrayWhite()
		rvParent?.let {
			parent ->
//			parent.backgroundGrayWhite()
			val animation = AnimationUtils.loadAnimation(parent.context, R.anim.scale)
			parent.startAnimation(animation)
		}
	}

	override fun rotateCharts() {
		ivCharts?.rotation = 0f
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

			val lastNum = when(this@WebViewHolder.lastId)
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
			val aMutable = when(configActions)
			{
				1 -> ConfigActions.biConfig
				2 -> ConfigActions.triReduceConfig
				3 -> ConfigActions.triConfig
				4 ->
				{
					lastId = "#idTableBasic"
					ConfigActions.biConfigReduce
				}
				5 -> ConfigActions.triStackedConfig
				6 -> ConfigActions.triBiBarColumnConfig
				else ->
				{
					lastId = "#idTableBasic"
					arrayListOf()
				}
			}
			val aConfigs = aMutable.toMutableList()
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
							idViewSelected = firstConfig
							ivActionHide = view
							break
						}
					}
				}
			}
			//endregion
			if (aConfigs.size == 10)
			{
				llCharts?.visibility = View.GONE
				rlCharts?.visibility = View.VISIBLE
				ivCharts?.setOnClickListener(this)
			}
			else
			{
				llCharts?.visibility = View.VISIBLE
				rlCharts?.visibility = View.GONE
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
		else
		{
			llCharts?.visibility = View.GONE
			rlCharts?.visibility = View.GONE
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
				R.id.ivCharts ->
				{
					ivCharts?.rotation = 180f
					ListPopup.showChartPopup(it, idViewSelected, this)
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
		if (simpleQuery.onlyHTML)
		{
			simpleQuery.onlyHTML = false
			wbQuery?.let { wbQuery ->
				loadDataForWebView(wbQuery, simpleQuery)
			}
		}
		else
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
				wbQuery?.let { wbQuery ->
					loadDataForWebView(wbQuery, simpleQuery)
				}
			}
			val interpreter = "<b>Interpreted as:</b> ${simpleQuery.interpretation}"
			tvInterpreter?.let { Interpretation.setUnderLine(simpleQuery.interpretation, it) }
//			tvInterpreter?.fromHtml(interpreter)
		}
	}

	private fun configChangeHeight(compareId: String)
	{
		queryBase?.canChangeHeight = lastId != compareId
	}

	override fun callAction(iv: ImageView?)
	{
		queryBase?.let { queryBase ->
			iv?.let {
				idViewSelected = it.id
				if (isFilter && (lastId == "#idTableBasic" || lastId == "#idTableDataPivot"))
				{
					showFilter()
				}
				//region can to change the height
				val config = when(iv.id)
				{
					R.id.ivTable -> "#idTableBasic"
					R.id.ivPivot -> "#idTableDataPivot"
					else -> "#container"
				}
				configChangeHeight(config)

				val pData = when(iv.id)
				{
					R.id.ivTable ->
					{
//						val idHide = lastId
						lastId = "#idTableBasic"
//						Pair("'$idHide', '#idTableBasic', ''", queryBase.rowsTable)
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
								//Pair("'$idHide', '#container', 'stacked_bar'", factorHeight)
								Pair("TypeEnum.BAR", factorHeight)
							}
							else
							{
								//Pair("'$idHide', '#container', 'bar'", factorHeight)
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
								//Pair("'$idHide', '#container', 'stacked_column'", factorHeight)
								Pair("TypeEnum.COLUMN", factorHeight)
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
//						val idHide = lastId
						lastId = "#container"
//						Pair("'$idHide', '#container', 'pie'", factorHeight)
						Pair("TypeEnum.PIE", factorHeight)
					}
					R.id.ivBubble ->
					{
						//val idHide = lastId
						lastId = "#container"
						//Pair("'$idHide', '#container', 'bubble'", factorHeight)
						Pair("TypeEnum.BUBBLE", factorHeight)
					}
					R.id.ivHeat ->
					{
//						val idHide = lastId
						lastId = "#container"
						//Pair("'$idHide', '#container', 'heatmap'", factorHeight)
						Pair("TypeEnum.HEATMAP", factorHeight)
					}
					R.id.ivPivot ->
					{
//						val idHide = lastId
						lastId = "#idTableDataPivot"
//						Pair("'$idHide', '#idTableDataPivot', '#idTableDataPivot'", queryBase.rowsPivot)
						Pair("TypeEnum.PIVOT", queryBase.rowsPivot)
					}
					R.id.ivStackedBar ->
					{
//						val idHide = lastId
						lastId = "#container"
//						Pair("'$idHide', '#container', 'stacked_bar'", factorHeight)
						Pair("TypeEnum.STACKED_BAR", queryBase.rowsPivot)
					}
					R.id.ivStackedColumn ->
					{
//						val idHide = lastId
						lastId = "#container"
//						Pair("'$idHide', '#container', 'stacked_column'", factorHeight)
						Pair("TypeEnum.STACKED_COLUMN", queryBase.rowsPivot)
					}
					R.id.ivStackedArea ->
					{
						val idHide = lastId
						lastId = "#container"
						Pair("'$idHide', '#container', 'stacked_area'", factorHeight)
					}
					else -> Pair("", factorHeight)
				}
				changeHeightWebView(pData.second)
				queryBase.showContainer = lastId
				wbQuery?.run {
					requestLayout()
					Handler(Looper.getMainLooper()).postDelayed({
//						loadUrl("javascript:hideTables(${pData.first});")
						loadUrl("javascript:updateData(${pData.first});")
					}, 100)
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
	private fun loadDataForWebView(webView: WebView, queryBase: QueryBase)
	{
		queryBase.run {
			with(webView)
			{
				rlLoad?.visibility = visible
				clearCache(true)
				clearHistory()
				requestLayout()
				val tmp = if (lastId == "#container") factorHeight
				else this@run.rowsTable
				changeHeightWebView(tmp)
				settings.javaScriptEnabled = true
				if (this@run.hasDrillDown)
				{
					addJavascriptInterface(
						JavaScriptInterface(webView, webView.context, this@run, chatView), "Android")
				}
				loadDataWithBaseURL(
					null,
					this@run.contentHTML,
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

							ivAlert?.let { ivAlert ->
								ivAlert.visibility = if (
								//simpleQuery.hasDrillDown &&
									this@run.limitRowNum <= this@run.aRows.size)
								{
									ivAlert.setOnClickListener {
										chatView?.showToast()
									}
									View.VISIBLE
								} else View.GONE
							}
						}, 200)
					}
				}

				setOnTouchListener { view, _ ->
					view.parent.requestDisallowInterceptTouchEvent(true)
					false
				}
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

				it.layoutParams = LinearLayout.LayoutParams(-1, customHeight)
				it.margin(12f, 32f, 12f, 1f)
				chatView?.scrollToPosition()
			}
		}
	}
}
