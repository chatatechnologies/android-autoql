package chata.can.chata_ai.pojo.chat

import chata.can.chata_ai.DoAsync
import chata.can.chata_ai.fragment.dataMessenger.presenter.PresenterContract
import chata.can.chata_ai.dialog.DrillDownContract
import chata.can.chata_ai.holder.HolderContract
import chata.can.chata_ai.pojo.webView.HtmlBuilder
import chata.can.chata_ai.extension.enumValueOfOrNull
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.pojo.dataKey
import chata.can.chata_ai.pojo.messageKey
import chata.can.chata_ai.pojo.webView.DashboardMaker
import org.json.JSONObject

data class QueryBase(val json: JSONObject): SimpleQuery(json)
{
	var hasDrillDown = true
	var isDashboard = false
	//private val referenceId = json.optString(referenceIdKey) ?: ""no
	private val joData = json.optJSONObject(dataKey)
	var message = json.optString(messageKey) ?: ""

	private var sql: String = ""
	var queryId = ""
	var displayType = ""
	private var interpretation = ""

	val aRows = ArrayList<ArrayList<String>>()
	var mIndexColumn = linkedMapOf<Int, Int>()
	var aColumn = ArrayList<ColumnQuery>()

	private var isSplitView = json.optBoolean("isSplitView", false)
	//Define creation of html code
	private var isSecondaryQuery = json.optBoolean("isSecondaryQuery", false)
	var splitQuery: QueryBase ?= null

	fun isTypeColumn(type: TypeDataQuery): Boolean
	{
		for (column in aColumn)
		{
			if (type == column.type)
			{
				return true
			}
		}
		return false
	}

	val numColumns: Int
	get() {
		return aColumn.size
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
	lateinit var  aXAxis: ArrayList<String>
	lateinit var aXDrillDown: ArrayList<String>

	private var view: HolderContract? = null
	var viewPresenter: PresenterContract?= null
	var viewDrillDown: DrillDownContract?= null
	var isLoadingHTML = true

	init {
		joData?.let {
			sql = joData.optString("sql") ?: ""
			queryId = joData.optString("query_id") ?: ""
			displayType = joData.optString("display_type") ?: ""
			interpretation = joData.optString("interpretation") ?: ""

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
						val cell = if (jaLevel2.isNull(index2)) ""
						else jaLevel2.optString(index2, "")
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

						val typeColumn = enumValueOfOrNull<TypeDataQuery>(
							type
						) ?: run { TypeDataQuery.UNKNOWN }

						val column = ColumnQuery(isGroupable, typeColumn, name, displayName, isVisible)
						aColumn.add(column)
					}
				}
			}
			//endregion

			if (aColumn.size < 4)
			{
				for (index in aColumn.indices)
				{
					val column = aColumn[index]
					if (column.isGroupable)
					{
						mIndexColumn[mIndexColumn.size] = index
					}
				}

				//region swap 1 or two by groupable
				if (mIndexColumn[0] == 1)
				{
					mIndexColumn[1]?.let {
						mIndexColumn[1] = 0
					} ?: run { mIndexColumn[1] = 0}
				}

				while (mIndexColumn.size < aColumn.size)
				{
					val values = mIndexColumn.values
					for (index in 0 until aColumn.size)
					{
						if (index !in values)
						{
							mIndexColumn[mIndexColumn.size] = index
						}
					}
				}
			}
			else
			{
				for (index in aColumn.indices)
				{
					mIndexColumn[index] = index
				}
			}

			DoAsync({
				isLoadingHTML = true
				when
				{
					aRows.size == 0 || isSimpleText || displayType == "suggestion" ->
					{
						aColumn.firstOrNull()?.let {
							column ->
							contentHTML = simpleText.formatWithColumn(column)
						}
					}
					else ->
					{
						//region generate html CODE
						var otherPart = ""
						if (isSplitView)
						{
							if (!isSecondaryQuery && splitQuery != null)
							{
								splitQuery?.let { splitQuery ->
									otherPart = HtmlBuilder.getByParts(splitQuery)
								}
							}
						}

						//Only is execute when queryBase is primary
						if (!isSecondaryQuery)
						{
							val dataForWebView = HtmlBuilder.build(this)
							if (displayType != "data")
							{
								dataForWebView.type = displayType
							}
							if (otherPart.isNotEmpty())
							{
								dataForWebView.secondaryPart = otherPart
							}
							when(aColumn.size)
							{
								2, 3 ->
								{
									dataForWebView.xAxis = aColumn.getOrNull(0)?.displayName ?: ""
									dataForWebView.yAxis = aColumn.getOrNull(1)?.displayName ?: ""
								}
								else -> {}
							}
							contentHTML = DashboardMaker.getHTML(dataForWebView)
							rowsTable = dataForWebView.rowsTable
							rowsPivot = dataForWebView.rowsPivot
						}
						//endregion
					}
				}
			},{
				viewDrillDown?.loadDrillDown(this)

				viewPresenter?.let {
					viewPresenter?.isLoading(false)
					viewPresenter?.addNewChat(typeView, this)
				} ?: run {
					isLoadingHTML = false
					showData()
				}
			}).execute()
		}
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
}