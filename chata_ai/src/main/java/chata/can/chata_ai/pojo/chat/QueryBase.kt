package chata.can.chata_ai.pojo.chat

import chata.can.chata_ai.Executor
import chata.can.chata_ai.fragment.dataMessenger.presenter.PresenterContract
import chata.can.chata_ai.dialog.DrillDownContract
import chata.can.chata_ai.dialog.manageData.FilterColumn
import chata.can.chata_ai.holder.HolderContract
import chata.can.chata_ai.pojo.webView.HtmlBuilder
import chata.can.chata_ai.extension.enumValueOfOrNull
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.extension.isNumber
import chata.can.chata_ai.pojo.dataKey
import chata.can.chata_ai.pojo.messageKey
import chata.can.chata_ai.pojo.query.CountColumn
import chata.can.chata_ai.pojo.query.RulesHtml
import chata.can.chata_ai.pojo.query.SupportCase
import chata.can.chata_ai.pojo.referenceIdKey
import chata.can.chata_ai.pojo.webView.D3OnHtml
import chata.can.chata_ai.pojo.webView.DashboardMaker
import org.json.JSONObject

data class QueryBase(val json: JSONObject): SimpleQuery(json)
{
	var hasDrillDown = true
	var isDashboard = false
	val referenceId = json.optString(referenceIdKey) ?: ""
	private val joData = json.optJSONObject(dataKey)
	var message = json.optString(messageKey) ?: ""

	var sql: String = ""
	var queryId = ""
	var displayType = ""
	var showContainer = ""
	private var interpretation = ""
	var limitRowNum = 0

	var supportCase: SupportCase ?= null
	val aRows = ArrayList<ArrayList<String>>()
	val aIndex = ArrayList<Int>()
	var aColumn = ArrayList<ColumnQuery>()
	var canChangeHeight = true

	fun allVisible(): Boolean
	{
		var allVisible = true
		for (column in aColumn)
		{
			if (!column.isVisible)
				allVisible = false
		}
		return allVisible
	}

	var isSecondaryQuery = json.optBoolean("isSecondaryQuery", false)
	var typeSuggestion = json.optString("typeSuggestion", "") ?: ""

	val numColumns: Int
	get() {
		return aColumn.size
	}

	val isGroupable: Boolean
	get() {
		var value = false
		for (column in aColumn)
		{
			if (column.isGroupable)
			{
				value = true
				break
			}
		}
		return value
	}

	val hasHash: Boolean
	get() = simpleText.contains("#")

	val isSimpleText: Boolean
	get() {
		val sizeLevel1 = aRows.size
		return aRows.firstOrNull()?.let {
			val sizeLevel2 = it.size
			return sizeLevel1 == 1 && sizeLevel2 == 1
		} ?: run { false }
	}

	val simpleText: String
	get() {
		return aRows.firstOrNull()?.let {
			it.firstOrNull()?: run { "" }
		} ?: run { "" }
	}

	var configActions = 0
	var isContrast = false
	var isTri = false
	var isTriInBi = false
	var contentHTML = ""
	var rowsTable = 0
	var rowsPivot = 0
	lateinit var aXAxis: ArrayList<String>
	lateinit var aXDrillDown: ArrayList<String>
	var mSourceDrill: LinkedHashMap<Int, LinkedHashMap<String, ArrayList< ArrayList< ArrayList<String>> >>> = linkedMapOf()
	var indexData = -1
	fun getSourceDrill() = if(mSourceDrill.isNotEmpty()) mSourceDrill[indexData] else linkedMapOf()

	var aCurrency = ArrayList<Pair<Int, ColumnQuery>>()
	var aQuality = ArrayList<Pair<Int, ColumnQuery>>()
	var aCommon = ArrayList<Pair<Int, ColumnQuery>>()
	var aCategoryX = ArrayList<String>()
	var aCategory = ArrayList<FilterColumn>()

	private var view: HolderContract? = null
	var viewPresenter: PresenterContract?= null
	var viewDrillDown: DrillDownContract?= null
	var isLoadingHTML = true
	var onlyHTML = false
	var reloadTable = false

	init {
		joData?.let {
			sql = joData.optJSONArray("sql")?.let { data -> data[0].toString() } ?: ""
			queryId = joData.optString("query_id") ?: ""
			displayType = joData.optString("display_type") ?: ""
			interpretation = joData.optString("interpretation") ?: ""
			limitRowNum = joData.optInt("limit_row_num")

			//region rows
			it.optJSONArray("rows")?.let {
				jaRows ->
				aRows.clear()
				//each row
				for (index in 0 until jaRows.length())
				{
					val newRow = ArrayList<String>()
					val jaLevel2 = jaRows.optJSONArray(index)
					for (index2 in 0 until jaLevel2.length())
					{
						val cell = if (jaLevel2.isNull(index2)) "null"
						else
						{
							val cellObj = jaLevel2.opt(index2)
							if (cellObj is Double)
							{
								cellObj.toBigDecimal().toPlainString()
							}
							else
							{
								cellObj.toString()
							}
						}
						newRow.add(cell)
					}
					aRows.add(newRow)
				}
			}
			//endregion
			//region columns
			it.optJSONArray("columns")?.let {
				jaColumns ->
				aColumn.clear()
				for (index in 0 until jaColumns.length())
				{
					jaColumns.optJSONObject(index)?.let {
						joColumn ->
						val isGroupable = joColumn.optBoolean("groupable", false)
						val type = joColumn.optString("type", "")
						val name = joColumn.optString("name", "")
						val displayName = joColumn.optString("display_name", "")
						val isVisible = joColumn.optBoolean("is_visible", true)

						val typeColumn = enumValueOfOrNull(
							type
						) ?: run { TypeDataQuery.UNKNOWN }

						val column = ColumnQuery(isGroupable, typeColumn, name, displayName, isVisible)
						aColumn.add(column)
					}
				}
			}
			//endregion
			buildContent()
		}
	}

	private fun buildContent()
	{
		var needDoAsync = false
		when
		{
			message == "No Data Found" ->
			{
				contentHTML = message
			}
			aRows.size == 0 || isSimpleText || displayType == "suggestion" ->
			{
				aColumn.firstOrNull()?.let { column ->
					contentHTML = simpleText.formatWithColumn(column)
				}
			}
			else -> needDoAsync = true
		}

		Executor({
			isLoadingHTML = true
			if (needDoAsync)
			{
				//region generate contentHTML
				val rulesHTML = RulesHtml(aColumn, CountColumn(), aRows.size)
				supportCase = rulesHTML.getSupportCharts()

				val pData = HtmlBuilder.build(this)
				val dataForWebView = pData.first
				val dataD3 = pData.second
				if (configActions == 0)
				{
					displayType = "data"
				}
				if (displayType != "data")
				{
					dataForWebView.type = displayType
					dataD3.type = displayType
				}
				if (reloadTable)
				{
					reloadTable = false
					dataForWebView.updateTable = true
					dataD3.updateTable = true
				}

				when(aColumn.size)
				{
					2, 3 ->
					{
						dataForWebView.xAxis = aColumn.getOrNull(
							aIndex[0])?.displayName ?: ""
						dataD3.xAxis = aColumn.getOrNull(
							aIndex[0])?.displayName ?: ""

						if (aColumn.size > 2)
						{
							val aBase = (0..1).toMutableList()
							var indexCount = -1
							for (index in aColumn.indices)
							{
								if (aColumn[index].type.isNumber())
								{
									indexCount = index
								}
							}
							if (indexCount != -1)
								aBase.remove(indexCount)
							aBase.remove(aIndex[0])
							dataD3.yAxis = aColumn.getOrNull(aBase[0])?.displayName ?: ""
							dataD3.middleAxis = aColumn.getOrNull(indexCount)?.displayName ?: ""
						}
						else
						{
							dataForWebView.yAxis = aColumn.getOrNull(
								aIndex[1])?.displayName ?: ""
							dataD3.yAxis = dataForWebView.yAxis
							dataD3.middleAxis = aColumn.getOrNull(
								aIndex[1])?.displayName ?: ""
						}
					}
					else ->
					{
						if (aIndex.isNotEmpty())
						{
							dataForWebView.xAxis = aColumn.getOrNull(aIndex[0])?.displayName ?: ""
							dataD3.xAxis = aColumn.getOrNull(aIndex[0])?.displayName ?: ""
							dataForWebView.yAxis = aColumn.getOrNull(aIndex[1])?.displayName ?: ""
							dataD3.yAxis = "Amount"
						}
					}
				}
				dataForWebView.isColumn = if (configActions == 0) false else isGroupable
				dataD3.isColumn = if (configActions == 0) false else isGroupable
				dataForWebView.isDashboard = isDashboard
//				contentHTML = DashboardMaker.getHTML(dataForWebView)
				contentHTML = D3OnHtml.getHtmlTest(dataD3)
				rowsTable = dataForWebView.rowsTable
				rowsPivot = dataForWebView.rowsPivot
			}
		},{
			viewDrillDown?.loadDrillDown(this)

			viewPresenter?.let {
				it.isLoading(false)
				it.addNewChat(typeView, this)
			} ?: run {
				isLoadingHTML = false
				showData()
			}
		}).execute()
	}

	fun resetData()
	{
		contentHTML = ""
		viewPresenter = null
		buildContent()
	}

	fun checkData(view: HolderContract)
	{
		this.view = view
		if (!isLoadingHTML)
		{
			showData()
		}
	}

	private fun showData()
	{
		if (contentHTML.isNotEmpty())
		{
			view?.onBind(this)
		}
	}

	fun addIndices(index1: Int, index2: Int)
	{
		if (index1 != -1 && index2 != -1)
		{
			aIndex.add(index1)
			aIndex.add(index2)
		}
	}
}