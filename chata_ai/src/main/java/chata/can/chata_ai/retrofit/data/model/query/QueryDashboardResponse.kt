package chata.can.chata_ai.retrofit.data.model.query

import chata.can.chata_ai.retrofit.data.model.ColumnModel
import com.google.gson.annotations.SerializedName

class QueryDashboardResponse(
	@SerializedName("message")
	val message: String,
	@SerializedName("reference_id")
	val referenceId: String,
	@SerializedName("data")
	val data: QueryDashboardDataResponse
)

fun emptyQueryDashboardResponse() = QueryDashboardResponse(
	message = "",
	referenceId = "",
	data = QueryDashboardDataResponse()
)

class QueryDashboardDataResponse(
	@SerializedName("columns")
	val columns: List<ColumnModel> = listOf(),
	@SerializedName("rows")
	val rows: MutableList< List<String> > = mutableListOf(),
	@SerializedName("row_limit")
	val rowLimit: Int = 0,
	@SerializedName("limit_row_num")
	val limitRowNum: Int = 0,
	@SerializedName("display_type")
	val displayType: String = "",
	@SerializedName("query_id")
	val queryId: String = "",
	@SerializedName("text")
	val text: String = "",
	@SerializedName("interpretation")
	val interpretation: String = "",
	@SerializedName("sql")
	val sql: List<String> = listOf()
)