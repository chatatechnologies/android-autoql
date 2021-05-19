package chata.can.chata_ai.fragment.dataMessenger.holder.webView

import chata.can.chata_ai.R
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.model.StringContainer
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.messageKey
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class WebViewPresenter(
	private val chatView: ChatContract.View?,
	val message: String): StatusResponse
{
	fun putReport(idQuery: String, message: String)
	{
		if (!AutoQLData.notLoginData())
		{
			val url = "${AutoQLData.domainUrl}/autoql/${api1}query/$idQuery?key=${AutoQLData.apiKey}"
			val header= getAuthorizationJWT()
			header["accept-language"] = SinglentonDrawer.languageCode

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

	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			if (jsonObject.has(messageKey))
			{
				val message = jsonObject.optString(messageKey)
				if (message == StringContainer.success)
				{
					chatView?.showAlert(message, R.drawable.ic_done)
				}
			}
		}
	}
}