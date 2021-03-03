package chata.can.chata_ai.fragment.notification

import chata.can.chata_ai.fragment.notification.model.Notification
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.view.bubbleHandle.DataMessengerRoot.apiKey
import chata.can.chata_ai.view.bubbleHandle.DataMessengerRoot.domainUrl
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class NotificationPresenter(private val view: NotificationContract): StatusResponse
{
	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject?.toString()
		view.showNotifications(0, arrayListOf())
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if (jsonObject != null)
		{
			val aNotification = ArrayList<Notification>()
			jsonObject.optJSONObject("data")?.let {
				var totalItems = 0
				it.optJSONObject("pagination")?.let { joPagination ->
					totalItems = joPagination.optInt("total_items")
				}
				it.optJSONArray("items")?.let { joItems ->
					for (index in 0 until joItems.length())
					{
						val json = joItems.optJSONObject(index)
						val id = json.optString("id")
//						val dataAlertId = json.optString("data_alert_id")
						val title = json.optString("title")
						val message = json.optString("message")
						val dataReturnQuery = json.optString("data_return_query")
						val createdAt = json.optInt("created_at")
						val state = json.optString("state")

						val notification = Notification(
							id, title, message, dataReturnQuery, createdAt, state)
						aNotification.add(notification)
					}
				}
				view.showNotifications(totalItems, aNotification)
			}
		}
	}

	fun getNotifications(offset: Int = 0, limit: Int = 10)
	{
		val url = "$domainUrl/autoql/${api1}data-alerts/notifications?key=${apiKey}&offset=$offset&limit=$limit"
		val retryPolicy = DefaultRetryPolicy(
			DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
			0,
			DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
		callStringRequest(
			Request.Method.GET,
			url,
			typeJSON,
			headers = Authentication.getAuthorizationJWT(),
			retryPolicy = retryPolicy,
			listener = this)
	}
}