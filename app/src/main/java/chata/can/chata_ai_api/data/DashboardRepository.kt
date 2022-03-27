package chata.can.chata_ai_api.data

import chata.can.chata_ai.pojo.dashboard.DashboardItemResponse
import chata.can.chata_ai_api.data.network.DashboardService

class DashboardRepository {
	private val api = DashboardService()

	suspend fun getDashboards(): List<DashboardItemResponse> {
		val response = api.getDashboards()
		return response.items
	}
}