package chata.can.chata_ai_api.data.network

import chata.can.chata_ai_api.data.model.DashboardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DashboardApiClient {
	@GET("dashboards")
	fun getDashboards(
		@Header("Authorization") beaverToken: String,
		@Query("key") key: String,
		@Query("project_id") projectId: String
	): Response<DashboardResponse>
}