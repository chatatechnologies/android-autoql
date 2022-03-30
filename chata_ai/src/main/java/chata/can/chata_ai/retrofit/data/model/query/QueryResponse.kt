package chata.can.chata_ai.retrofit.data.model.query

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object QueryResponse {
	suspend fun getQueryResponse(queryEntity: QueryEntity): String {
		return withContext(Dispatchers.IO) {
			queryEntity.getContentDisplay()
		}
	}
}