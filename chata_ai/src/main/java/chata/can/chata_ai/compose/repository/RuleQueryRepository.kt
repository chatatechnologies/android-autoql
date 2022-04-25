package chata.can.chata_ai.compose.repository

import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.di.AppModule.provideRetrofit
import chata.can.chata_ai.compose.model.RuleQueryResponse
import chata.can.chata_ai.compose.network.RuleQueryApi
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RuleQueryRepository {
	private val dataOrException = DataOrException<RuleQueryResponse, Boolean, Exception>()

	suspend fun getRuleQuery(idRule: String): DataOrException<RuleQueryResponse, Boolean, Exception> {
		return withContext(Dispatchers.IO) {
			try {
				dataOrException.loading = true
				dataOrException.data = provideRetrofit().create(RuleQueryApi::class.java)
					.getRuleQuery(
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
			dataOrException
		}
	}
}