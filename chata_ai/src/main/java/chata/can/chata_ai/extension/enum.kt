package chata.can.chata_ai.extension

import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.retrofit.data.model.column.TypeColumn

inline fun <reified T: Enum<T>> enumValueOfOrNull(value: String): T?
{
	return enumValues<T>().find { it.name == value }
}

fun TypeColumn.isUnCountable() = this == TypeColumn.DATE || this == TypeColumn.DATE_STRING || this == TypeColumn.STRING || this == TypeColumn.UNKNOWN

fun TypeColumn.isNumber() = this == TypeColumn.DOLLAR_AMT || this == TypeColumn.QUANTITY || this == TypeColumn.PERCENT

fun TypeDataQuery.isUnCountable() =
	this == TypeDataQuery.DATE || this == TypeDataQuery.DATE_STRING
		|| this == TypeDataQuery.STRING || this == TypeDataQuery.UNKNOWN

fun TypeDataQuery.isNumber() =
	this == TypeDataQuery.DOLLAR_AMT || this == TypeDataQuery.QUANTITY
		|| this == TypeDataQuery.PERCENT || this == TypeDataQuery.NUMBER