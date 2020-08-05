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
						val index = model.indexOfFirst { it.query == query }
						val queryBase = QueryBase(JSONObject(response))
						queryBase.isDashboard = true
						model[index]?.let { it.queryBase = queryBase }
						if (index != -1)
						{
							view.notifyQueryAtIndex(index)
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
				"getDashboardQueries" ->
				{
					val model = getCurrentDashboard()

					val queryBase = QueryBase(jsonObject)
					queryBase.isDashboard = true
					val query = jsonObject.optString("query") ?: ""
					val title = jsonObject.optString("title") ?: ""
					val isSecond = jsonObject.optBoolean("isSecond", false)

					if (isSecond)
					{
						//search secondQuery
						val secondIndex = model.indexOfFirst { it.secondQuery == query && it.title == title }
						if (secondIndex != -1)
						{
							model[secondIndex]?.let { dashboard ->
								dashboard.queryBase2 = queryBase
								configQueryBase(dashboard, queryBase)
							}
							view.notifyQueryAtIndex(secondIndex)
						}
					}
					else
					{
						val index = model.indexOfFirst { it.query == query && it.title == title }
						if (index != -1)
						{
							model[index]?.let { dashboard ->
								dashboard.queryBase = queryBase
								configQueryBase(dashboard, queryBase)
							}
							view.notifyQueryAtIndex(index)
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
				view.notifyQueryAtIndex(index)

				val query = dashboard.query
				if (query.isNotEmpty())
				{
					val mInfoHolder = hashMapOf<String, Any>(
						"query" to query,
						"title" to dashboard.title,
						"nameService" to "getDashboardQueries")
					QueryRequest.callQuery(query, this, "dashboards", mInfoHolder)
				}
				val secondQuery = dashboard.secondQuery
				if (secondQuery.isNotEmpty())
				{
					val mInfoHolder = hashMapOf(
						"isSecond" to true,
						"query" to secondQuery,
						"title" to dashboard.title,
						"nameService" to "getDashboardQueries")
					QueryRequest.callQuery(query, this, "dashboards", mInfoHolder)
				}
			}
		}
	}
}