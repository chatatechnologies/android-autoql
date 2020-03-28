package chata.can.chata_ai.view.extension

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery

fun String.toDoubleNotNull() = this.toDoubleOrNull() ?: 0.0

fun String.formatWithColumn(
	columnQuery: ColumnQuery,
	currencySymbol: String = "",
	commaCharacter: String = ""
): String
{
	return when(columnQuery.type)
	{
		TypeDataQuery.DOLLAR_AMT ->
		{
			val tmp = toDoubleNotNull()
			tmp.formatDecimals(currencySymbol, commaCharacter = commaCharacter)
		}
		else -> ""
	}
}