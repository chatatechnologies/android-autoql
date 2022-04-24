package chata.can.chata_ai.compose.repository

import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.model.RuleQueryResponse
import chata.can.chata_ai.compose.network.RuleQueryApi
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication
import javax.inject.Inject

class RuleQueryRepository @Inject constructor(private val api: RuleQueryApi) {
	private val dataOrException = DataOrException<RuleQueryResponse, Boolean, Exception>()

	suspend fun getRuleQuery(idRule: String): DataOrException<RuleQueryResponse, Boolean, Exception> {
		try {
			dataOrException.loading = true
			dataOrException.data = api.getRuleQuery(
				idRule = idRule,
				beaverToken = Authentication.bearerToken(),
				acceptLanguage = SinglentonDrawer.languageCode,
				integratorDomain = AutoQLData.domainUrl,
				key = AutoQLData.apiKey
			)
			dataOrException.loading = false
		} catch (exception: Exception) {
			dataOrException.e = exception
		}
		return dataOrException
	}
}