package chata.can.chata_ai_api.fragment.inputOutput

import android.content.Context
import chata.can.chata_ai.activity.dataMessenger.DataChatContract
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.dataKey
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

	override fun onFailure(jsonObject: JSONObject?)
	{

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
						else -> {}
					}
				}
				jsonObject.has(referenceIdKey) ->
				{
					val queryBase = QueryBase(jsonObject)
				}
			}

		}
	}
}