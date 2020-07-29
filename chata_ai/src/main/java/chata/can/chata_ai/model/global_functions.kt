package chata.can.chata_ai.model

import chata.can.chata_ai.pojo.chat.TypeDataQuery

fun supportPivot(aColumns: ArrayList<TypeDataQuery>): Boolean
{
	var support = false
	if (aColumns.size in 2..3)
	{
		val valid1 = aColumns[0] == TypeDataQuery.DATE
		val valid2 = aColumns[1] == TypeDataQuery.DOLLAR_AMT
		support = valid1 && valid2
		if (support && aColumns.size == 3)
		{
			support = aColumns[2] == TypeDataQuery.DOLLAR_AMT
		}
	}

	return support
}