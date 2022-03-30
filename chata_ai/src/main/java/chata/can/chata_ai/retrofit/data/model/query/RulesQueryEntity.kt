package chata.can.chata_ai.retrofit.data.model.query

import chata.can.chata_ai.retrofit.ColumnEntity
import chata.can.chata_ai.retrofit.data.model.column.TypeColumn

class RulesQueryEntity(
	private val columnsEntity: List<ColumnEntity>,
	private val numRows: Int
) {
	private val manageColumnEntity = ManageColumnEntity()


	fun getSupportChart() {
		manageColumnEntity.resetData()
		getDifferentTypes()

		manageColumnEntity.run {
			val columnEntitySize = columnsEntity.size
			when {
				columnEntitySize == 2 -> {}
				columnEntitySize > 2 -> {}
			}
		}
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