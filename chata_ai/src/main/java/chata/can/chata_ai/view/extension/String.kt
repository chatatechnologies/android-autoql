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

fun String.toCapitalColumn(): String
{
	var tmp = this
	if (tmp.contains("___"))
	{
		tmp = tmp.replace("___", " (") + ")"
	}
	tmp = tmp.replace("_"," ")

	val aSequence = tmp.subSequence(0,1)
	return if (aSequence.isNotEmpty())
	{
		val upperCase = aSequence[0].toUpperCase()
		val sb = StringBuilder("$upperCase")

		for(index in 1 until tmp.length)
		{
			sb.append(
				if(!tmp[index-1].isLetterOrDigit())
					tmp[index].toUpperCase()
				else
					tmp[index])
		}
		return sb.toString()
	}
	else ""
}