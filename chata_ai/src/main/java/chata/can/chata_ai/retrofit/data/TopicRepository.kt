package chata.can.chata_ai.retrofit.data

import chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.QueryBuilderData
import chata.can.chata_ai.retrofit.core.seeMore
import chata.can.chata_ai.retrofit.data.model.ItemTopicResponse
import chata.can.chata_ai.retrofit.data.network.TopicService

class TopicRepository {
	private val api = TopicService()

	suspend fun getTopic(key: String, projectId: String): List<ItemTopicResponse> {
		val response = api.getTopics(key, projectId)
		//region update data for query builder
		val aMainData = QueryBuilderData.aMainData
		aMainData.clear()
		val mMainQuery = QueryBuilderData.mMainQuery
		mMainQuery.clear()

		val items = response.items ?: listOf()

		for (item in items) {
			val topic = item.topic
			aMainData.add(topic)
			val listQueries = arrayListOf(seeMore)
			listQueries.addAll(item.queries)
			mMainQuery[topic] = listQueries
		}
		//endregion
		return items
	}
}