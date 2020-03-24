package chata.can.chata_ai.activity.chat

import android.content.Context
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.tool.Network
import chata.can.chata_ai.request.query.QueryRequest
import org.json.JSONArray
import org.json.JSONObject

class ChatServicePresenter(
	private val context: Context,
	private var view: ChatContract?) : StatusResponse
{
	private var lastQuery = ""
	private val interactor = DataChatInteractor()

	fun getAutocomplete(suggestionQuery: String)
	{
		if (Network.checkInternetConnection(context))
		{
			lastQuery = suggestionQuery
			interactor.getAutocomplete(lastQuery, this)
		}
	}

	fun getSafety(query: String)
	{
		QueryRequest.callSafetyNet(query, this)
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		if (jsonObject != null)
		{

		}
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			when
			{
				jsonObject.has("matches") ->
				{
					jsonObject.optJSONArray("matches")?.let {
						val aData = ArrayList<String>()
						for (index in 0 until it.length())
						{
							aData.add(it.optString(index))
						}
						view?.setDataAutocomplete(aData)
					}
				}
				jsonObject.has("full_suggestion") ->
				{
					jsonObject.optJSONArray("full_suggestion")?.let {
						if (it.length() == 0)
						{
							val query = jsonObject.optString("query") ?: ""
							hashMapOf<String, Any>("query" to query)
							QueryRequest.callQuery(query, this)
						}
					}
				}
				jsonObject.has("reference_id") ->
				{
					view?.addNewChat(QueryBase(jsonObject))
				}
				else ->
				{

				}
			}
		}

		if (jsonArray != null)
		{

		}
	}

	fun onDestroy()
	{
		view = null
	}
}