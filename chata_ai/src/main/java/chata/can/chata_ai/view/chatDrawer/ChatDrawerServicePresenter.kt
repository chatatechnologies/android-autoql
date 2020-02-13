package chata.can.chata_ai.view.chatDrawer

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.pojo.urlBase
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class ChatDrawerServicePresenter(private val view: ChatDrawerContract): StatusResponse
{
	fun autocomplete(sAutocomplete: String)
	{
		val url = "$urlBase${api1}autocomplete?q=$sAutocomplete&projectid=1"
		RequestBuilder.callStringRequest(
			Request.Method.GET,
			url,
			typeJSON,
			listener = this
		)
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject?.let {

		}
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			if (it.has("matches"))
			{
				it.optJSONArray("matches")?.let {
					itMatches ->
					val aMatches = ArrayList<String>()
					if (itMatches.length() > 0)
					{
						for (index in 0 until itMatches.length())
						{
							aMatches.add(itMatches.optString(index, ""))
						}
					}
					view.setDataAutocomplete(aMatches)
				}
			}
		}

		jsonArray?.let {

		}
	}
}