package chata.can.chata_ai.fragment.dataMessenger

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.request_native.BaseRequest
import chata.can.request_native.RequestData
import chata.can.request_native.RequestMethod
import org.json.JSONArray
import org.json.JSONObject

class SuggestionPresenter(
	private val view: ChatContract.View,
	private val thankYouFeedback: String): chata.can.request_native.StatusResponse
{
	override fun onFailureResponse(jsonObject: JSONObject)
	{
		jsonObject.toString()
	}

	override fun onSuccessResponse(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			view.addSimpleText(thankYouFeedback)
		}
	}

	fun setSuggestion(idQuery: String)
	{
		with(AutoQLData) {
			val url = "$domainUrl/autoql/${api1}query/$idQuery/suggestions?key=$apiKey"
			val header = Authentication.getAuthorizationJWT().apply {
				put("accept-language", SinglentonDrawer.languageCode)
				put("Content-Type", "application/json")
			}

			val mParams = hashMapOf<String, Any>("suggestion" to "None of these")
			val requestData = RequestData(
				RequestMethod.PUT,
				url,
				header,
				mParams
			)
			BaseRequest(requestData, this@SuggestionPresenter).execute()
		}
	}
}