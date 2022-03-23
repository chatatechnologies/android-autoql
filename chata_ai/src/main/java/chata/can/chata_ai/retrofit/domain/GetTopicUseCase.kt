package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.TopicRepository

class GetTopicUseCase {
	private val repository = TopicRepository()

	suspend fun getTopics(key: String, projectId: String) = repository.getTopic(key, projectId)
}