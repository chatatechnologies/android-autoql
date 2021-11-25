package chata.can.chata_ai.dialog.twiceDrill

import chata.can.chata_ai.dialog.DrillDownContract
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import chata.can.request_native.BaseRequest
import chata.can.request_native.RequestData
import chata.can.request_native.RequestMethod
import chata.can.request_native.StatusResponse
import org.json.JSONArray
import org.json.JSONObject

class TwiceDrillPresenter(
	private var contract: DrillDownContract,
	private val queryBase: QueryBase
): StatusResponse
{
	override fun onFailureResponse(jsonObject: JSONObject)
	{
		jsonObject.toString()
	}

	override fun onSuccessResponse(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			val queryBase = QueryBase(jsonObject)
			//queryBase.hasDrillDown = false
			contract.loadDrillDown(queryBase)
		}
	}

	fun getQueryDrillDown(value1: String, value2: String = "")
	{
		val queryId = queryBase.queryId
		with(AutoQLData)
		{
			val header = getAuthorizationJWT().apply {
				put("accept-language", SinglentonDrawer.languageCode)
				put("Content-Type", "application/json")
			}

			val aColumn = arrayListOf<HashMap<String, String>>()
			when (queryBase.aColumn.size)
			{
				2 ->
				{
					aColumn.add(hashMapOf("name" to queryBase.aColumn[0].name, "value" to value1))
				}
				3 ->
				{
					aColumn.add(hashMapOf("name" to queryBase.aColumn[0].name, "value" to value1))
					aColumn.add(hashMapOf("name" to queryBase.aColumn[1].name, "value" to value2))
				}
			}

			val mParams = hashMapOf<String, Any>(
				"columns" to aColumn,
				"test" to true,
				"translation" to "include")

			val url = "$domainUrl/autoql/${api1}query/${queryId}/drilldown?key=$apiKey"
			val requestData = RequestData(
				RequestMethod.POST,
				url,
				header,
				mParams
			)
			BaseRequest(requestData, this@TwiceDrillPresenter).execute()
		}
	}
}