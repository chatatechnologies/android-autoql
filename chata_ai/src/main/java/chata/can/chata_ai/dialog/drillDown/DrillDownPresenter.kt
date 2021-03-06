package chata.can.chata_ai.dialog.drillDown

import chata.can.chata_ai.dialog.DrillDownContract
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.dataKey
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class DrillDownPresenter(
	private val queryBase: QueryBase,
	private val view: DrillDownContract?
): StatusResponse
{
	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
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

	override fun onFailure(jsonObject: JSONObject?) {}

	fun getQueryDrillDown()
	{
		val queryId = queryBase.queryId
		with(AutoQLData)
		{
			val header = getAuthorizationJWT()
			header["accept-language"] = SinglentonDrawer.languageCode
			val mParams = hashMapOf<String, Any>(
				"columns" to ArrayList<String>(),
				"test" to true,
				"translation" to "include")

			val url = "$domainUrl/autoql/${api1}query/${queryId}/drilldown?key=$apiKey"
			callStringRequest(
				Request.Method.POST,
				url,
				typeJSON,
				headers = header,
				parametersAny = mParams,
				listener = this@DrillDownPresenter)
		}
	}
}