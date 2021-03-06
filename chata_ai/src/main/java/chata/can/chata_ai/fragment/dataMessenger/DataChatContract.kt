package chata.can.chata_ai.fragment.dataMessenger

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.pojo.urlBase
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import com.android.volley.Request
import java.net.URLEncoder

class DataChatContract
{
	fun getAutocomplete(content: String, listener : StatusResponse)
	{
		var header: HashMap<String, String> ?= null
		val nameService: String
		val url = if (AutoQLData.notLoginData())
		{
			nameService = "demoAutocomplete"
			"$urlBase${api1}autocomplete?q=$content&projectid=1&user_id=demo&customer_id=demo"
		}
		else
		{
			with(AutoQLData)
			{
				header = getAuthorizationJWT()
				header?.let {
					it["accept-language"] = SinglentonDrawer.languageCode
				}
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
		val queryEncode = URLEncoder.encode(query, "UTF-8").replace("+", " ")
		val url = if (AutoQLData.notLoginData())
		{
			nameService = "safetynet"
			"$urlBase${api1}safetynet?q=$queryEncode&projectId=1&user_id=demo&customer_id=demo"
		}
		else
		{
			with(AutoQLData)
			{
				header = getAuthorizationJWT()
				header?.let {
					it["accept-language"] = SinglentonDrawer.languageCode
				}
				nameService = "validate"
				"$domainUrl/autoql/${api1}query/validate?text=$queryEncode&key=$apiKey"
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