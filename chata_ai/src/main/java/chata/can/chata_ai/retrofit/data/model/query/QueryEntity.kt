package chata.can.chata_ai.retrofit.data.model.query

import chata.can.chata_ai.pojo.webView.D3OnHtml
import chata.can.chata_ai.retrofit.core.formatValue
import chata.can.chata_ai.retrofit.core.keySuggestion
import chata.can.chata_ai.retrofit.data.model.ColumnModel
import chata.can.chata_ai.retrofit.data.model.html.BodyBuilder
import chata.can.chata_ai.retrofit.toColumnEntity

fun QueryDashboardResponse.queryResponseDataToQueryEntity(): QueryEntity {
	data.run {
		return QueryEntity(
			message = message,
			columns = columns,
			rows = rows,
			rowLimit = rowLimit,
			limitRowNum = limitRowNum,
			displayType = displayType,
			queryId = queryId,
			text = text,
			interpretation = interpretation,
			sql = sql
		)
	}
}

class QueryEntity(
	val message: String,
	val columns: List<ColumnModel>,
	val rows: MutableList< List<String> >,
	val rowLimit: Int,
	val limitRowNum: Int,
	val displayType: String,
	val queryId: String,
	val text: String,
	val interpretation: String,
	val sql: List<String>
) {
	val columnsEntity = columns.map { it.toColumnEntity() }
	lateinit var caseQueryEntity: CaseQueryEntity
	var configActions = 0
	val aIndex = ArrayList<Int>()
	lateinit var aXAxis: ArrayList<String>
	lateinit var aXDrillDown: ArrayList<String>

	private fun isSimpleText(): Boolean {
		val sizeLevel1 = rows.size
		return rows.firstOrNull()?.let {
			val sizeLevel2 = it.size
			return sizeLevel1 == 1 && sizeLevel2 == 1
		} ?: run { false }
	}

	private fun getSimpleText(): String {
		return rows.firstOrNull()?.let {
			it.firstOrNull() ?: run { "" }
		} ?: run { "" }
	}

	fun addIndices(indexA: Int, indexB: Int) {
		if (indexA != -1 && indexB != -1) {
			aIndex.add(indexA)
			aIndex.add(indexB)
		}
	}

	fun getContentDisplay(): String {
		return when {
			message == "No Data Found" -> {
				message
			}
			rows.isEmpty() || isSimpleText() || displayType == keySuggestion -> {
				columnsEntity.firstOrNull()?.let { column ->
					getSimpleText().formatValue(column)
				} ?: run { "" }
			}
			else -> {
				caseQueryEntity = RulesQueryEntity(columnsEntity, rows.size).getSupportChart()
				val dataD3 = BodyBuilder().getHtml(this)
				D3OnHtml.getHtmlTest(dataD3)
			}
		}
	}
























}