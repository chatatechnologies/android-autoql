package chata.can.chata_ai.fragment.dataMessenger.presenter

import android.content.Context
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.DataChatContract
import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.chat.*
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.tool.Network
import chata.can.chata_ai.request.query.QueryRequest
import org.json.JSONArray
import org.json.JSONObject

class ChatServicePresenter(
	private val context: Context,
	private var view: ChatContract.View?) : StatusResponse,
	PresenterContract
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

	private fun getQuery(query: String, mInfoHolder: HashMap<String, Any>)
	{
		isLoading(true)
		QueryRequest.callQuery(query, this, "data_messenger", mInfoHolder)
	}

	fun getQuery(query: String)
	{
		val mInfoHolder = hashMapOf<String, Any>("query" to query)
		getQuery(query, mInfoHolder)
	}

	private fun getRelatedQueries(query: String, message: String, queryId: String)
	{
		//val words = query.split(" ").joinTo(StringBuilder(), separator = ",").toString()
		val mData = hashMapOf<String, Any>(
			"query" to query,
			"message" to message,
			"query_id" to queryId)
//QueryRequest.callRelatedQueries(words, this, mData)
		QueryRequest.callRelatedQueries(query, this, mData)
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		if (jsonObject != null)
		{
			when(jsonObject.optString("nameService"))
			{
				"autocomplete" ->
				{
					val aData = arrayListOf("No matches")
					view?.setDataAutocomplete(aData)
				}
				else ->
				{
					val textError = jsonObject.optString("RESPONSE") ?: ""
					if (textError.isNotEmpty())
					{
						try {
							val jsonError = JSONObject(textError)
							val reference = jsonError.optString(referenceIdKey) ?: ""
							var message = jsonError.optString(messageKey) ?: ""
							val query = jsonObject.optString("query") ?: ""
							if (reference == "1.1.430")
							{
								if (SinglentonDrawer.mIsEnableSuggestion)
								{
									val response = jsonObject.optString("RESPONSE", "")
									var queryId = ""
									try {
										val joResponse = JSONObject(response)
										joResponse.optJSONObject("data")?.let { joData ->
											queryId = joData.optString("query_id")
										}
									} catch (ex: Exception) {}
									getRelatedQueries(query, message, queryId)
								}
								else
								{
									message = "suggestion not supported"
									view?.isLoading(false)
								}
								view?.addChatMessage(TypeChatView.LEFT_VIEW, message, query)
							}
							else
							{
//								val messageComplete = if (message.isEmpty())
//								{
//									"Internal Service Error: Our system is experiencing an unexpected error. We're aware of this issue and are working to fix it as soon as possible."
//								}
//								else
//									"$message\n\nError ID: $reference"
//								view?.run {
//									addChatMessage(TypeChatView.LEFT_VIEW, messageComplete, query)
//									isLoading(false)
//								}
							}
						} catch (ex: Exception) {}
					}
				}
			}
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
							jsonObject.getJSONData()?.let { data ->
								makeSuggestion(data, "replacements", "text")
							}
						}
						"callRelatedQueries" ->
						{
							jsonObject.optJSONObject("data")?.let {
								joData ->
								joData.optJSONArray("items")?.let {
									jaItems ->
									val json = JSONObject().put("query", "")
									val queryBase = QueryBase(json).apply {
										val queryId = jsonObject.optString("query_id")
										this.queryId = queryId
										for (index in 0 until jaItems.length())
										{
											val item = jaItems.opt(index).toString()
											aRows.add(arrayListOf(item))
										}
									}
									queryBase.message = jsonObject.optString("message", "") ?: ""
									val query = jsonObject.optString("query", "")
									isLoading(false)
									view?.run {
										addNewChat(TypeChatView.SUGGESTION_VIEW, queryBase)
									}
								}
							}
						}
						else ->
						{

						}
					}
				}
				jsonObject.has(referenceIdKey) ->
				{
//					ChatComponent(jsonObject)
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
								numRows == 0 ->
								{
									TypeChatView.LEFT_VIEW
								}
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
									if(queryBase.hasHash)
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