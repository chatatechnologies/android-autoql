package chata.can.chata_ai.request.drillDown

import chata.can.chata_ai.activity.chat.ChatContract
import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class DrillDownPresenter(
	private val queryBase: QueryBase,
	private val view: ChatContract.View?): StatusResponse
{
	fun postDrillDown(valueInRow: String)
	{
		val column = queryBase.aColumn[0]
		val nameColumn = column.name

		val url1 = "$urlStaging${api1}chata/query/drilldown"

		val mParams = hashMapOf<String, Any>(
			"query_id" to queryBase.queryId,
			"group_bys" to hashMapOf(nameColumn to valueInRow),
			"username" to "demo",
			"customer_id" to "qbo-1",
			"user_id" to "vidhya@chata.ai",
			"debug" to true)

		val url = if (DataMessenger.domainUrl.isEmpty())
		{
			"$urlStaging${api1}chata/query/drilldown"
		}
		else
		{
			"https://qbo-staging.chata.io/autoql/api/v1/query/q_cBWzZxrCRCGuBuEv7DYXaQ/"
			""
		}

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
		if (jsonObject != null)
		{
			val queryBase = QueryBase(jsonObject)
			queryBase.hasDrillDown = false
			if (queryBase.displayType == "table")
			{
				queryBase.displayType = "data"
			}
			val typeView = when(queryBase.displayType)
			{
				"suggestion" ->
				{
					if (SinglentonDrawer.mIsEnableSuggestion)
					{
						val query = jsonObject.optString("query")
						queryBase.message = query
						TypeChatView.SUGGESTION_VIEW
					}
					else
					{
						queryBase.message = "${queryBase.displayType} not supported"
						TypeChatView.LEFT_VIEW
					}
				}
				"data" ->
				{
					val numColumns = queryBase.numColumns
					when
					{
						numColumns == 1 -> TypeChatView.LEFT_VIEW
						numColumns > 1 -> TypeChatView.WEB_VIEW
						else -> TypeChatView.LEFT_VIEW
					}
				}
				else -> TypeChatView.LEFT_VIEW
			}
			view?.addNewChat(typeView, queryBase)
		}
	}
}