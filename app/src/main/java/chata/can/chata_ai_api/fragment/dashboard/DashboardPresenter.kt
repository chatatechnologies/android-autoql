package chata.can.chata_ai_api.fragment.dashboard

import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.query.QueryRequest
import chata.can.chata_ai.request.dashboard.Dashboard as RequestDashboard
import org.json.JSONArray
import org.json.JSONObject

class DashboardPresenter(
	private val view: DashboardContract): StatusResponse
{
	private var countExecutedQueries = 0
	private val model = SinglentonDashboard.mModel

	override fun onFailure(jsonObject: JSONObject?) {}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			when(jsonObject.optString("nameService") ?: "")
			{
				"getDashboardQueries" ->
				{
					val queryBase = QueryBase(jsonObject)
					countExecutedQueries++
				}
				else ->
				{
					jsonObject.optJSONArray("array")?.let {
						jsonArray1 ->
						jsonArray1.optJSONObject(0)?.let {
							firstJSON ->
							firstJSON.optJSONArray("data")?.let {
								jaData ->
								for (index in 0 until jaData.length())
								{
									jaData.optJSONObject(index)?.let {
										json ->
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
											val x = optInt("x", -1)
											val y = optInt("y", -1)

											val dashboard = Dashboard(displayType, h, i, isNewTile, key, maxH, minH, minW, moved, query, splitView, static, title, w, x, y)
											model.add(dashboard)
										}
									}
								}
								view.setDashboards()
							}
						}
					}
				}
			}

			if (countExecutedQueries == model.countData())
			{
				view.reloadQueries()
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
				val query = dashboard.query
				val mInfoHolder = hashMapOf<String, Any>(
					"query" to query,
					"nameService" to "getDashboardQueries")
				QueryRequest.callQuery(query, this, mInfoHolder)
			}
		}
	}
}