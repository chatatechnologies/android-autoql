package chata.can.chata_ai.fragment.dataMessenger

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class SuggestionPresenter(
	private val view: ChatContract.View,
	private val thankYouFeedback: String): StatusResponse
{
	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject?.toString()
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			view.addSimpleText(thankYouFeedback)
		}
	}

	fun setSuggestion(idQuery: String)
	{
		with(AutoQLData) {
			val url = "$domainUrl/autoql/${api1}query/$idQuery/suggestions?key=$apiKey"
			val header = Authentication.getAuthorizationJWT()
			header["accept-language"] = SinglentonDrawer.languageCode
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