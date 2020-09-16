package chata.can.chata_ai.fragment.dataMessenger.holder.webView

import chata.can.chata_ai.R
import chata.can.chata_ai.activity.dataMessenger.ChatContract
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.messageKey
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class WebViewPresenter(private val chatView: ChatContract.View?): StatusResponse
{
	fun putReport(idQuery: String, message: String)
	{
		if (!DataMessenger.isDemo())
		{
			val url = "${DataMessenger.domainUrl}/autoql/${api1}query/$idQuery?key=${DataMessenger.apiKey}"
			val header= getAuthorizationJWT()

			val mParams = hashMapOf<String, Any>("is_correct" to false)
			if (message.isNotEmpty())
			{
				mParams["message"] = message
			}

			callStringRequest(
				Request.Method.PUT,
				url,
				typeJSON,
				headers = header,
				parametersAny = mParams,
				listener = this)
		}
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		if (jsonObject != null)
		{
			println(jsonObject)
		}
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			if (jsonObject.has(messageKey))
			{
				val message = jsonObject.optString(messageKey)
				if (message == "Success")
				{
					chatView?.showAlert("Thank you for your feedback.", R.drawable.ic_done)
					println(message)
				}
			}
		}
	}
}