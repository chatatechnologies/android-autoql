package chata.can.chata_ai_api.fragment.main

import chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.QueryBuilderData
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai_api.R
import chata.can.request_native.StatusResponse
import org.json.JSONArray
import org.json.JSONObject

class MainServicePresenter(private val view: MainContract): StatusResponse
{
	fun createAuthenticate()
	{
		with(AutoQLData)
		{
			Authentication.callLogin(username, password, this@MainServicePresenter)
		}
	}

	fun createJWT()
	{
		Authentication.callJWL(
			AutoQLData.token,
			AutoQLData.userID,
			AutoQLData.projectId,
			this)
	}

	fun callRelated()
	{
		Authentication.callRelatedQuery(this)
	}

	fun callTopics()
	{
		Authentication.callTopics(this)
	}

	override fun onFailureResponse(jsonObject: JSONObject)
	{
		with(view)
		{
			isEnableLogin(true)
			//showError(errorCode, errorService)
			showAlert("Invalid Credentials", R.drawable.ic_error)
		}
	}

	override fun onSuccessResponse(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			when(jsonObject.optString("nameService"))
			{
				"callLogin" ->
				{
					val token = jsonObject.optString("RESPONSE")
					AutoQLData.token = token
					view.callJWt()
				}
				"callJWL" ->
				{
					val jwt = jsonObject.optString("RESPONSE") ?: ""
					AutoQLData.JWT = jwt
					view.callRelated()
					view.initPollService()
				}
				"callRelatedQuery" ->
				{
					with(view)
					{
						savePersistentData()
						callTopics()
					}
				}
				"callTopics" ->
				{
					val response = jsonObject.optString("RESPONSE")
					val joResponse = JSONObject(response)
					joResponse.optJSONArray("items")?.let { jaItems ->
						val aMainData = QueryBuilderData.aMainData
						aMainData.clear()
						val mMainQuery = QueryBuilderData.mMainQuery
						mMainQuery.clear()

						for (index in 0 until jaItems.length())
						{
							val json = jaItems.optJSONObject(index)
							val topic = json.optString("topic", "")
							aMainData.add(topic)

							json.optJSONArray("queries")?.let { jaQueries ->
								val aData = ArrayList<String>()
								for (index2 in 0 until jaQueries.length())
								{
									val query = jaQueries.optString(index2) ?: ""
									aData.add(query)
								}
								aData.add("💡See more...")
								mMainQuery[topic] = aData
							}
						}
					}
					AutoQLData.wasLoginIn = true
					view.run {
						changeAuthenticate(true)
						changeStateAuthenticate()
						isEnableLogin(true)
						showAlert("Login Successful", R.drawable.ic_done)
					}
				}
			}
		}
	}
}