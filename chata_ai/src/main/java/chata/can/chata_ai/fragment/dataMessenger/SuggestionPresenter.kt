package chata.can.chata_ai.fragment.dataMessenger

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class SuggestionPresenter: StatusResponse
{
	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject?.let {
			it.toString()
		}
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			it.toString()
		}
		jsonArray?.let {
			it.toString()
		}
	}

	fun setSuggestion(idQuery: String)
	{
		//https://accounting-demo-staging.chata.io/autoql/api/v1/query/q_rHIptjtIT3GebVuANcPJDg/suggestions?key=AIzaSyDX28JVW248PmBwN8_xRROWvO0a2BWH67o
		with(DataMessenger) {
			val url = "$domainUrl/autoql/${api1}query/$idQuery/suggestions?key=$apiKey"
			val header = Authentication.getAuthorizationJWT()
			val mParams = hashMapOf<String, Any>("suggestion" to "None of these")
			RequestBuilder.callStringRequest(
				Request.Method.PUT,
				url,
				typeJSON,
				headers = header,
				parametersAny = mParams,
				listener = this@SuggestionPresenter)
		}
	}
}