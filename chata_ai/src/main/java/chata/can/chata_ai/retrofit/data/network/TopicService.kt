package chata.can.chata_ai.retrofit.data.network

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.authentication.Authentication.bearerToken
import chata.can.chata_ai.retrofit.core.RetrofitHelper
import chata.can.chata_ai.retrofit.data.model.TopicResponse
import chata.can.chata_ai.retrofit.data.model.emptyTopicResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TopicService {
	private val retrofit = RetrofitHelper.getRetrofit()

	suspend fun getTopics(key: String, projectId: String): TopicResponse {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(TopicApiClient::class.java)
					.getTopics(
						beaverToken = bearerToken(),
						acceptLanguage = SinglentonDrawer.languageCode,
						integratorDomain = AutoQLData.domainUrl,
						key = key,
						projectId = projectId
					)
				response.body() ?: emptyTopicResponse()
			} catch (ex: Exception) {
				emptyTopicResponse()
			}
		}
	}
}