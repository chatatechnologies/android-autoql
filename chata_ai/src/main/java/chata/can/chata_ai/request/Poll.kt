package chata.can.chata_ai.request

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import com.android.volley.Request

object Poll
{
	fun callPoll(listener: StatusResponse)
	{
		val url = "${DataMessenger.domainUrl}/autoql/${api1}data-alerts/notifications/summary/" +
			"poll?key=${DataMessenger.apiKey}&unacknowledged=0"
		val mAuthorization = hashMapOf("Authorization" to "Bearer ${DataMessenger.JWT}")
		callStringRequest(
			Request.Method.GET,
			url,
			headers = mAuthorization,
			listener = listener)
	}

	fun callShowNotification(listener: StatusResponse)
	{
		val url = "${DataMessenger.domainUrl}/autoql/${api1}data-alerts/notifications?key=${DataMessenger.apiKey}"
		val mAuthorization = hashMapOf("Authorization" to "Bearer ${DataMessenger.JWT}")
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