package chata.can.chata_ai_api.fragment.main

import chata.can.chata_ai.pojo.DataMessenger
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication
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

	override fun onFailure(jsonObject: JSONObject?)
	{
		if (jsonObject != null)
		{
			val errorCode = jsonObject.optString("CODE", "")
			val errorService = jsonObject.optString("nameService", "")
			view.showError(errorCode, errorService)
		}
	}

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
						isEnableLogin(true)
						savePersistentData()
					}
				}
			}
		}
	}
}