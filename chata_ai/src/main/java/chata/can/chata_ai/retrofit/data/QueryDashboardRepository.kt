package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.query.QueryDashboardResponse
import chata.can.chata_ai.retrofit.data.network.QueryDashboardService
import com.google.gson.JsonObject

class QueryDashboardRepository {
	private val api = QueryDashboardService()

	suspend fun getQueryDashboard(body: JsonObject): QueryDashboardResponse {
		return api.getQuery(body)
	}
}