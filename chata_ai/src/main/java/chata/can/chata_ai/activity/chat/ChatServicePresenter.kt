package chata.can.chata_ai.activity.chat

import android.content.Context
import chata.can.chata_ai.pojo.SinglentonDrawer
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
		interactor.callSafetyNet(query, this)
	}

	fun getQuery(query: String)
	{
		val mInfoHolder = hashMapOf<String, Any>("query" to query)
		QueryRequest.callQuery(query, this, mInfoHolder)
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
				jsonObject.has("nameService") ->
				{
					when(jsonObject.optString("nameService"))
					{
						"demoAutocomplete" ->
						{
							makeMatches(jsonObject)
						}
						"autocomplete" ->
						{
							jsonObject.getJSONData()?.let {
								makeMatches(it)
							}
						}
						"safetynet" ->
						{
							makeSuggestion(jsonObject, "full_suggestion", "query")
						}
						"validate" ->
						{
							jsonObject.getJSONData()?.let {
								data ->
								makeSuggestion(data, "replacements", "text")
							}
						}
						else ->
						{

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
							if (SinglentonDrawer.mIsEnableSuggestion)
							{
								val query = jsonObject.optString("query")
								queryBase.message = query
								TypeChatView.SUGGESTION_VIEW
							}
							else
							{
								queryBase.message = "${queryBase.displayType} not supported"
								TypeChatView.LEFT_VIEW
							}
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

	private fun JSONObject.getJSONData() = optJSONObject("data")

	private fun makeMatches(json: JSONObject)
	{
		if (json.has("matches"))
		{
			json.optJSONArray("matches")?.let {
				val aData = ArrayList<String>()
				for (index in 0 until it.length())
				{
					aData.add(it.optString(index))
				}
				view?.setDataAutocomplete(aData)
			}
		}
	}

	/**
	 * @param keySuggestion can be "full_suggestion" or "replacements"
	 * @param keyQuery can be "query" or "text"
	 */
	private fun makeSuggestion(json: JSONObject, keySuggestion: String, keyQuery: String)
	{
		if (json.has(keySuggestion))
		{
			json.optJSONArray(keySuggestion)?.let {
				if (it.length() == 0)
				{
					val query = json.optString(keyQuery) ?: ""
					val mInfoHolder = hashMapOf<String, Any>("query" to query)
					QueryRequest.callQuery(query, this, mInfoHolder)
				}
				else
				{
					val simpleQuery = FullSuggestionQuery(json)
					view?.addNewChat(TypeChatView.FULL_SUGGESTION_VIEW, simpleQuery)
				}
			}
		}
	}

	fun onDestroy()
	{
		view = null
	}
}