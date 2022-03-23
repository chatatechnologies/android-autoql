package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.retrofit.data.network.TopicService

class TopicRepository {
	private val api = TopicService()

	suspend fun getTopic(key: String, projectId: String) {
		val response = api.getTopics(key, projectId)

	}
}