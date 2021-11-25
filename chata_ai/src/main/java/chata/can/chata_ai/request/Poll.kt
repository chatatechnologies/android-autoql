package chata.can.chata_ai.request

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.request_native.BaseRequest
import chata.can.request_native.RequestData
import chata.can.request_native.RequestMethod
import chata.can.request_native.StatusResponse

object Poll
{
	fun callPoll(listener: StatusResponse)
	{
		val url = "${AutoQLData.domainUrl}/autoql/${api1}data-alerts/notifications/summary/" +
			"poll?key=${AutoQLData.apiKey}&unacknowledged=0"
		val header = hashMapOf(
			"Authorization" to "Bearer ${AutoQLData.JWT}",
			"accept-language" to SinglentonDrawer.languageCode)
		val requestData = RequestData(
			RequestMethod.GET,
			url,
			header
		)
		BaseRequest(requestData, listener).execute()
	}

	fun callShowNotification(listener: StatusResponse)
	{
		val url = "${AutoQLData.domainUrl}/autoql/${api1}data-alerts/notifications?key=${AutoQLData.apiKey}"
		val header = hashMapOf(
			"Authorization" to "Bearer ${AutoQLData.JWT}",
			"accept-language" to SinglentonDrawer.languageCode,
			"Content-Type" to "application/json")
		val mParams = hashMapOf<String, Any>(
			"notification_id" to "null",
			"state" to "ACKNOWLEDGED")
		val requestData = RequestData(
			RequestMethod.PUT,
			url,
			header,
			mParams
		)
		BaseRequest(requestData, listener).execute()
	}
}