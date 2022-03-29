package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.QueryDashboardRepository
import chata.can.chata_ai.retrofit.data.model.query.QueryDashboardResponse

class GetQueryDashboardUseCase {
	private val repository = QueryDashboardRepository()

	suspend fun getQueryDashboard(): QueryDashboardResponse {
		return repository.getQueryDashboard()
	}
}