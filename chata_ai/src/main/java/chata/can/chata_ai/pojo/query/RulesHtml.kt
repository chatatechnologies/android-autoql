package chata.can.chata_ai.pojo.query

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object RulesHtml
{
	var countDollarAMT = 0
	var countQuantity = 0
	var countPercent = 0
	var countNumber = 0
	var countDate = 0
	var countDateString = 0
	var countString = 0

	fun getSupportCharts(aColumns: ArrayList<ColumnQuery>): Int
	{
		init()
		getDifferentTypes(aColumns)
		val case: SupportCase

		when
		{
			aColumns.size > 2 ->
			{
				//Case 5; bar, line, column, pie
				if (countString > 0 && numberColumns() > 0 && isUseOnlyNumber())
				{
					case = SupportCase.CASE_5
				}
			}
		}
		return 0
	}

	//region internal methods
	private fun init()
	{
		countDollarAMT = 0
		countQuantity = 0
		countPercent = 0
		countNumber = 0
		countDate = 0
		countDateString = 0
		countString = 0
	}

	private fun getDifferentTypes(aColumns: ArrayList<ColumnQuery>)
	{
		for (column in aColumns)
		{
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
		}
	}

	private fun numberColumns() = countDollarAMT + countQuantity + countPercent + countNumber

	private fun isUseOnlyNumber(): Boolean
	{
		val isDollar = countDollarAMT > 0 && countQuantity == 0 && countPercent == 0
		val isQuality = countDollarAMT == 0 && countQuantity > 0 && countPercent == 0
		val isPercent = countDollarAMT == 0 && countQuantity == 0 && countPercent > 0
		return isDollar ||isQuality || isPercent
	}
	//endregion
}