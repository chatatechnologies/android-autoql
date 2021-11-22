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
//		region request GET (JWT)
		val url1 = "https://backend-staging.chata.io/api/v1/jwt?" +
			"display_name=carlos@rinro.com.mx" +
			"&project_id=spira-demo3"
		val header1 = hashMapOf("Authorization" to "8e1b3ad8-d551-4bdf-8423-dcaf9d77d466")
		val requestData1 = RequestData(
			RequestMethod.GET,
			url1,
			header1
		)
//		BaseRequest().getBaseRequest(requestData1)
//		endregion
//		region request GET (related query)
		val url2 = "https://spira-staging.chata.io/autoql/api/v1/query/related-queries?key=AIzaSyBxmGxl9J9siXz--dS-oY3-5XRSFKt_eVo&search=test"
		val jwt = "eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJSUzI1NiIsICJraWQiOiAiNzUxZmYzY2YxMjA2ZGUwODJhNzM1MjY5OTI2ZDg0NTgzYjcyOTZmNCJ9.eyJpYXQiOiAxNjM3NTM5Mzc0LCAiZXhwIjogMTYzNzU2MDk3NCwgImlzcyI6ICJkZW1vMy1qd3RhY2NvdW50QHN0YWdpbmctMjQ1NTE0LmlhbS5nc2VydmljZWFjY291bnQuY29tIiwgImF1ZCI6ICJkZW1vMy1zdGFnaW5nLmNoYXRhLmlvIiwgInN1YiI6ICJkZW1vMy1qd3RhY2NvdW50QHN0YWdpbmctMjQ1NTE0LmlhbS5nc2VydmljZWFjY291bnQuY29tIiwgImVtYWlsIjogImRlbW8zLWp3dGFjY291bnRAc3RhZ2luZy0yNDU1MTQuaWFtLmdzZXJ2aWNlYWNjb3VudC5jb20iLCAicHJvamVjdF9pZCI6ICJzcGlyYS1kZW1vMyIsICJ1c2VyX2lkIjogImNhcmxvc0ByaW5yby5jb20ubXgiLCAiZGlzcGxheV9uYW1lIjogImNhcmxvc0ByaW5yby5jb20ubXgiLCAicmVzb3VyY2VfYWNjZXNzIjogWyIvYXV0b3FsL2FwaS92MS9kYXRhLWFsZXJ0cy8qKiIsICIvYXV0b3FsL2FwaS92MS90aGVtZXMiLCAiL2F1dG9xbC9hcGkvdjEvcnVsZXMvKioiLCAiL2F1dG9xbC9hcGkvdjIvcXVlcnkiLCAiL2F1dG9xbC9hcGkvdjEvcnVsZXMiLCAiL2F1dG9xbC9hcGkvdjEvcXVlcnkiLCAiL2F1dG9xbC9hcGkvdjEvcXVlcnkvKioiLCAiL2F1dG9xbC9hcGkvdjEvZGF0YS1hbGVydHMiLCAiL2F1dG9xbC9hcGkvdjEvbm90aWZpY2F0aW9ucy8qKiJdfQ.phPghEgIwZ5L1ctI_21UtFmEDpDuPxmtUIAYCTA3uc2ya9wdHn3qyQAdkfV5PunA7IuT2eKb_fgAOpeEpbuW59hoO1LacsRwFrEplSjqMj0cEtxFPM2IpffSn0aIWggXN65uylXKkeJhn97i8hG42G104EqeI3A6oKDTD9yh79-xn8tx06BhgjGmXESgH0PFzXF6q_aQKnRI6paAJHvWH_Ek1sV6eCaXg-gjPtcrXH_-qRM-as438UuM0dcR3tqcZYH50YHV2XaqyItlSnzurSdtt-o-nzD56fCjraew2pIeKFwxAI7k72Q2sJqB1hCVyBd4tgysTw0A2qkB8OJQww"
		val header2 = hashMapOf(
			"Authorization" to "Bearer $jwt",
			"accept-language" to "es-MX"
		)
		val requestData2 = RequestData(
			RequestMethod.GET,
			url2,
			header2
		)
//		BaseRequest().getBaseRequest(requestData2)
//		endregion

		val url3 = "https://backend-staging.chata.io/api/v1/topics?key=AIzaSyBxmGxl9J9siXz--dS-oY3-5XRSFKt_eVo&project_id=spira-demo3"
		val header3 = hashMapOf(
			"Authorization" to "Bearer $jwt",
			"accept-language" to "es-US",
			"Integrator-Domain" to "https://spira-staging.chata.io"
		)
		val requestData3 = RequestData(
			RequestMethod.GET,
			url3,
			header3
		)
//		BaseRequest().getBaseRequest(requestData3)
		val url4 = "https://spira-staging.chata.io/autoql/api/v1/query/column-visibility?key=AIzaSyBxmGxl9J9siXz--dS-oY3-5XRSFKt_eVo"
		val header4 = hashMapOf(
			"Authorization" to "Bearer $jwt",
			"accept-language" to "es-US",
			//for application/json
			"Content-Type" to "application/json"
		)
		//region columns
		val mParams = hashMapOf<String, Any>()
		val aColumns = ArrayList< HashMap<String, Any> >()

		val mColumn1 = hashMapOf<String, Any>(
			"name" to "format(t_TicketHeader.th_TicketDate, 'yyyy-MM')",
			"is_visible" to false)
		aColumns.add(mColumn1)
		val mColumn2 = hashMapOf<String, Any>(
			"name" to "sum(coalesce(t_TicketDetail.td_OrderAmount, 0))",
			"is_visible" to true
		)
		aColumns.add(mColumn2)
		mParams["columns"] = aColumns

		//endregion
		val requestData4 = RequestData(
			RequestMethod.PUT,
			url4,
			header4,
			mParams
		)
		BaseRequest().getBaseRequest(requestData4)
	}
}