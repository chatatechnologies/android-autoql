package chata.can.chata_ai.activity.notification.adapter

import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class RuleQueryPresenter(private val view: NotificationContract): StatusResponse
{
	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject?.let {

		}
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			jsonObject.optJSONObject("query_result")?.let { joQueryResult ->
				val queryBase = QueryBase(joQueryResult)
				when(queryBase.displayType)
				{
					"suggestion" ->
					{
						if (SinglentonDrawer.mIsEnableSuggestion)
							view.showText("${queryBase.displayType} not supported")
						else
							view.showText("${queryBase.displayType} not supported")
					}
					dataKey ->
					{
						val numColumns = queryBase.numColumns
						val numRows = queryBase.aRows.size
						when
						{
							numRows == 0 ->
							{
								view.showText(queryBase.contentHTML)
							}
							(numColumns == 1 && numRows > 1) || numColumns > 1 ->
							{
								//queryBase.viewDrillDown = view
							}
							numColumns == 1 ->
							{
								if(queryBase.hasHash)
								{
									//TODO SHOW HELP
								}
								else
									view.showText(queryBase.contentHTML)
							}
							else -> view.showText(queryBase.contentHTML)
						}
					}
					else -> view.showText(queryBase.contentHTML)
				}
			}
		}
	}

	fun getRuleQuery(idRule: String)
	{
		val url = "$urlChataIO${api1}rule-notifications/$idRule?key=${DataMessenger.apiKey}"
		val mAuthorization = Authentication.getAuthorizationJWT()
		mAuthorization["Integrator-Domain"] = DataMessenger.domainUrl
		RequestBuilder.callStringRequest(
			Request.Method.GET,
			url,
			typeJSON,
			mAuthorization,
			listener = this)
	}
}