package chata.can.chata_ai.fragment.dataMessenger

import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.pojo.urlBase
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import com.android.volley.Request

class DataChatContract
{
	fun getAutocomplete(content: String, listener : StatusResponse)
	{
		var header: HashMap<String, String> ?= null
		val nameService: String
		val url = if (!DataMessenger.isNecessaryLogin || DataMessenger.domainUrl.isEmpty())
		{
			nameService = "demoAutocomplete"
			"$urlBase${api1}autocomplete?q=$content&projectid=1&user_id=demo&customer_id=demo"
		}
		else
		{
			with(DataMessenger)
			{
				header = getAuthorizationJWT()
				nameService = "autocomplete"
				"$domainUrl/autoql/${api1}query/autocomplete?text=$content&key=$apiKey"
			}
		}

		callStringRequest(
			Request.Method.GET,
			url,
			typeJSON,
			headers = header,
			infoHolder = hashMapOf("nameService" to nameService, "url" to url),
			listener = listener)
	}

	fun callSafetyNet(query: String, listener: StatusResponse)
	{
		var header: HashMap<String, String> ?= null
		val nameService: String
		val url = if (!DataMessenger.isNecessaryLogin || DataMessenger.domainUrl.isEmpty())
		{
			nameService = "safetynet"
			"$urlBase${api1}safetynet?q=$query&projectId=1&user_id=demo&customer_id=demo"
		}
		else
		{
			with(DataMessenger)
			{
				header = getAuthorizationJWT()
				nameService = "validate"
				"$domainUrl/autoql/${api1}query/validate?text=$query&key=$apiKey"
			}
		}

		callStringRequest(
			Request.Method.GET,
			url,
			typeJSON,
			headers = header,
			infoHolder = hashMapOf("nameService" to nameService),
			listener = listener)
	}
}