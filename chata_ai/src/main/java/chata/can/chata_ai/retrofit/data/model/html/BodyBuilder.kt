package chata.can.chata_ai.retrofit.data.model.html

import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.webView.DataD3
import chata.can.chata_ai.retrofit.data.model.column.TypeColumn
import chata.can.chata_ai.retrofit.data.model.query.CaseQueryEntity
import chata.can.chata_ai.retrofit.data.model.query.QueryEntity

class BodyBuilder {
	private val aCount = arrayListOf(TypeColumn.DOLLAR_AMT, TypeDataQuery.QUANTITY)

	fun getHtml(queryEntity: QueryEntity) {
		val dataD3 = DataD3()
		OrderRow(queryEntity).setOrderRowByDate()

		val rows = queryEntity.rows
		val columns = queryEntity.columnsEntity
		val limitRow = queryEntity.limitRowNum

		val pairData = TableBuilder(rows, columns, limitRow).getTable()
		dataD3.table = pairData.first
		dataD3.rowsTable = pairData.second

		var posColumnX = 0
		var posColumnY = 1
		val aDataX: ArrayList<Int>
		val aDataY: ArrayList<Int>
		val aSecondary = ArrayList<Int>()
		val isTriConfig = false

		when(queryEntity.caseQueryEntity) {
			CaseQueryEntity.CASE_1 -> {}
			CaseQueryEntity.CASE_2 -> {}
			CaseQueryEntity.CASE_3 -> {}
			CaseQueryEntity.CASE_5 -> {}
			CaseQueryEntity.CASE_6 -> {}
		}
	}
}