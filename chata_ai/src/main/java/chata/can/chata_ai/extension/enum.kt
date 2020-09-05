package chata.can.chata_ai.extension

import chata.can.chata_ai.pojo.chat.TypeDataQuery

inline fun <reified T: Enum<T>> enumValueOfOrNull(value: String): T?
{
	return enumValues<T>().find { it.name == value }
}

fun TypeDataQuery.isNumber() =
	this == TypeDataQuery.DOLLAR_AMT || this == TypeDataQuery.QUANTITY
		|| this == TypeDataQuery.PERCENT || this == TypeDataQuery.NUMBER