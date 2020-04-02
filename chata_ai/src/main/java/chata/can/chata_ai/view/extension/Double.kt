package chata.can.chata_ai.view.extension

import chata.can.chata_ai.pojo.SinglentonDrawer

fun Double.formatDecimals(
	prefix: String = "",
	suffix: String = "",
	commaCharacter: String = ""
): String
{
	val tmp = "%$commaCharacter.${SinglentonDrawer.mDecimalsCurrency}f".format(this)
	return "$prefix$tmp$suffix"
}