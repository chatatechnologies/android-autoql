package chata.can.chata_ai.activity.chat

import chata.can.chata_ai.pojo.WebService
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import com.android.volley.Request

class DataChatInteractor
{
	fun getAutocomplete(content: String, listener : StatusResponse)
	{
		with(WebService)
		{
			val url = "$urlBase${versionApi}autocomplete?q=$content&projectid=1&user_id=demo&customer_id=demo"
			callStringRequest(Request.Method.GET,url, typeJSON, listener = listener)
		}
	}
}