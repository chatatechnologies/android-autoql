package chata.can.chata_ai.request

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import com.android.volley.Request

object Poll
{
	fun callPoll(listener: StatusResponse)
	{
		val url = "${DataMessenger.domainUrl}/autoql/${api1}rules/notifications/summary/" +
			"poll?key=${DataMessenger.apiKey}&unacknowledged=0"
		val mAuthorization = hashMapOf("Authorization" to "Bearer ${DataMessenger.JWT}")
		callStringRequest(
			Request.Method.GET,
			url,
			headers = mAuthorization,
			listener = listener)
	}
}