package chata.can.chata_ai.fragment.dataMessenger

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.view.bubbleHandle.DataMessengerRoot
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class SuggestionPresenter(private val view: ChatContract.View): StatusResponse
{
	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject?.toString()
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			view.addSimpleText("Thank you for your feedback")
		}
	}

	fun setSuggestion(idQuery: String)
	{
		with(DataMessengerRoot) {
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