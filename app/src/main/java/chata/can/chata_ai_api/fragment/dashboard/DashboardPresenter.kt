package chata.can.chata_ai_api.fragment.dashboard

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.model.dashboard.DashboardSingle
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDashboard.aDashboardModel
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
	private val model = SinglentonDashboard.mModel

	override fun onFailure(jsonObject: JSONObject?)
	{
		if (jsonObject != null)
		{
			when(jsonObject.optString("nameService") ?: "")
			{
				"getDashboardQueries" ->
				{
//					val response = jsonObject.optString("RESPONSE") ?: ""
//					try {
//						val query = jsonObject.optString("query") ?: ""
//						val index = model.indexOfFirst { it.query == query }
//						val queryBase = QueryBase(JSONObject(response))
//						queryBase.isDashboard = true
//						model[index]?.let { it.queryBase = queryBase }
//						if (index != -1)
//						{
//							view.notifyQueryAtIndex(index)
//						}
//					}
//					catch (e: Exception){ }
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
					val queryBase = QueryBase(jsonObject)
					queryBase.isDashboard = true
					val query = jsonObject.optString("query") ?: ""
					val title = jsonObject.optString("title") ?: ""
					val index = model.indexOfFirst { it.query == query && it.title == title }

					val currentItem = model[index]
					currentItem?.let {
						it.queryBase = queryBase
						if (it.splitView)
						{
							it.queryBase?.run {
								it.querySecondary = this.copy()
								it.querySecondary?.displayType = it.secondDisplayType
							}
						}
					}

					queryBase.typeView = when(queryBase.displayType)
					{
						dataKey ->
						{
							val numColumns = queryBase.numColumns
							when
							{
								numColumns == 1 -> {
									if( queryBase.hasHash)
										TypeChatView.HELP_VIEW
									else
										TypeChatView.LEFT_VIEW
								}
								numColumns > 1 ->
								{
									queryBase.displayType = currentItem?.displayType ?: "table"
									TypeChatView.WEB_VIEW
								}
								else -> TypeChatView.LEFT_VIEW
							}
						}
						else -> TypeChatView.LEFT_VIEW
					}

					view.notifyQueryAtIndex(index)
				}
				else ->
				{
					jsonObject.optJSONArray("array")?.let {
						jsonArray1 ->
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
												val secondDisplayType = optString("secondDisplayType", "")
												val static = optBoolean("static", false)
												val title = optString("title", "")
												val w = optInt("w", -1)
												val axisX = optInt("x", -1)
												val axisY = optInt("y", -1)

												aKeys.add(Pair(axisY, axisX))
												val dashboard = Dashboard(displayType, h, i, isNewTile, key, maxH, minH, minW, moved, query, splitView, static, title, w, axisX, axisY)
												dashboard.secondDisplayType = secondDisplayType
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
								aDashboardModel.add(
									DashboardSingle(
										idDashboard,
										nameDashboard,
										mModel)
								)
							}
						}
						view.setDashboards()
					}
				}
			}
		}
	}

	fun resetDashboards(isWaiting: Boolean)
	{
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
		for (index in 0 until model.countData())
		{
			model[index]?.let { dashboard ->
				dashboard.isWaitingData = true
				view.notifyQueryAtIndex(index)

				val query = dashboard.query
				val title = dashboard.title
				val mInfoHolder = hashMapOf<String, Any>(
					"query" to query,
					"title" to title,
					"nameService" to "getDashboardQueries")
				QueryRequest.callQuery(query, this, "dashboards", mInfoHolder)
			}
		}
	}
}