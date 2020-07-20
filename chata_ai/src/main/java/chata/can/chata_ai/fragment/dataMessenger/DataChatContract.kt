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
				//header = hashMapOf("Authorization" to "Bearer eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJSUzI1NiIsICJraWQiOiAiNzUxZmYzY2YxMjA2ZGUwODJhNzM1MjY5OTI2ZDg0NTgzYjcyOTZmNCJ9.eyJpYXQiOiAxNTk1Mjc4MjI0LCAiZXhwIjogMTU5NTI5OTgyNCwgImlzcyI6ICJkZW1vMy1qd3RhY2NvdW50QHN0YWdpbmctMjQ1NTE0LmlhbS5nc2VydmljZWFjY291bnQuY29tIiwgImF1ZCI6ICJkZW1vMy1zdGFnaW5nLmNoYXRhLmlvIiwgInN1YiI6ICJkZW1vMy1qd3RhY2NvdW50QHN0YWdpbmctMjQ1NTE0LmlhbS5nc2VydmljZWFjY291bnQuY29tIiwgImVtYWlsIjogImRlbW8zLWp3dGFjY291bnRAc3RhZ2luZy0yNDU1MTQuaWFtLmdzZXJ2aWNlYWNjb3VudC5jb20iLCAicHJvamVjdF9pZCI6ICJzcGlyYS1kZW1vMyIsICJ1c2VyX2lkIjogInZpZGh5YWs0NjRAZ21haWwuY29tIiwgImRpc3BsYXlfbmFtZSI6ICJ2aWRoeWFrNDY0QGdtYWlsLmNvbSIsICJyZXNvdXJjZV9hY2Nlc3MiOiBbIi9hdXRvcWwvYXBpL3YxL3J1bGVzLyoqIiwgIi9hdXRvcWwvYXBpL3YxL3F1ZXJ5IiwgIi9hdXRvcWwvYXBpL3YxL3J1bGVzIiwgIi9hdXRvcWwvYXBpL3YxL3F1ZXJ5LyoqIl19.pXvk_EyFSCizwWk9vZ8aCk6ceASwzY1PbXdxyEdlEhBY-ifewGoTS2pyD8qQFayiOHHRblBUFkiOw8ACsovIJxVcErXjO4aeUP477YARk80YRY_LfVxM3r1Tevpczei2SlwaaVn3jR6Nl7GKyP3ppYa8Cesw_wFB6j6eYly_z_Xl0vBo-d0-VkrZMG9OI2yp-uzqmMFK-NjQub0TrE5Ux8cHZVY2Ro0rFpW2_jFXg-nIbos27kaxqQmenjlbPZOYW76wsoeyelJdOiZsvJcCUOhzSuyuAVWpILHVY3A-wtAc3dgVGMujCzfy5UFRdK53ogIjMxqmoBomHZKiHS9BNw==")
				nameService = "autocomplete"
				"$domainUrl/autoql/${api1}query/autocomplete?text=$content&key=$apiKey"
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