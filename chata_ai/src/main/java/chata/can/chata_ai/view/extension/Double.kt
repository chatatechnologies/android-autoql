package chata.can.chata_ai.view.extension

fun Double.formatDecimals(
	prefix: String = "",
	suffix: String = "",
	numDecimals: Int = 2,
	commaCharacter: String = ""
): String
{
	val tmp = "%$commaCharacter.${numDecimals}f".format(this)
	return "$prefix$tmp$suffix"
}