package chata.can.chata_ai.activity.notification.adapter

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.pojo.urlChataIO
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class RuleQueryPresenter: StatusResponse
{
	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject?.let {

		}
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			jsonObject.optJSONObject("query_result")?.let { joQueryResult ->
				QueryBase(joQueryResult)
			}
		}
	}

	fun getRuleQuery(idRule: Int)
	{
		val url = "$urlChataIO${api1}rule-notifications/$idRule?key=${DataMessenger.apiKey}"
		val mAuthorization = Authentication.getAuthorizationJWT()
		mAuthorization["Integrator-Domain"] = DataMessenger.domainUrl
		RequestBuilder.callStringRequest(
			Request.Method.GET,
			url,
			typeJSON,
			mAuthorization,
			listener = this)
	}
}