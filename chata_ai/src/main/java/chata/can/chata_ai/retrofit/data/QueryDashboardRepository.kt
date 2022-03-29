package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.model.query.QueryDashboardResponse
import chata.can.chata_ai.retrofit.data.network.QueryDashboardService

class QueryDashboardRepository {
	private val api = QueryDashboardService()

	suspend fun getQueryDashboard(): QueryDashboardResponse {
		return api.getQuery()
	}
}