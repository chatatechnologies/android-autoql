package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.query.QueryDashboardResponse
import retrofit2.Response
import retrofit2.http.POST

interface QueryDashboardApiClient {
	@POST("")
	suspend fun queryDashboard(

	): Response<QueryDashboardResponse>
}