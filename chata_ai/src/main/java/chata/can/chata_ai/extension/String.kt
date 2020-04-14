package chata.can.chata_ai.extension

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

//fun String.toIntNotNull() = this.toIntOrNull() ?: 0
fun String.toLongNotNull() = this.toLongOrNull() ?: 0L

fun String.toDoubleNotNull() = this.toDoubleOrNull() ?: 0.0

fun String.formatWithColumn(
	columnQuery: ColumnQuery,
	currencySymbol: String = SinglentonDrawer.currencyCode,
	commaCharacter: String = ","
): String
{
	return when(columnQuery.type)
	{
		TypeDataQuery.DATE ->
		{
			val format =
			if (columnQuery.name.contains("month"))
				SinglentonDrawer.monthYearFormat.replace("Y", "y")
			else
				SinglentonDrawer.dayMonthYearFormat.
				replace("Y", "y").
				replace("D", "d")

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
			tmp.formatSymbolDecimals(
				currencySymbol,
				commaCharacter = commaCharacter)
		}
		TypeDataQuery.QUANTITY ->
		{
			val tmp = toDoubleNotNull()
			tmp.formatDecimals(SinglentonDrawer.quantityDecimals)
		}
		TypeDataQuery.PERCENT ->
		{
			val double = toDoubleNotNull() * 100
			//region check later
			val classColor = when
			{
				double > 0 -> "green"
				double < 0 -> "red"
				else -> ""
			}
			//endregion
			val doubleString = double.formatSymbolDecimals(suffix = "%")
			"<span class='$classColor'>$doubleString</span>"
		}
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

fun String.isColor(): Pair<String, Boolean>
{
	val newColor = toLowerCase(Locale.US)
	val colorPattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})")

	return Pair(newColor, colorPattern.matcher(newColor).matches())
}

val aMonths = arrayListOf("January", "February", "March", "April", "May", "June",
	"July", "August", "September", "October", "November", "December")
fun String.toConvertMonth(): String
{
	val aMonthsLocal = DateFormatSymbols(Locale.US).months
	val index = aMonthsLocal.indexOf(this)
	return if (index != -1)
		aMonths[index]
	else
		this
}