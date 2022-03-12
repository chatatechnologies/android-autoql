package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.ruleQuery.RuleQueryResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RuleQueryApiClient {
	//url should variable - idRule
	@GET("rule-notifications/{id_rule}")
	suspend fun getRuleQuery(
		@Path(value = "id_rule") idRule: String,
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Header("acceptLanguage") integratorDomain: String,
		@Query("key") key: String
	): Response<RuleQueryResponseModel>
}