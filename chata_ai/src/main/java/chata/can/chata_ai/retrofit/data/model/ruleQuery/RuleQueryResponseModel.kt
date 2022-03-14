package chata.can.chata_ai.retrofit.data.model.ruleQuery

import chata.can.chata_ai.retrofit.data.model.ColumnModel
import com.google.gson.annotations.SerializedName

//region RuleQueryResponseModel
data class RuleQueryResponseModel(
	@SerializedName("query_result")
	val queryResult: QueryResult
)

fun emptyRuleQuery() = RuleQueryResponseModel(
	QueryResult(
		message = "",
		referenceId = "",
		emptyQueryResultData()
	)
)
//endregion

//region QueryResult
data class QueryResult(
	@SerializedName("message")
	val message: String,
	@SerializedName("reference_id")
	val referenceId: String,
	@SerializedName("data")
	val data: QueryResultData
)
//endregion

//region query result data
data class QueryResultData(
	@SerializedName("columns")
	val columns: List<ColumnModel> = listOf(),
	@SerializedName("display_type")
	val displayType: String = "",
	@SerializedName("limit_row_num")
	val limitRowNum: Int = 0,
	@SerializedName("query_id")
	val queryId: String = "",
	@SerializedName("row_limit")
	val rowLimit: Int = 0,
	@SerializedName("rows")
	val rows: List< List<String> > = listOf()
)

fun emptyQueryResultData() = QueryResultData(
	columns = listOf(),
	displayType = "",
	limitRowNum = 0,
	queryId = "",
	rowLimit = 0,
	rows = listOf()
)
//endregion