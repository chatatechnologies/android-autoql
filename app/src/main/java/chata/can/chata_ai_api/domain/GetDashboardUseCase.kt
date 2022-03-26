package chata.can.chata_ai_api.domain

import chata.can.chata_ai_api.data.DashboardRepository
import chata.can.chata_ai_api.data.model.DashboardItemResponse

class GetDashboardUseCase {
	private val repository = DashboardRepository()

	suspend fun getDashboards(): List<DashboardItemResponse> = repository.getDashboards()
}