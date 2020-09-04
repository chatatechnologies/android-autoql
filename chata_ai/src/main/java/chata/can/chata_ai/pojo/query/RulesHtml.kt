package chata.can.chata_ai.pojo.query

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery

object RulesHtml
{
	fun getSupportCharts(aColumns: ArrayList<ColumnQuery>): Int
	{
		val aTypes = getDifferentTypes(aColumns)
		val isNumber = isUniqueNumber(aTypes)

		return 0
	}

	//region internal methods
	fun getDifferentTypes(aColumns: ArrayList<ColumnQuery>): ArrayList<TypeDataQuery>
	{
		val aTypes = ArrayList<TypeDataQuery>()

		for (column in aColumns)
		{
			if (column.type !in aTypes)
			{
				aTypes.add(column.type)
			}
		}
		return aTypes
	}

	fun isUniqueNumber(aTypes: ArrayList<TypeDataQuery>): Boolean
	{
		//DOLLAR_AMT
		var countDollarAMT = 0
		//QUANTITY
		var countQuantity = 0
		//PERCENT
		var countPercent = 0

		for (type in aTypes)
		{
			when(type)
			{
				TypeDataQuery.DOLLAR_AMT -> countDollarAMT++
				TypeDataQuery.QUANTITY -> countQuantity++
				TypeDataQuery.PERCENT -> countPercent++
				else -> {}
			}
		}

		return false
	}
	//endregion
}