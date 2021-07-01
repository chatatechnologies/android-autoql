package chata.can.chata_ai.request

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import com.android.volley.Request

object Poll
{
	fun callPoll(listener: StatusResponse)
	{
		val url = "${AutoQLData.domainUrl}/autoql/${api1}data-alerts/notifications/summary/" +
			"poll?key=${AutoQLData.apiKey}&unacknowledged=0"
		val mAuthorization = hashMapOf(
			"Authorization" to "Bearer ${AutoQLData.JWT}",
			"accept-language" to SinglentonDrawer.languageCode)
		callStringRequest(
			Request.Method.GET,
			url,
			headers = mAuthorization,
			listener = listener)
	}

	fun callShowNotification(listener: StatusResponse)
	{
		val url = "${AutoQLData.domainUrl}/autoql/${api1}data-alerts/notifications?key=${AutoQLData.apiKey}"
		val mAuthorization = hashMapOf(
			"Authorization" to "Bearer ${AutoQLData.JWT}",
			"accept-language" to SinglentonDrawer.languageCode)
		val mParams = hashMapOf<String, Any>(
			"notification_id" to "null",
			"state" to "ACKNOWLEDGED")
		callStringRequest(
			Request.Method.PUT,
			url,
			typeJSON,
			headers = mAuthorization,
			parametersAny = mParams,
			listener = listener)
	}
}