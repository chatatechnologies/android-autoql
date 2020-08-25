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
	override fun onFailure(jsonObject: JSONObject?)
	{
		if (jsonObject != null)
		{
			when(jsonObject.optString("nameService") ?: "")
			{
				"getDashboardQueries" ->
				{
					val model = getCurrentDashboard()
					val response = jsonObject.optString("RESPONSE") ?: ""
					try {
						val query = jsonObject.optString("query") ?: ""
						val title = jsonObject.optString("title") ?: ""
						val key = jsonObject.optString("key") ?: ""
						val isSecondaryQuery = jsonObject.optBoolean("isSecondaryQuery", false)

						if (isSecondaryQuery)
						{
							val primaryQuery = jsonObject.optString("primaryQuery") ?: ""
							//search secondQuery
							val secondIndex = model.indexOfFirst {
								it.secondQuery == query && it.query == primaryQuery && it.title == title && it.key == key
							}
							if (secondIndex != -1)
							{
								model[secondIndex]?.let { dashboard ->
									dashboard.jsonSecondary = JSONObject(response)
									if (checkQueriesDashboard(dashboard))
									{
										//region initQueryBase
										val secondQuery = initQueryEmpty(dashboard, response = response)
										//endregion
										dashboard.jsonPrimary?.let {
											//region initQueryBase
											initQueryEmpty(dashboard, it)
											//endregion
											//Init secondary query
											dashboard.queryBase?.splitQuery = secondQuery
											view.notifyQueryAtIndex(secondIndex)
										}
									}
								}
							}
						}
						else
						{
							val index = model.indexOfFirst {
								it.query == query && it.title == title && it.key == key
							}
							if (index != -1)
							{
								model[index]?.let { dashboard ->
									dashboard.jsonPrimary = JSONObject(response)
									if (dashboard.splitView)
									{
										if (checkQueriesDashboard(dashboard))
										{
											dashboard.jsonSecondary?.let {
												initQueryEmpty(dashboard, it)
											}
											view.notifyQueryAtIndex(index)
										}
									}
									else
									{
										val code = jsonObject.optInt("CODE")

										val json = JSONObject(response)
										if (code == 400)
										{
											val words = query.split(" ").joinTo(StringBuilder(), separator = ",").toString()
											QueryRequest.callRelatedQueries(words, this)
											//call again query on dashboard
//												"message"
//												"I want to make sure I understood your query. Did you mean:"
										}
										else
										{
											val queryBase = QueryBase(json)
											queryBase.isDashboard = true
											dashboard.queryBase = queryBase

											view.notifyQueryAtIndex(index)
										}
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
					jsonObject.toString()
				}
				"getDashboardQueries" ->
				{
					val model = getCurrentDashboard()
					val query = jsonObject.optString("query") ?: ""
					val title = jsonObject.optString("title") ?: ""
					val key = jsonObject.optString("key") ?: ""
					val isSecondaryQuery = jsonObject.optBoolean("isSecondaryQuery", false)

					if (isSecondaryQuery)
					{
						val primaryQuery = jsonObject.optString("primaryQuery") ?: ""
						//search secondQuery
						val secondIndex = model.indexOfFirst {
							it.secondQuery == query && it.query == primaryQuery && it.title == title && it.key == key
						}
						if (secondIndex != -1)
						{
							model[secondIndex]?.let { dashboard ->
								dashboard.jsonSecondary = jsonObject
								if (checkQueriesDashboard(dashboard))
								{
									val secondQuery = initSecondaryQuery(jsonObject, dashboard)
									dashboard.jsonPrimary?.let {
										dashboard.queryBase = initQueryBase(dashboard, it)
										//Init secondary query
										dashboard.queryBase?.splitQuery = secondQuery
									}
									//view.notifyQueryAtIndex(secondIndex)
								}
							}
						}
					}
					else
					{
						val index = model.indexOfFirst {
							it.query == query && it.title == title && it.key == key
						}
						if (index != -1)
						{
							model[index]?.let { dashboard ->
								dashboard.jsonPrimary = jsonObject
								if (dashboard.splitView)
								{
									if (checkQueriesDashboard(dashboard))
									{
										dashboard.jsonSecondary?.let {
											val secondQuery = initSecondaryQuery(it, dashboard)
											dashboard.queryBase = initQueryBase(dashboard, jsonObject)
											dashboard.queryBase?.splitQuery = secondQuery
										}
										//view.notifyQueryAtIndex(index)
									}
								}
								else
								{
									dashboard.queryBase = initQueryBase(dashboard, jsonObject)
									view.notifyQueryAtIndex(index)
								}
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

	private fun initQueryBase(dashboard: Dashboard, jsonObject: JSONObject): QueryBase
	{
		return QueryBase(jsonObject).apply {
			isDashboard = true
			configQueryBase(dashboard, this)
		}
	}

	private fun initSecondaryQuery(jsonObject: JSONObject, dashboard: Dashboard): QueryBase
	{
		return QueryBase(jsonObject).apply {
			displayType = dashboard.secondDisplayType
		}
	}

	private fun initQueryEmpty(
		dashboard: Dashboard,
		json: JSONObject = JSONObject(),
		response: String = ""): QueryBase
	{
		val finalJSON = if (response.isEmpty()) json else JSONObject(response)
		return QueryBase(finalJSON).apply {
			isDashboard = true
			dashboard.queryBase = this
		}
	}

	private fun checkQueriesDashboard(dashboard: Dashboard): Boolean
	{
		return dashboard.jsonPrimary != null && dashboard.jsonSecondary != null
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
									//model.add(dashboard)
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

	private fun configQueryBase(dashboard: Dashboard, queryBase: QueryBase)
	{
		queryBase.typeView = when(queryBase.displayType)
		{
			dataKey ->
			{
				val numColumns = queryBase.numColumns
				when
				{
					numColumns == 1 ->
					{
						if(queryBase.hasHash)
							TypeChatView.HELP_VIEW
						else
							TypeChatView.LEFT_VIEW
					}
					numColumns > 1 ->
					{
						queryBase.displayType = dashboard.displayType
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
		val model = getCurrentDashboard()

		for (index in 0 until model.countData())
		{
			model[index]?.let {
				it.isWaitingData = isWaiting
				it.queryBase = null
			}
		}
	}

	fun getDashboards()
	{
		RequestDashboard.getDashboard(this)
	}

	fun getDashboardQueries()
	{
		val model = getCurrentDashboard()

		for (index in 0 until model.countData())
		{
			model[index]?.let { dashboard ->
				dashboard.isWaitingData = true
				dashboard.queryBase = null
				view.notifyQueryAtIndex(index)

				val query = dashboard.query
				if (query.isNotEmpty())
				{
					val mInfoHolder = hashMapOf(
						"key" to dashboard.key,
						"isSplitView" to dashboard.splitView,
						"query" to query,
						"title" to dashboard.title,
						"nameService" to "getDashboardQueries")
					QueryRequest.callQuery(query, this, "dashboards", mInfoHolder)
				}
				val secondQuery = dashboard.secondQuery
				if (secondQuery.isNotEmpty())
				{
					val mInfoHolder = hashMapOf(
						"key" to dashboard.key,
						"isSplitView" to true,
						"isSecondaryQuery" to true,
						"primaryQuery" to query,
						"query" to secondQuery,
						"title" to dashboard.title,
						"nameService" to "getDashboardQueries")
					QueryRequest.callQuery(secondQuery, this, "dashboards", mInfoHolder)
				}
			}
		}
	}
}