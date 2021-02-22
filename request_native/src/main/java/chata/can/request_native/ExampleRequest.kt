package chata.can.request_native

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

object ExampleRequest {
	fun callRequestSimple(
		context: Context,
		contentType : String = "",
		headers : HashMap<String, String>?= null,
		parameters : HashMap<String, String>?= null,
		parametersAny : HashMap<String, Any> ?= null,
		parameterArray: ArrayList<*> ?= null,
		infoHolder: HashMap<String, Any> ?= null)
	{
		val queue = Volley.newRequestQueue(context)
		val url = "https://spira-staging.chata.io/autoql/api/v1/query"

		val stringRequest = object: StringRequest(
			Method.POST,
			url,
			{
				queue.cancelAll(url)
				println(it?.toString())
			},
			{
				queue.cancelAll(url)
				println(it?.toString())
			}
		)
		{
			override fun getBodyContentType(): String
			{
				return if (contentType.isEmpty())
					super.getBodyContentType()
				else
					"application/json"
			}

			override fun getHeaders(): MutableMap<String, String>
			{
				return headers ?: HashMap()
			}


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

			override fun getParams(): MutableMap<String, String>
			{
				return if (contentType.isEmpty())
					parameters ?: HashMap()
				else
					super.getParams()
			}
		}

		stringRequest.tag = url
		stringRequest.retryPolicy = DefaultRetryPolicy(
			10000,
			3,//DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
			DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

		queue.add(stringRequest)
	}
}