package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.core.RetrofitHelper
import chata.can.chata_ai.retrofit.data.model.ruleQuery.RuleQueryResponseModel
import chata.can.chata_ai.retrofit.data.model.ruleQuery.emptyRuleQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RuleQueryService {
	private val retrofit = RetrofitHelper.getRetrofit()

	suspend fun getRuleQuery(idRule: String): RuleQueryResponseModel {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(RuleQueryApiClient::class.java)
					.getRuleQuery(
						idRule = idRule,
						beaverToken = "Bearer ${AutoQLData.JWT}",
						acceptLanguage = SinglentonDrawer.languageCode,
						integratorDomain = AutoQLData.domainUrl,
						key = AutoQLData.apiKey
					)
				response.body() ?: emptyRuleQuery()
			} catch (ex: Exception) {
				emptyRuleQuery()
			}
		}
	}
}