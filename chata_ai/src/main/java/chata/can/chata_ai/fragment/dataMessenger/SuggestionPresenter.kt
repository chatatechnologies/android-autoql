package chata.can.chata_ai.fragment.dataMessenger

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import org.json.JSONArray
import org.json.JSONObject

class SuggestionPresenter: StatusResponse
{
	override fun onFailure(jsonObject: JSONObject?)
	{

	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{

	}

	fun setSuggestion(idQuery: String)
	{
		with(DataMessenger){
			val url = "$domainUrl/autoql/${api1}query/$idQuery/suggestions?key=$apiKey"
		}
		//https://accounting-demo-staging.chata.io/autoql/api/v1/query/q_rHIptjtIT3GebVuANcPJDg/suggestions?key=AIzaSyDX28JVW248PmBwN8_xRROWvO0a2BWH67o
	}
}