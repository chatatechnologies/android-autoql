package chata.can.chata_ai.activity.chat

import chata.can.chata_ai.pojo.request.StatusResponse
import org.json.JSONArray
import org.json.JSONObject

class ChatServicePresenter(private var view: ChatContract?) : StatusResponse
{
	private var lastQuery = ""
	private val interactor = DataChatInteractor()

	fun getAutocomplete(suggestionQuery: String)
	{
		lastQuery = suggestionQuery
		interactor.getAutocomplete(lastQuery, this)
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