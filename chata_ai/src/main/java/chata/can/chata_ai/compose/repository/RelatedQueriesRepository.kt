package chata.can.chata_ai.compose.repository

import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.model.RelatedQueriesResponse
import chata.can.chata_ai.compose.network.RelatedQueriesApi
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication
import javax.inject.Inject

class RelatedQueriesRepository @Inject constructor(private val api: RelatedQueriesApi) {
	private val dataOrException = DataOrException<RelatedQueriesResponse, Boolean, Exception>()

	suspend fun getRelatedQueries(
		search: String,
		pageSize: Int,
		page: Int
	): DataOrException<RelatedQueriesResponse, Boolean, Exception> {
		try {
			dataOrException.loading = true
			dataOrException.data = api.getRelatedQuery(
				beaverToken = Authentication.bearerToken(),
				acceptLanguage = SinglentonDrawer.languageCode,
				apiKey = AutoQLData.apiKey,
				search = search,
				pageSize = pageSize,
				page = page
			)
			dataOrException.loading = false
		} catch (exception: Exception) {
			dataOrException.e = exception
		}
		return dataOrException
	}
}