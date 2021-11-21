package chata.can.chata_ai_api.test

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import chata.can.request_native.BaseRequest
import chata.can.request_native.RequestData
import chata.can.request_native.RequestMethod

class TestActivity: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		setContentView(TextView(this).apply {
			text = "Test Activity"
		})

		val parameters = HashMap<String, Any>()
		parameters["username"] = "admin"
		parameters["password"] = "admin123"

		val requestData = RequestData(
			RequestMethod.GET,
			"https://backend-staging.chata.io/api/v1/login",
			parameters
		)
		BaseRequest().getBaseRequest(requestData)
	}
}