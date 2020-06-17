package chata.can.chata_ai.extension

inline fun <reified T: Enum<T>> enumValueOfOrNull(value: String): T?
{
	return enumValues<T>().find { it.name == value }
}