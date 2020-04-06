package chata.can.chata_ai.activity.chat

import chata.can.chata_ai.pojo.DataMessenger
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.pojo.urlBase
import com.android.volley.Request

class DataChatInteractor
{
	fun getAutocomplete(content: String, listener : StatusResponse)
	{
		var header: HashMap<String, String> ?= null

		val url = if (DataMessenger.domainUrl.isEmpty())
		{
			"$urlBase${api1}autocomplete?q=$content&projectid=1&user_id=demo&customer_id=demo"
		}
		else
		{
			with(DataMessenger)
			{
				header = hashMapOf("Authorization" to "Bearer $JWT")
				"$domainUrl/autoql/${api1}query/autocomplete?text=$content&key=$apiKey"
			}
		}
		callStringRequest(Request.Method.GET,url, typeJSON, headers = header, listener = listener)
	}
}