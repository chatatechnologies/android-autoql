package chata.can.chata_ai.pojo.chat

import chata.can.chata_ai.DoAsync
import chata.can.chata_ai.activity.chat.presenter.PresenterContract
import chata.can.chata_ai.dialog.drillDown.DrillDownContract
import chata.can.chata_ai.holder.HolderContract
import chata.can.chata_ai.pojo.webView.HtmlBuilder
import chata.can.chata_ai.pojo.webView.HtmlMarked
import chata.can.chata_ai.extension.enumValueOfOrNull
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.pojo.dataKey
import chata.can.chata_ai.pojo.messageKey
//import chata.can.chata_ai.pojo.referenceIdKey
import org.json.JSONObject

class QueryBase(json: JSONObject): SimpleQuery(json)
{
	var hasDrillDown = true
	//private val referenceId = json.optString(referenceIdKey) ?: ""
	private val joData = json.optJSONObject(dataKey)
	var message = json.optString(messageKey) ?: ""

	private var sql: String = ""
	var queryId = ""
	var displayType = ""
	private var interpretation = ""

	val aRows = ArrayList<ArrayList<String>>()
	var mIndexColumn = linkedMapOf<Int, Int>()
	var aColumn = ArrayList<ColumnQuery>()

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
	var contentHTML = ""
	var rowsTable = 0
	var rowsPivot = 0

	private var view: HolderContract? = null
	var viewPresenter: PresenterContract ?= null
	var viewDrillDown: DrillDownContract ?= null
	var isLoadingHTML = false

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
						val cell = jaLevel2.optString(index2, "")
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
						val type = joColumn.optString("type")
						val name = joColumn.optString("name")
						val displayName = joColumn.optString("display_name", "")
						val isActive = joColumn.optBoolean("active", false)

						val typeColumn = enumValueOfOrNull<TypeDataQuery>(
							type
						) ?: run { TypeDataQuery.UNKNOWN }

						val column = ColumnQuery(isGroupable, typeColumn, name, displayName, isActive)
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
						} ?: run { "" }
					}
					else ->
					{
						val dataForWebView = HtmlBuilder.build(this)
						if (displayType != "data")
						{
							dataForWebView.type = displayType
						}
						when(aColumn.size)
						{
							2 ->
							{
								val column0 = aColumn[0]
								dataForWebView.xAxis = column0.displayName
								val column1 = aColumn[1]
								dataForWebView.yAxis = column1.displayName
							}
							3 -> {}
							else -> {}
						}

						contentHTML = HtmlMarked.getHTML(dataForWebView)
						rowsTable = dataForWebView.rowsTable
						rowsPivot = dataForWebView.rowsPivot
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
		view?.onBind(this)
	}
}