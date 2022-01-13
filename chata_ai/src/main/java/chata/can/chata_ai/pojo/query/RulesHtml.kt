package chata.can.chata_ai.pojo.query

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery

class RulesHtml(
	private val aColumns: ArrayList<ColumnQuery>,
	private val countColumn: CountColumn,
	private val numRows: Int)
{
	fun getSupportCharts(): SupportCase
	{
		countColumn.clearData()
		getDifferentTypes(aColumns)
		var case: SupportCase ?= null

		countColumn.run {
			val columnSize = aColumns.size
			when
			{
				columnSize == 2 ->
				{
					//case 1; bar, line, column, pie; 1 series
					if (countGroupable == 1 && numberColumns() == 1)
					{
						case = if (numRows == 1)
							SupportCase.NO_CASE
						else
							SupportCase.CASE_1
					}
				}
				columnSize > 2 ->
				{
					if (columnSize == 3)
					{
						//Case 3; heat_map, bubble, stacked bar, stacked column
						if (countGroupable == 2 && numberColumns() == 1)
							case = SupportCase.CASE_3
					}
					else
					{
						//Case 5; bar, line, column, pie
						if (countString > 0 && numberColumns() > 0)
						{
							when
							{
								isUseOnlyNumber() -> case = SupportCase.CASE_5
								repeatNumber3() -> case = SupportCase.CASE_6
								else ->
								{
									if (countDate > 0 && countDollarAMT > 0)
										case = SupportCase.CASE_2
								}
							}
						}
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