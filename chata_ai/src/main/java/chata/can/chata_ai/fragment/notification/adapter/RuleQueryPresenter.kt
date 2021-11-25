package chata.can.chata_ai.fragment.notification.adapter

import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.*
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.request_native.BaseRequest
import chata.can.request_native.RequestData
import chata.can.request_native.RequestMethod
import chata.can.request_native.StatusResponse
import org.json.JSONArray
import org.json.JSONObject

class RuleQueryPresenter(private val view: NotificationContract): StatusResponse
{
	override fun onFailureResponse(jsonObject: JSONObject)
	{
		showInternalServiceError()
	}

	override fun onSuccessResponse(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			jsonObject.optJSONObject("query_result")?.let { joQueryResult ->
				val queryBase = QueryBase(joQueryResult)
				when(queryBase.displayType)
				{
					"suggestion" ->
					{
						if (SinglentonDrawer.mIsEnableSuggestion)
							view.showText("${queryBase.displayType} not supported", 16f)
						else
							view.showText("${queryBase.displayType} not supported", 16f)
					}
					dataKey ->
					{
						val numColumns = queryBase.numColumns
						val numRows = queryBase.aRows.size
						when
						{
							numRows == 0 ->
							{
								view.showText(queryBase.contentHTML, 22f)
							}
							(numColumns == 1 && numRows > 1) || numColumns > 1 ->
							{
								queryBase.toString()
								//queryBase.viewDrillDown = view
							}
							numColumns == 1 ->
							{
								if (queryBase.hasHash)
								{
									queryBase.toString()
									//TODO SHOW HELP
								}
								else
									view.showText(queryBase.contentHTML, 22f)
							}
							else -> view.showText(queryBase.contentHTML, 22f)
						}
					}
					"" -> showInternalServiceError()
					else -> view.showText(queryBase.contentHTML, 22f)
				}
			}
		}
	}

	private fun showInternalServiceError()
	{
		view.showText(textSize = 16f, intRes = R.string.internal_service_error)
	}

	fun getRuleQuery(idRule: String)
	{
		view.showLoading()
		val url = "${getMainURL()}${api1}rule-notifications/$idRule?key=${AutoQLData.apiKey}"
		val header = Authentication.getAuthorizationJWT()
		header["accept-language"] = SinglentonDrawer.languageCode
		header["Integrator-Domain"] = AutoQLData.domainUrl
		val requestData = RequestData(
			RequestMethod.GET,
			url,
			header)
		BaseRequest(requestData, this).execute()
	}
}