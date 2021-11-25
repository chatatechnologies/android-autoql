package chata.can.chata_ai.dialog.drillDown

import chata.can.chata_ai.dialog.DrillDownContract
import chata.can.chata_ai.model.StringContainer
import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import chata.can.request_native.BaseRequest
import chata.can.request_native.RequestData
import chata.can.request_native.RequestMethod
import org.json.JSONArray
import org.json.JSONObject

class DrillDownPresenter(
	private val queryBase: QueryBase,
	private val view: DrillDownContract?
): chata.can.request_native.StatusResponse
{
	override fun onSuccessResponse(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			val queryBase = QueryBase(jsonObject)
			queryBase.hasDrillDown = false
			if (queryBase.displayType == "table")
			{
				queryBase.displayType = dataKey
			}
			view?.loadDrillDown(queryBase)
		}
	}

	override fun onFailureResponse(jsonObject: JSONObject)
	{
		val reference = jsonObject.optString(referenceIdKey) ?: ""
		val message = jsonObject.optString(messageKey) ?: ""
		val messageComplete = "$message\n\n${StringContainer.errorId} $reference"

		val json = JSONObject().put("query", "")
		val newQueryBase = QueryBase(json)
		newQueryBase.message = messageComplete
		//newQueryBase.queryId = "queryId"
		newQueryBase.hasDrillDown = false
		view?.loadDrillDown(newQueryBase)
	}

	fun getQueryDrillDown()
	{
		val queryId = queryBase.queryId
		with(AutoQLData)
		{
			val header = getAuthorizationJWT().apply {
				put("accept-language", SinglentonDrawer.languageCode)
				put("Content-Type", "application/json")
			}
			val mParams = hashMapOf<String, Any>(
				"columns1" to ArrayList<String>(),
				"test" to true,
				"translation" to "include")

			val url = "$domainUrl/autoql/${api1}query/${queryId}/drilldown?key=$apiKey"
			val requestData = RequestData(
				RequestMethod.POST,
				url,
				header,
				mParams
			)
			BaseRequest(requestData, this@DrillDownPresenter).execute()
		}
	}
}