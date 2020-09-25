package chata.can.chata_ai_api.fragment.inputOutput

import android.content.Context
import chata.can.chata_ai.activity.dataMessenger.DataChatContract
import chata.can.chata_ai.pojo.dataKey
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.tool.Network
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

	override fun onFailure(jsonObject: JSONObject?)
	{

	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			if (jsonObject.has("nameService"))
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
					else -> {}
				}
			}
		}
	}
}