package chata.can.chata_ai.retrofit.data.model.query

import chata.can.chata_ai.retrofit.ColumnEntity
import chata.can.chata_ai.retrofit.data.model.column.TypeColumn

class RulesQueryEntity(
	private val columnsEntity: List<ColumnEntity>,
	private val numRows: Int
) {
	private val manageColumnEntity = ManageColumnEntity()

	fun getSupportChart(): CaseQueryEntity {
		manageColumnEntity.resetData()
		getDifferentTypes()
		var caseQueryEntity: CaseQueryEntity = CaseQueryEntity.NO_CASE

		manageColumnEntity.run {
			val columnEntitySize = columnsEntity.size
			when {
				columnEntitySize == 2 -> {
					//case 1; bar, line, column, pie; 1 series
					if (countGroup == 1 && numberColumns() == 1) {
						caseQueryEntity = if (numRows == 1) CaseQueryEntity.NO_CASE
						else CaseQueryEntity.CASE_1
					}
				}
				columnEntitySize > 2 -> {
					if (columnEntitySize == 3) {
						//Case 3; heat_map, bubble, stacked bar, stacked column
						if (countGroup == 2 && numberColumns() == 1) {
							caseQueryEntity = CaseQueryEntity.CASE_3
						}
					}
					else {
						//Case 5; bar, line, column, pie
						if (countString > 0 && numberColumns() > 0){
							when {
								isUseOnlyNumber() -> caseQueryEntity = CaseQueryEntity.CASE_5
								repeat3Numbers() -> caseQueryEntity = CaseQueryEntity.CASE_6
								else -> {
									if (countDate > 0 && countDollarAMT > 0)
										caseQueryEntity = CaseQueryEntity.CASE_2
								}
							}
						}
					}
				}
			}
		}
		return caseQueryEntity
	}

	private fun getDifferentTypes() {
		manageColumnEntity.run {
			columnsEntity.forEach { columnEntity: ColumnEntity ->
				columnEntity.typeColumn
				when (columnEntity.typeColumn) {
					TypeColumn.DATE -> countDate++
					TypeColumn.DATE_STRING -> countDateString++
					TypeColumn.DOLLAR_AMT -> countDollarAMT++
					TypeColumn.PERCENT -> countPercent++
					TypeColumn.QUANTITY -> countQuantity++
					else -> countString++
				}

				if (columnEntity.groupable) countGroup++
			}
		}
	}
}