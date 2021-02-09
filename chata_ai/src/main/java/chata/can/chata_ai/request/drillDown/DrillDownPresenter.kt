package chata.can.chata_ai.request.drillDown

import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class DrillDownPresenter(
	private val queryBase: QueryBase,
	private val chatView: ChatContract.View?): StatusResponse
{
	fun postDrillDown(valueInRow: String = "")
	{
		var header: HashMap<String, String> ?= null
		val mParams = hashMapOf<String, Any>()

		val url = if (DataMessenger.notLoginData())
		{
			val column = queryBase.aColumn[0]
			val nameColumn = column.name

			mParams["debug"] = true
			mParams["query_id"] = queryBase.queryId
			mParams["group_bys"] = hashMapOf(nameColumn to valueInRow)
			mParams["username"] = "demo"
			mParams["customer_id"] = "qbo-1"
			mParams["user_id"] = "vidhya@chata.ai"

			"$urlStaging${api1}chata/query/drilldown"
		}
		else
		{
			val queryId = queryBase.queryId
			with(DataMessenger)
			{
				header = getAuthorizationJWT()

				val aValues = valueInRow.split("_")
				val aColumns = arrayListOf<HashMap<String, String>>()
				var iterator = 0

				for (index in queryBase.aIndex)
				{
					val column = queryBase.aColumn[index]
					if (column.isGroupable)
					{
						aColumns.add(hashMapOf("name" to column.name, "value" to aValues[iterator++]))
					}
				}
				mParams["translation"] = "include"
				mParams["test"] = true
				mParams["columns"] = aColumns
				"$domainUrl/autoql/${api1}query/${queryId}/drilldown?key=$apiKey"
			}
		}

		callStringRequest(
			Request.Method.POST,
			url,
			typeJSON,
			headers = header,
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
				queryBase.displayType = dataKey
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
				dataKey ->
				{
					val numColumns = queryBase.numColumns
					val numRows = queryBase.aRows.size
					numRows.toString()
					when
					{
						numRows == 0 -> TypeChatView.LEFT_VIEW
						(numColumns == 1 && numRows > 1) || numColumns > 1 -> TypeChatView.WEB_VIEW
						numColumns == 1 -> TypeChatView.LEFT_VIEW
						else -> TypeChatView.LEFT_VIEW
					}
				}
				else -> TypeChatView.LEFT_VIEW
			}
			chatView?.run {
				isLoading(false)
				addNewChat(typeView, queryBase)
			}
		}
	}
}