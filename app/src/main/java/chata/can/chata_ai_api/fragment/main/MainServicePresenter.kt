package chata.can.chata_ai_api.fragment.main

import chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.QueryBuilderData
import chata.can.chata_ai.view.bubbleHandle.DataMessengerRoot
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai_api.R
import org.json.JSONArray
import org.json.JSONObject

class MainServicePresenter(private val view: MainContract): StatusResponse
{
	fun createAuthenticate()
	{
		with(DataMessengerRoot)
		{
			Authentication.callLogin(username, password, this@MainServicePresenter)
		}
	}

	fun createJWT()
	{
		Authentication.callJWL(
			DataMessengerRoot.token,
			DataMessengerRoot.userID,
			DataMessengerRoot.projectId,
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

	override fun onFailure(jsonObject: JSONObject?)
	{
		if (jsonObject != null)
		{
//			val errorCode = jsonObject.optString("CODE", "")
//			val errorService = jsonObject.optString("nameService", "")
			with(view)
			{
				isEnableLogin(true)
				//showError(errorCode, errorService)
				showAlert("Invalid Credentials", R.drawable.ic_error)
			}
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
					DataMessengerRoot.token = token
					view.callJWt()
				}
				"callJWL" ->
				{
					val jwt = jsonObject.optString("RESPONSE") ?: ""
					DataMessengerRoot.JWT = jwt
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
					jsonObject.optJSONArray("items")?.let { jaItems ->
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