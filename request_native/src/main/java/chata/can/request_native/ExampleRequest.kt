package chata.can.request_native

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.net.UnknownHostException

object ExampleRequest {
	fun initVolleyRequest(context: Context)
	{
		requestQueue = Volley.newRequestQueue(context)
	}
	//Start when chat or dashboard's component start in XMLs
	var requestQueue: RequestQueue?= null

	/**
	 * @since 0.1
	 * @author Carlos Buruel
	 */
	fun callStringRequest(
		methodRequest : Int,
		urlRequest : String
		//listener : StatusResponse
	)
	{
		val stringRequest = StringRequest(
			methodRequest,
			urlRequest,
			{
				requestQueue?.cancelAll(urlRequest)
			},
			{
				requestQueue?.cancelAll(urlRequest)
			})
		requestQueue?.add(stringRequest)
	}
}