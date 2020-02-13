package chata.can.chata_ai.pojo.request

import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONObject
import java.net.UnknownHostException

object RequestBuilder
{
	//Start when chat or dashboard's component start in XMLs
	var requestQueue: RequestQueue ?= null

	/**
	 * @since 0.1
	 * @author Carlos Buruel
	 */
	fun callStringRequest(
		methodRequest : Int,
		urlRequest : String,
		contentType : String = "",
		headers : HashMap<String, String>?= null,
		parameters : HashMap<String, String>?= null,
		parametersAny : HashMap<String, Any> ?= null,
		parameterArray: ArrayList<*> ?= null,
		listener : StatusResponse)
	{
		val stringRequest = object: StringRequest(
			methodRequest,
			urlRequest,
			Response.Listener
			{
				requestQueue?.cancelAll(urlRequest)

				try {
					val json = JSONObject(it)
					listener.onSuccess(json)
				}
				catch(ex: Exception)
				{
					try {
						val jsonArray = JSONArray(it)
						listener.onSuccess(jsonArray = jsonArray)
					}
					catch (ex: Exception)
					{
						listener.onSuccess(JSONObject().put("RESPONSE", it ?: ""))
					}

				}
			},
			Response.ErrorListener
			{
				requestQueue?.cancelAll(urlRequest)
				val json = JSONObject()
				if (it is UnknownHostException)
				{
					json.put("CODE", 500)
				}
				else
				{
					if (it.networkResponse == null)
					{
						json.put("CODE", 500)
					}
					else
					{
						val statusCode = it.networkResponse.statusCode
						/**
						 * DEFINE MESSAGE IN @param json
						 */
						json.put("CODE", statusCode)
					}
				}
				listener.onFailure(json)
			})
		{
			override fun getBody(): ByteArray
			{
				return if (contentType.isEmpty())
				{
					super.getBody()
				}
				else
				{
					if (parameterArray != null)
					{
						JSONArray(parameterArray).toString().toByteArray()
					}
					else
					{
						val params = parameters ?: parametersAny ?: HashMap()
						val map = params.map {(key, value) ->
							key to value
						}.toMap()

						JSONObject(map).toString().toByteArray()
					}
				}
			}

			override fun getBodyContentType(): String
			{
				return if (contentType.isEmpty())
					super.getBodyContentType()
				else
					contentType
			}

			override fun getHeaders(): MutableMap<String, String>
			{
				return headers ?: HashMap()
			}

			override fun getParams(): MutableMap<String, String>
			{
				return if (contentType.isEmpty())
					parameters ?: HashMap()
				else
					super.getParams()
			}
		}

		stringRequest.tag = urlRequest
		stringRequest.retryPolicy = DefaultRetryPolicy(
			10000,
			DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
			DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
		requestQueue?.add(stringRequest)
	}
}