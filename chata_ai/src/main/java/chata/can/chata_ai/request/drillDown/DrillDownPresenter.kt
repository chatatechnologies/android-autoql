package chata.can.chata_ai.request.drillDown

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.pojo.urlStaging
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class DrillDownPresenter(private val queryBase: QueryBase): StatusResponse
{
	fun postDrillDown(valueInRow: String)
	{
		val column = queryBase.aColumn[0]
		val nameColumn = column.name

		val url = "$urlStaging${api1}chata/query/drilldown"

		val mParams = hashMapOf<String, Any>(
			"query_id" to queryBase.queryId,
			"group_bys" to hashMapOf(nameColumn to valueInRow),
			"username" to "demo",
			"customer_id" to "qbo-1",
			"user_id" to "vidhya@chata.ai",
			"debug" to true)

		callStringRequest(
			Request.Method.POST,
			url,
			typeJSON,
			parametersAny = mParams,
			listener = this)
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject.toString()
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject.toString()
	}
}