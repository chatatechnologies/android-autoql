package chata.can.chata_ai_api.fragment.main

import chata.can.chata_ai.pojo.DataMessenger
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.request.dashboard.Dashboard as RequestDashboard
import org.json.JSONArray
import org.json.JSONObject

class MainServicePresenter(private val view: MainContract): StatusResponse
{
	fun createAuthenticate()
	{
		with(DataMessenger)
		{
			Authentication.callLogin(username, password, this@MainServicePresenter)
		}
	}

	fun createJWT(userId: String, projectId: String)
	{
		Authentication.callJWL(DataMessenger.token, userId, projectId, this)
	}

	fun getDashboards()
	{
		RequestDashboard.getDashboard(this)
	}

	override fun onFailure(jsonObject: JSONObject?) {}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			when(jsonObject.optString("nameService"))
			{
				"callLogin" ->
				{
					val token = jsonObject.optString("RESPONSE")
					DataMessenger.token = token
					view.callJWt()
				}
				"callJWL" ->
				{
					val jwt = jsonObject.optString("RESPONSE") ?: ""
					DataMessenger.JWT = jwt
					with(view)
					{
						changeAuthenticate(true)
						changeStateAuthenticate()
						getDashboards()
					}
				}
			}
		}

		if (jsonArray != null)
		{
			jsonArray.optJSONObject(0)?.let {
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
								SinglentonDashboard.mModel.add(dashboard)
							}
						}
					}
				}
			}
		}
	}
}