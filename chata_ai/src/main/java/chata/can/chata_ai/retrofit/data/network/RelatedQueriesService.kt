package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.core.RetrofitHelperDynamic
import chata.can.chata_ai.retrofit.data.model.relatedQueries.RelatedQueryModel
import chata.can.chata_ai.retrofit.data.model.relatedQueries.emptyRelatedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RelatedQueriesService {
	private val retrofit = RetrofitHelperDynamic.getRetrofit()

	suspend fun getRelatedQuery(
		search: String,
		pageSize: Int = 12,
		page: Int = 1
	): RelatedQueryModel {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(RelatedQueriesApiClient::class.java)
					.getRelatedQuery(
						beaverToken = "Bearer ${AutoQLData.JWT}",
						acceptLanguage = SinglentonDrawer.languageCode,
						apiKey = AutoQLData.apiKey,
						search = search,
						pageSize = pageSize,
						page = page
					)
				response.body() ?: emptyRelatedModel()
			} catch (ex: Exception) {
				emptyRelatedModel()
			}
		}
	}
}