package chata.can.chata_ai.fragment.dataMessenger.holder.webView

import chata.can.chata_ai.pojo.DataMessenger.apiKey
import chata.can.chata_ai.pojo.DataMessenger.domainUrl
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.messageKey
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class WebViewPresenter: StatusResponse
{
	fun putReport(idQuery: String)
	{
		if (domainUrl.isNotEmpty())
		{
			val url = "$domainUrl/autoql/${api1}query/$idQuery?key=$apiKey"
			val header= getAuthorizationJWT()

			val mParams = hashMapOf<String, Any>("is_correct" to false)

			callStringRequest(
				Request.Method.PUT,
				url,
				typeJSON,
				headers = header,
				parametersAny = mParams,
				listener = this)
		}
	}

	override fun onFailure(jsonObject: JSONObject?) {}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			if (jsonObject.has(messageKey))
			{
				val message = jsonObject.optString(messageKey)
				if (message == "Success")
				{
					println(message)
				}
			}
		}
	}
}