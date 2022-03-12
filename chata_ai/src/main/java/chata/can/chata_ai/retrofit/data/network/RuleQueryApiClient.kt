package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.retrofit.data.model.RuleQueryResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RuleQueryApiClient {
	//url should variable
	@GET("rule-notifications/nt_NGI3NDNiZjgtZjRmMS00MzI2LWIyYjItM2JkNDg1NTcxOTI4")
	suspend fun getRuleQuery(
		@Header("Authorization") beaverToken: String,
		@Header("accept-language") acceptLanguage: String,
		@Header("acceptLanguage") integratorDomain: String,
		@Query("key") key: String
	): Response<RuleQueryResponseModel>
}