package chata.can.chata_ai.activity.chat

import android.content.Context
import chata.can.chata_ai.pojo.chat.*
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.tool.Network
import chata.can.chata_ai.request.query.QueryRequest
import org.json.JSONArray
import org.json.JSONObject

class ChatServicePresenter(
	private val context: Context,
	private var view: ChatContract.View?) : StatusResponse
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
							val mInfoHolder = hashMapOf<String, Any>("query" to query)
							QueryRequest.callQuery(query, this, mInfoHolder)
						}
						else
						{
							val simpleQuery = FullSuggestionQuery(jsonObject)
							view?.addNewChat(TypeChatView.FULL_SUGGESTION_VIEW, simpleQuery)
						}
					}
				}
				jsonObject.has("reference_id") ->
				{
					val queryBase = QueryBase(jsonObject)
					val typeView = when(queryBase.displayType)
					{
						"suggestion" ->
						{
							val query = jsonObject.optString("query")
							queryBase.message = query
							TypeChatView.SUGGESTION_VIEW
						}
						"data" ->
						{
							val numColumns = queryBase.numColumns
							when
							{
								numColumns == 1 -> TypeChatView.LEFT_VIEW
								numColumns > 1 -> TypeChatView.WEB_VIEW
								else -> TypeChatView.LEFT_VIEW
							}
						}
						else -> TypeChatView.LEFT_VIEW
					}
					view?.addNewChat(typeView, queryBase)
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