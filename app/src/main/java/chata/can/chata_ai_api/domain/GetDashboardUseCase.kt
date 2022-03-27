package chata.can.chata_ai_api.domain

import chata.can.chata_ai.pojo.dashboard.DashboardItemResponse
import chata.can.chata_ai_api.data.DashboardRepository

class GetDashboardUseCase {
	private val repository = DashboardRepository()

	suspend fun getDashboards(): List<DashboardItemResponse> = repository.getDashboards()
}