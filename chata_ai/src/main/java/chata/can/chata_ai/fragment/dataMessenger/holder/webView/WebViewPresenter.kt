package chata.can.chata_ai.fragment.dataMessenger.holder.webView

import chata.can.chata_ai.R
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.model.StringContainer
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.messageKey
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import chata.can.request_native.BaseRequest
import chata.can.request_native.RequestData
import chata.can.request_native.RequestMethod
import chata.can.request_native.StatusResponse
import org.json.JSONArray
import org.json.JSONObject

class WebViewPresenter(
	private val chatView: ChatContract.View?,
	val message: String): StatusResponse
{
	fun putReport(idQuery: String, message: String)
	{
		if (AutoQLData.wasLoginIn)
		{
			val url = "${AutoQLData.domainUrl}/autoql/${api1}query/$idQuery?key=${AutoQLData.apiKey}"
			val header= getAuthorizationJWT().apply {
				put("accept-language", SinglentonDrawer.languageCode)
				put("Content-Type", "application/json")
			}

			val mParams = hashMapOf<String, Any>("is_correct" to false)
			if (message.isNotEmpty())
			{
				mParams["message"] = message
			}
			val requestData = RequestData(
				RequestMethod.PUT,
				url,
				header,
				mParams
			)
			BaseRequest(requestData, this).execute()
		}
	}

	override fun onFailureResponse(jsonObject: JSONObject)
	{
		jsonObject.toString()
	}

	override fun onSuccessResponse(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			if (jsonObject.has(messageKey))
			{
				val message1 = jsonObject.optString(messageKey)
				if (message1 == StringContainer.success)
				{
					chatView?.showAlert(message, R.drawable.ic_done)
				}
			}
		}
	}
}