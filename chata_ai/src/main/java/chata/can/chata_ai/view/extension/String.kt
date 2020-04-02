package chata.can.chata_ai.view.extension

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import java.text.SimpleDateFormat
import java.util.*

//fun String.toIntNotNull() = this.toIntOrNull() ?: 0

fun String.toDoubleNotNull() = this.toDoubleOrNull() ?: 0.0

fun String.formatWithColumn(
	columnQuery: ColumnQuery,
	commaCharacter: String = ""
): String
{
	return when(columnQuery.type)
	{
		TypeDataQuery.DATE ->
		{
			val format = "MMM dd, yyyy"
			if (isEmpty() || this == "0")
				""
			else
			{
				val aTmp = split(".")
				aTmp.firstOrNull()?.toIntOrNull()?.let {
					val dateFormat = SimpleDateFormat(format, Locale.US)
					val date = Date(it * 1000L)
					dateFormat.format(date)
				} ?: run { "" }
			}
		}
		TypeDataQuery.DOLLAR_AMT ->
		{
			val tmp = toDoubleNotNull()
			tmp.formatDecimals(SinglentonDrawer.mCurrencyCode, commaCharacter = commaCharacter)
		}
		TypeDataQuery.QUANTITY -> "${toDoubleNotNull()}"
		TypeDataQuery.STRING -> this
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