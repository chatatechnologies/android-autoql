package chata.can.chata_ai_api.fragment.inputOutput

import android.content.Context
import chata.can.chata_ai.activity.dataMessenger.DataChatContract
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.dataKey
import chata.can.chata_ai.pojo.messageKey
import chata.can.chata_ai.pojo.referenceIdKey
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.tool.Network
import chata.can.chata_ai.request.query.QueryRequest
import org.json.JSONArray
import org.json.JSONObject

class InputOutputPresenter(
	private val context: Context,
	private val view: InputOutputContract
): StatusResponse
{
	private val contract = DataChatContract()

	fun getAutocomplete(suggestionQuery: String)
	{
		if (Network.checkInternetConnection(context))
		{
			contract.getAutocomplete(suggestionQuery, this)
		}
	}

	fun getSafety(query: String)
	{
		contract.callSafetyNet(query, this)
	}

	fun getQuery(query: String)
	{
		val mInfoHolder = hashMapOf<String, Any>("query" to query)
		getQuery(query, mInfoHolder)
	}

	private fun getQuery(query: String, mInfoHolder: HashMap<String, Any>)
	{
		QueryRequest.callQuery(query, this, "data_messenger", mInfoHolder)
	}

	private fun getRelatedQueries(query: String, message: String)
	{
		val words = query.split(" ").joinTo(StringBuilder(), separator = ",").toString()
		val mData = hashMapOf<String, Any>("message" to message)
		QueryRequest.callRelatedQueries(words, this, mData)
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		if (jsonObject != null)
		{
			when(jsonObject.optInt("CODE"))
			{
				400 ->
				{
					val textError = jsonObject.optString("RESPONSE") ?: ""
					if (textError.isNotEmpty())
					{
						try
						{
							val jsonError = JSONObject(textError)
							val reference = jsonError.optString(referenceIdKey)
							val query = jsonObject.optString("query") ?: ""
							if (reference == "1.1.430")
							{
								val message = jsonError.optString(messageKey)
								getRelatedQueries(query, message)
							}
							else
							{
								view.showText("suggestion not supported")
							}
						}
						catch (ex: Exception) {}
					}
				}
				else ->
				{
					val textError = jsonObject.optString("RESPONSE") ?: ""
					if (textError.isNotEmpty())
					{
						try {
							val jsonError = JSONObject(textError)
							val message = jsonError.optString("message")
							view.showText(message)
						}
						catch (ex: Exception) {}
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
						"autocomplete" ->
						{
							jsonObject.optJSONObject(dataKey)?.let { json ->
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
										view.setDataAutocomplete(aData)
									}
								}
							}
						}
						"validate" ->
						{
							jsonObject.optJSONObject(dataKey)?.let {  data ->
								if (data.has("replacements"))
								{
									data.optJSONArray("replacements")?.let {
										if (it.length() == 0)
										{
											val query = data.optString("text") ?: ""
											getQuery(query)
										}
									}
								}
							}
						}
						"callRelatedQueries" ->
						{
							jsonObject.optJSONObject("data")?.let { joData ->
								joData.optJSONArray("items")?.let { jaItems ->
									val json = JSONObject().put("query", "")

								}
							}
						}
						else -> {}
					}
				}
				jsonObject.has(referenceIdKey) ->
				{
					val queryBase = QueryBase(jsonObject)
					when(queryBase.displayType)
					{
						"suggestion" ->
						{
							if (SinglentonDrawer.mIsEnableSuggestion)
								//TODO SHOW SUGGESTION VIEW
								view.showText("${queryBase.displayType} not supported")
							else
								view.showText("${queryBase.displayType} not supported")
						}
						dataKey ->
						{
							val numColumns = queryBase.numColumns
							val numRows = queryBase.aRows.size
							when
							{
								numRows == 0 ->
								{
									view.showText(queryBase.contentHTML)
								}
								(numColumns == 1 && numRows > 1) || numColumns > 1 ->
								{
									queryBase.viewDrillDown = view
								}
								numColumns == 1 ->
								{
									if(queryBase.hasHash)
									{
										//TODO SHOW HELP
									}
									else
										view.showText(queryBase.contentHTML)
								}
								else -> view.showText(queryBase.contentHTML)
							}
						}
						else -> view.showText(queryBase.contentHTML)
					}
				}
			}

		}
	}
}