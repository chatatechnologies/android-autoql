package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.pojo.SinglentonDrawer.languageCode
import chata.can.chata_ai.pojo.autoQL.AutoQLData.apiKey
import chata.can.chata_ai.request.authentication.Authentication.bearerToken
import chata.can.chata_ai.retrofit.core.RetrofitHelperDynamic
import chata.can.chata_ai.retrofit.data.model.query.QueryDashboardResponse
import chata.can.chata_ai.retrofit.data.model.query.emptyQueryDashboardResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QueryDashboardService {
	private val retrofit = RetrofitHelperDynamic.getRetrofit()

	suspend fun getQuery(body: JsonObject): QueryDashboardResponse {
//		val bodyTest = JsonObject()
//		bodyTest.addProperty("text", "Total revenue this year")
//		bodyTest.addProperty("test", true)
//		bodyTest.addProperty("source", "data_messenger.user")
//		bodyTest.addProperty("translation", "include")

		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(QueryDashboardApiClient::class.java)
					.queryDashboard(
						beaverToken = bearerToken(),
						acceptLanguage = languageCode,
						contentType = "application/json",
						key = apiKey,
						post = body
					)
				response.body() ?: emptyQueryDashboardResponse()
			} catch (ex: Exception) {
				emptyQueryDashboardResponse()
			}
		}
	}
}