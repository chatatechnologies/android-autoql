package chata.can.request_native

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

object ExampleRequest {
	fun callRequestSimple(context: Context)
	{
		val queue = Volley.newRequestQueue(context)
		val url = "https://www.google.com"

		val stringRequest = StringRequest(
			Request.Method.GET,
			url,
			{
				println(it?.toString())
			},
			{
				println(it?.toString())
			}
		)
		queue.add(stringRequest)
	}
}