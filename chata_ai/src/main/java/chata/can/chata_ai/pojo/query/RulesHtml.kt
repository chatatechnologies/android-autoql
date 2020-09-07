package chata.can.chata_ai.pojo.query

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery

class RulesHtml(
	private val aColumns: ArrayList<ColumnQuery>,
	private val countColumn: CountColumn)
{
	fun getSupportCharts(): SupportCase
	{
		countColumn.clearData()
		getDifferentTypes(aColumns)
		var case: SupportCase ?= null

		countColumn.run {
			when
			{
				aColumns.size == 2 ->
				{
					//case 1; bar, line, column, pie; 1 series
					if (countGroupable == 1 && numberColumns() == 1)
					{

						case = SupportCase.CASE_1
					}
				}
				aColumns.size > 2 ->
				{
					//Case 5; bar, line, column, pie
					if (countString > 0 && numberColumns() > 0 && isUseOnlyNumber())
					{
						case = SupportCase.CASE_5
					}
				}
			}
		}
		return case ?: SupportCase.NO_CASE
	}

	//region internal methods
	private fun getDifferentTypes(aColumns: ArrayList<ColumnQuery>)
	{
		countColumn.run {
			for (column in aColumns)
			{
				//region column types
				when(column.type)
				{
					TypeDataQuery.DOLLAR_AMT -> countDollarAMT++
					TypeDataQuery.QUANTITY -> countQuantity++
					TypeDataQuery.PERCENT -> countPercent++
					TypeDataQuery.NUMBER -> countNumber++
					TypeDataQuery.DATE -> countDate++
					TypeDataQuery.DATE_STRING -> countDateString++
					TypeDataQuery.STRING -> countString++
					else -> {}
				}
				//endregion

				if (column.isGroupable)
				{
					countGroupable++
				}
			}
		}
	}
	//endregion
}