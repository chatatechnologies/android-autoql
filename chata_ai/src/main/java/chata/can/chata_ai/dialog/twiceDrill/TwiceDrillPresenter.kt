package chata.can.chata_ai.dialog.twiceDrill

import chata.can.chata_ai.dialog.DrillDownContract
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class TwiceDrillPresenter(
	private var contract: DrillDownContract,
	private val queryBase: QueryBase,
	private val value1: String,
	private val value2: String
): StatusResponse
{
	override fun onFailure(jsonObject: JSONObject?)
	{
		if (jsonObject != null)
		{

		}
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			val queryBase = QueryBase(jsonObject)
			queryBase.hasDrillDown = false

			contract.loadDrillDown(queryBase)
		}
	}

	fun getQueryDrillDown()
	{
		val queryId = queryBase.queryId
		with(DataMessenger)
		{
			val header = Authentication.getAuthorizationJWT()

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
			RequestBuilder.callStringRequest(
				Request.Method.POST,
				url,
				typeJSON,
				headers = header,
				parametersAny = mParams,
				listener = this@TwiceDrillPresenter
			)
		}
	}
}