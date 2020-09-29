package chata.can.chata_ai_api.fragment.dashboard

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDashboard.getCurrentDashboard
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.dataKey
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.query.QueryRequest
import chata.can.chata_ai.request.dashboard.Dashboard as RequestDashboard
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap

class DashboardPresenter(
	private val view: DashboardContract): StatusResponse
{
	private var mModel: BaseModelList<Dashboard> ?= null

	override fun onFailure(jsonObject: JSONObject?)
	{
		if (jsonObject != null)
		{
			when(jsonObject.optString("nameService") ?: "")
			{
				"getDashboardQueries" ->
				{
					val response = jsonObject.optString("RESPONSE") ?: ""
					val joCurrent = JSONObject(response)

					try {
						val query = jsonObject.optString("query") ?: ""
						val title = jsonObject.optString("title") ?: ""
						val key = jsonObject.optString("key") ?: ""
						val isSecondaryQuery = jsonObject.optBoolean("isSecondaryQuery", false)
						//region scope for call related queries
						val code = jsonObject.optInt("CODE")
						val referenceId = joCurrent.optString("reference_id") ?: ""
						val isSuggestion = referenceId == "1.1.430" || referenceId == "1.1.431"
						if (code == 400 && isSuggestion)
						{
							val mData = hashMapOf<String, Any>(
								"query" to query,
								"title" to title,
								"key" to key,
								"isSecondaryQuery" to isSecondaryQuery)
							val words = query.split(" ")
								.joinTo(StringBuilder(), separator = ",").toString()
							QueryRequest.callRelatedQueries(words, this, mData)
						}
						//endregion
						else
						{
							mModel?.run {
								val index = this.indexOfFirst { it.key == key }
								if (index != -1)
								{
									this[index]?.let { dashboard ->
										val queryBase = QueryBase(joCurrent).apply {
											isDashboard = true
											typeView = TypeChatView.SUPPORT
										}
										if (isSecondaryQuery)
											dashboard.queryBase2 = queryBase
										else
											dashboard.queryBase = queryBase
										notifyQueryByIndex(index)
									}
								}
							}
						}

					}
					catch (e: Exception){ }
				}
			}
		}
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			when(jsonObject.optString("nameService") ?: "")
			{
				"callRelatedQueries" ->
				{
					val key = jsonObject.optString("key") ?: ""
					val isSecondaryQuery = jsonObject.optBoolean("isSecondaryQuery", false)

					//region build json for suggestion
					jsonObject.optJSONObject("data")?.let { joData ->
						joData.optJSONArray("items")?.let { jaItems ->
							val json = JSONObject().put("query", "")
							val queryBase = QueryBase(json).apply {
								isDashboard = true
								for (index in 0 until jaItems.length())
								{
									val item = jaItems.opt(index).toString()
									aRows.add(arrayListOf(item))
								}
							}
							queryBase.typeView = TypeChatView.SUGGESTION_VIEW

							val model = getCurrentDashboard()
							val index = model.indexOfFirst { it.key == key }

							if (index != -1)
							{
								model[index]?.let { dashboard ->
									if (isSecondaryQuery)
										dashboard.queryBase2 = queryBase
									else
										dashboard.queryBase = queryBase

									notifyQueryByIndex(index)
								}
							}
						}
					}
					//endregion
				}
				"getDashboardQueries" ->
				{
					val key = jsonObject.optString("key") ?: ""
					val isSecondaryQuery = jsonObject.optBoolean("isSecondaryQuery", false)
					mModel?.run {
						val index = indexOfFirst { it.key == key }
						if (index != -1)
						{
							this[index]?.let { dashboard ->
								val queryBase = QueryBase(jsonObject).apply {
									isDashboard = true
									configQueryBase(dashboard, this, isSecondaryQuery)
								}
								if (isSecondaryQuery)
									dashboard.queryBase2 = queryBase
								else
								{
									dashboard.queryBase = queryBase
									if (dashboard.secondQuery.isEmpty() && dashboard.splitView)
									{
										dashboard.queryBase2 = QueryBase(jsonObject).apply {
											isDashboard = true
											this.isSecondaryQuery = true
											configQueryBase(dashboard, this, true)
										}
									}
								}
								notifyQueryByIndex(index)
							}
						}
					}
				}
				"getDashboard" ->
				{
					setDashboard(jsonObject)
				}
				else ->
				{

				}
			}
		}
	}

	/**
	 * Update for each update on current dashboard
	 */
	fun updateModel()
	{
		mModel = getCurrentDashboard()
	}

	private fun setDashboard(jsonObject: JSONObject)
	{
		jsonObject.optJSONArray("items")?.let { jsonArray1 ->
			for (index in 0 until jsonArray1.length())
			{
				val joDashboard = jsonArray1.optJSONObject(index) ?: JSONObject()
				val idDashboard = joDashboard.optInt("id", 0)
				val nameDashboard = joDashboard.optString("name", "")

				val mModel = BaseModelList<Dashboard>()

				if (idDashboard != 0 && joDashboard.length() > 0)
				{
					joDashboard.optJSONArray("data")?.let { jaData ->
						val aKeys = ArrayList<Pair<Int, Int>>()
						val mPartial = ConcurrentHashMap<String, Dashboard>()

						for (index2 in 0 until jaData.length())
						{
							jaData.optJSONObject(index2)?.let { json ->
								with(json)
								{
									val displayType = optString("displayType", "")
									val h = optInt("h", -1)
									val i = optString("i", "")
									val isNewTile = optBoolean("isNewTile", false)
									val key = optString("key", "")
									val maxH = optInt("maxH", -1)
									val minH = optInt("minH", -1)
									val minW = optInt("minW", -1)
									val moved = optBoolean("moved", false)
									val query = optString("query", "")
									val splitView = optBoolean("splitView", false)
									val static = optBoolean("static", false)
									val title = optString("title", "")
									val w = optInt("w", -1)
									val axisX = optInt("x", -1)
									val axisY = optInt("y", -1)

									aKeys.add(Pair(axisY, axisX))
									val dashboard = Dashboard(displayType, h, i, isNewTile, key, maxH, minH, minW, moved, query, splitView, static, title, w, axisX, axisY)
									if (splitView)
									{
										val secondDisplayType = optString("secondDisplayType", "")
										val secondQuery = optString("secondQuery", "")
										dashboard.secondDisplayType = secondDisplayType
										dashboard.secondQuery = secondQuery
									}
									optJSONArray("queryValidationSelections")?.let {
										it.optJSONObject(0)?.let { qvs ->
											dashboard.value = qvs.optString("text")
											dashboard.valueLabel = qvs.optString("value_label")
										}
									}
									mPartial["${axisY}_$axisX"] = dashboard
								}
							}
						}
						//region order dashboard
						aKeys.sortedWith(compareBy ({ it.first }, { it.second })).let {
							for (key in it)
							{
								val newKey = "${key.first}_${key.second}"
								mPartial[newKey]?.let { dashboard ->
									mModel.add(dashboard)
								}
							}
						}
						//endregion
					}

					SinglentonDashboard.add(idDashboard, nameDashboard, mModel)
				}
			}
			SinglentonDashboard.sortData()
			view.setDashboards()
		}
	}

	private fun configQueryBase(dashboard: Dashboard, queryBase: QueryBase, isSplitView: Boolean)
	{
		queryBase.typeView = when(queryBase.displayType)
		{
			dataKey ->
			{
				val numColumns = queryBase.numColumns
				when
				{
					queryBase.message == "No Data Found" -> TypeChatView.LEFT_VIEW
					numColumns == 1 ->
					{
						if(queryBase.hasHash)
							TypeChatView.HELP_VIEW
						else
							TypeChatView.LEFT_VIEW
					}
					numColumns > 1 ->
					{
						queryBase.displayType =
							if (isSplitView) dashboard.secondDisplayType else dashboard.displayType
						if (queryBase.displayType.isEmpty())
						{
							queryBase.displayType = "bar"
						}
						TypeChatView.WEB_VIEW
					}
					else -> TypeChatView.LEFT_VIEW
				}
			}
			else -> TypeChatView.LEFT_VIEW
		}
	}

	fun resetDashboards(isWaiting: Boolean)
	{
		mModel?.run {
			for (index in 0 until this.countData())
			{
				this[index]?.let {
					it.isWaitingData = isWaiting
					it.queryBase = null
				}
			}
		}

	}

	fun getDashboards()
	{
		RequestDashboard.getDashboard(this)
	}

	fun getDashboardQueries()
	{
		mModel?.run {
			for (index in 0 until this.countData())
			{
				this[index]?.let { dashboard ->
					dashboard.isWaitingData = true
					dashboard.queryBase = null
					notifyQueryByIndex(index)
					callQuery(dashboard)
				}
			}
		}
	}

	private fun getDataQuery(dashboard: Dashboard, wantSplitView: Boolean): HashMap<String, Any>
	{
		with(dashboard)
		{
			val mInfoHolder = hashMapOf<String, Any>(
				"key" to key,
				"isSplitView" to splitView,
				"title" to title,
				"nameService" to "getDashboardQueries")

			if (wantSplitView && splitView)
			{
				val secondQuery = if (secondQuery.isEmpty()) query else secondQuery
				mInfoHolder["isSecondaryQuery"] = true
				mInfoHolder["primaryQuery"] = query
				mInfoHolder["query"] = secondQuery
			}
			else
			{
				mInfoHolder["query"] = query
				if (value.isNotEmpty() && valueLabel.isNotEmpty())
				{
					val mUserSelection = hashMapOf(
						"value" to value,
						"value_label" to valueLabel,
						"canonical" to "ORIGINAL_TEXT")
					mInfoHolder["user_selection"] = mUserSelection
				}
			}
			return mInfoHolder
		}
	}

	fun notifyQueryByIndex(index: Int)
	{
		view.notifyQueryAtIndex(index)
	}

	fun callQuery(dashboard: Dashboard)
	{
		val query = dashboard.query
		if (query.isNotEmpty())
		{
			val mInfoHolder = getDataQuery(dashboard,false)
			QueryRequest.callQuery(query, this, "dashboards", mInfoHolder)
		}
		val secondQuery = dashboard.secondQuery
		if (secondQuery.isNotEmpty())
		{
			dashboard.isWaitingData2 = true
			val mInfoHolder = getDataQuery(dashboard,true)
			QueryRequest.callQuery(secondQuery, this, "dashboards", mInfoHolder)
		}
	}
}