package chata.can.chata_ai_api.data.network

import chata.can.chata_ai.pojo.autoQL.AutoQLData.apiKey
import chata.can.chata_ai.pojo.autoQL.AutoQLData.domainUrl
import chata.can.chata_ai.pojo.autoQL.AutoQLData.projectId
import chata.can.chata_ai.pojo.dashboard.DashboardResponse
import chata.can.chata_ai.pojo.dashboard.emptyDashboardResponse
import chata.can.chata_ai.request.authentication.Authentication.bearerToken
import chata.can.chata_ai_api.core.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DashboardService {
	private val retrofit = RetrofitHelper.getRetrofit()

	suspend fun getDashboards(): DashboardResponse {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(DashboardApiClient::class.java)
					.getDashboards(
						beaverToken = bearerToken(),
						integratorDomain = domainUrl,
						key = apiKey,
						projectId = projectId
					)
				response.body() ?: emptyDashboardResponse()
			} catch (ex: Exception) {
				emptyDashboardResponse()
			}
		}
	}
}