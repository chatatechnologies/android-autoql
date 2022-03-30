package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.QueryDashboardRepository
import chata.can.chata_ai.retrofit.data.model.query.QueryEntity
import com.google.gson.JsonObject

class GetQueryDashboardUseCase {
	private val repository = QueryDashboardRepository()

	suspend fun getQueryDashboard(body: JsonObject): QueryEntity {
		return repository.getQueryDashboard(body)
	}
}