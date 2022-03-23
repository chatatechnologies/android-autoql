package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.TopicRepository
import chata.can.chata_ai.retrofit.data.model.ItemTopicResponse

class GetTopicUseCase {
	private val repository = TopicRepository()

	suspend fun getTopics(
		key: String,
		projectId: String
	): List<ItemTopicResponse> = repository.getTopic(key, projectId)
}