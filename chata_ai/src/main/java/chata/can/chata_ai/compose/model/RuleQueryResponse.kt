package chata.can.chata_ai.compose.model

data class RuleQueryResponse(
	val query_result: QueryResult
)

data class QueryResult(
	val message: String,
	val reference_id: String,
	val data: QueryResultData?
)

data class QueryResultData(
	val columns: List<ColumnResponse> = listOf(),
	val display_type: String = "",
	val limit_row_num: Int = 0,
	val query_id: String = "",
	val row_limit: Int = 0,
	val rows: List<List<String>> = listOf()
)