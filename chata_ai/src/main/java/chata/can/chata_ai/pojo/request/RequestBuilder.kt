package chata.can.chata_ai.pojo.request

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.net.UnknownHostException

object RequestBuilder
{
	fun initVolleyRequest(context: Context)
	{
		requestQueue = Volley.newRequestQueue(context)
	}
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
		infoHolder: HashMap<String, Any> ?= null,
		retryPolicy: DefaultRetryPolicy ?= null,
		listener : StatusResponse)
	{
		fun addInfoHolder(json: JSONObject)
		{
			infoHolder?.let {
				if (it.size > 0)
				{
					for ((key, value) in it)
					{
						json.put(key, value)
					}
				}
			}
		}

		val stringRequest = object: StringRequest(
			methodRequest,
			urlRequest,
			{
				requestQueue?.cancelAll(urlRequest)

				try {
					val json = JSONObject(it)
					addInfoHolder(json)
					listener.onSuccess(json)
				}
				catch(ex: Exception)
				{
					try {
						val jsonArray = JSONArray(it)
						with(JSONObject())
						{
							addInfoHolder(this)
							if (length() > 0)
							{
								put("array", jsonArray)
								listener.onSuccess(this)
							}
							else
							{
								listener.onSuccess(jsonArray = jsonArray)
							}
						}
					}
					catch (ex: Exception)
					{
						with(JSONObject())
						{
							addInfoHolder(this)
							put("RESPONSE", it ?: "")
							listener.onSuccess(this)
						}
					}
				}
			},
			{
				requestQueue?.cancelAll(urlRequest)
				val json = JSONObject()
				if (it is UnknownHostException)
				{
					json.put("CODE", 500)
					json.put("CONTENT", "UnknownHostException")
				}
				else
				{
					if (it.networkResponse == null)
					{
						json.put("CODE", 500)
						json.put("CONTENT", "networkResponse is empty")
					}
					else
					{
						val statusCode = it.networkResponse.statusCode
						val aResponse = it.networkResponse.data ?: byteArrayOf(0)
						val response = String(aResponse)
						/**
						 * DEFINE MESSAGE IN @param json
						 */
						json.put("CODE", statusCode)
						json.put("RESPONSE", response)
					}
				}
				addInfoHolder(json)
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

			override fun getParams(): MutableMap<String, String>?
			{
				return if (contentType.isEmpty())
					parameters ?: HashMap()
				else
					super.getParams()
			}
		}

		stringRequest.tag = urlRequest
		stringRequest.retryPolicy = retryPolicy ?: DefaultRetryPolicy(
			10000,
			3,//DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
			DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
		requestQueue?.add(stringRequest)
	}
}