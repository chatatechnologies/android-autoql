package chata.can.chata_ai.fragment.dataMessenger

import android.content.Context
import chata.can.chata_ai.activity.chat.presenter.PresenterContract
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.FullSuggestionQuery
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.SimpleQuery
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.dataKey
import chata.can.chata_ai.pojo.referenceIdKey
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.tool.Network
import chata.can.chata_ai.request.query.QueryRequest
import org.json.JSONArray
import org.json.JSONObject

class ChatServicePresenter(
	private val context: Context,
	private var view: ChatContract.View?) : StatusResponse, PresenterContract
{
	private var lastQuery = ""
	private val contract = DataChatContract()

	fun getAutocomplete(suggestionQuery: String)
	{
		if (Network.checkInternetConnection(context))
		{
			lastQuery = suggestionQuery
			contract.getAutocomplete(lastQuery, this)
		}
	}

	fun getSafety(query: String)
	{
		contract.callSafetyNet(query, this)
	}

	fun getQuery(query: String)
	{
		isLoading(true)
		val mInfoHolder = hashMapOf<String, Any>("query" to query)
		QueryRequest.callQuery(query, this, "data_messenger", mInfoHolder)
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		isLoading(false)
		val textError = jsonObject?.optString("RESPONSE") ?: ""
		if (textError.isNotEmpty())
		{
			try {
				val jsonError = JSONObject(textError)
				val message = jsonError.optString("message")
				view?.addChatMessage(TypeChatView.LEFT_VIEW, message)
			}
			catch (ex: Exception) { }
		}
//		val ja = JSONArray()
//		ja.put("No matches")
//		val json = JSONObject()
//		json.put("matches", ja)
//		makeMatches(json)
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
				jsonObject.has(referenceIdKey) ->
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
						dataKey ->
						{
							val numColumns = queryBase.numColumns
							val numRows = queryBase.aRows.size
							when
							{
								numColumns == 1 && numRows > 1 ->
								{
									queryBase.viewPresenter = this
									queryBase.typeView = TypeChatView.WEB_VIEW
									TypeChatView.WEB_VIEW
								}
								numColumns > 1 ->
								{
									queryBase.viewPresenter = this
									queryBase.typeView = TypeChatView.WEB_VIEW
									TypeChatView.WEB_VIEW
								}
								numColumns == 1 ->
								{
									if( queryBase.hasHash)
										TypeChatView.HELP_VIEW
									else
										TypeChatView.LEFT_VIEW
								}
								else -> TypeChatView.LEFT_VIEW
							}
						}
						else -> TypeChatView.LEFT_VIEW
					}
					if (queryBase.viewPresenter == null)
					{
						isLoading(false)
						addNewChat(typeView, queryBase)
					}
				}
				else ->
				{

				}
			}
		}
	}

	override fun isLoading(isVisible: Boolean)
	{
		view?.isLoading(isVisible)
	}

	override fun addNewChat(typeView: Int, queryBase: SimpleQuery)
	{
		view?.addNewChat(typeView, queryBase)
	}

	private fun JSONObject.getJSONData() = optJSONObject(dataKey)

	private fun makeMatches(json: JSONObject)
	{
		if (json.has("matches"))
		{
			json.optJSONArray("matches")?.let {
				val aData = ArrayList<String>()
				if (it.length() == 0)
				{
					aData.add("No matches")
				}
				else
				{
					for (index in 0 until it.length())
					{
						aData.add(it.optString(index))
					}

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
					getQuery(query)
				}
				else
				{
					val simpleQuery = FullSuggestionQuery(json)
					addNewChat(TypeChatView.FULL_SUGGESTION_VIEW, simpleQuery)
				}
			}
		}
	}
}