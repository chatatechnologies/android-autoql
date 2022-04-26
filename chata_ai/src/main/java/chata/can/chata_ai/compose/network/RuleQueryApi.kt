package chata.can.chata_ai.compose.network

import chata.can.chata_ai.compose.model.RuleQueryResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RuleQueryApi {
	@GET("rule-notifications/{id_rule}")
	suspend fun getRuleQuery(
		@Path(value = "id_rule") idRule: String,
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Header("integrator-domain") integratorDomain: String,
		@Query("key") key: String
	): RuleQueryResponse
}