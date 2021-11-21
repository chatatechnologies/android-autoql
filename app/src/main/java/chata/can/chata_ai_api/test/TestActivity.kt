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

//		region request POST (login)
		val parameters = HashMap<String, Any>()
		parameters["username"] = "admin"
		parameters["password"] = "admin123"

		val requestData = RequestData(
			RequestMethod.POST,
			"https://backend-staging.chata.io/api/v1/login",
			parameters = parameters
		)
		//BaseRequest().getBaseRequest(requestData)
//		endregion
//		region request GET JWT
		val url1 = "https://backend-staging.chata.io/api/v1/jwt?" +
			"display_name=carlos@rinro.com.mx" +
			"&project_id=spira-demo3"
		val header1 = hashMapOf("Authorization" to "8e1b3ad8-d551-4bdf-8423-dcaf9d77d466")
		val requestData1 = RequestData(
			RequestMethod.GET,
			url1
		)
		BaseRequest().getBaseRequest(requestData1)
//		endregion

	}
}