package chata.can.chata_ai.model

import chata.can.chata_ai.pojo.chat.TypeDataQuery

fun isSupportPivot(aColumns: List<TypeDataQuery>): Boolean
{
	var support = false

	if (aColumns.size == 3)
	{
		val typeDate = TypeDataQuery.DATE
		val typeDollar = TypeDataQuery.DOLLAR_AMT
		val valid1 = aColumns[0] == typeDate || aColumns[1] == typeDate
		val valid2 = aColumns[1] == typeDollar || aColumns[2] == typeDollar
		support = valid1 && valid2
		if (support
//			&& aColumns.size == 3
		)
		{
			support = aColumns[2] == typeDollar
		}
	}

	return support
}