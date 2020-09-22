package chata.can.chata_ai.activity.notification

import chata.can.chata_ai.activity.notification.model.Notification
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.apiKey
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.domainUrl
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class NotificationPresenter(private val view: NotificationContract): StatusResponse
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
			val aNotification = ArrayList<Notification>()
			jsonObject.optJSONObject("data")?.let {
				val totalPages = it.optInt("total_pages", 0)
				it.optJSONArray("notifications")?.let { jaNotifications ->
					for (index in 0 until jaNotifications.length())
					{
						val json = jaNotifications.optJSONObject(index)
						val title = json.optString("rule_title")
						val message = json.optString("rule_message")
						val query = json.optString("rule_query")
						val createdAt = json.optInt("created_at")

						val notification = Notification(title, message, query, createdAt)
						aNotification.add(notification)
					}
					view.showNotifications(totalPages, aNotification)
				}
			}
		}
	}

	fun getNotifications(offset: Int = 0, limit: Int = 10)
	{
		val url = "$domainUrl/autoql/${api1}rules/notifications?key=${apiKey}&offset=$offset&limit=$limit"

		callStringRequest(
			Request.Method.GET,
			url,
			typeJSON,
			headers = Authentication.getAuthorizationJWT(),
			listener = this)
	}
}