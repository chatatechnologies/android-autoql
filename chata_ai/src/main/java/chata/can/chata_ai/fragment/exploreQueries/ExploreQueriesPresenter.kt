package chata.can.chata_ai.fragment.exploreQueries

import chata.can.chata_ai.pojo.DataMessenger
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class ExploreQueriesPresenter: StatusResponse
{
	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if(jsonObject != null)
		{

		}
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		if(jsonObject != null)
		{

		}
	}

	fun validateQuery(query: String)
	{
		with(DataMessenger)
		{
			val header = getAuthorizationJWT()
			val url = "$domainUrl/autoql/${api1}query/validate?text=$query&key=$apiKey"

			callStringRequest(
				Request.Method.GET,
				url,
				typeJSON,
				headers = header,
				infoHolder = hashMapOf("nameService" to "validate"),
				listener = this@ExploreQueriesPresenter)
		}
	}
}