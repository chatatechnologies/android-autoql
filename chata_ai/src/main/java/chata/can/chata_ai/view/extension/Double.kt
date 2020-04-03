package chata.can.chata_ai.view.extension

import chata.can.chata_ai.pojo.SinglentonDrawer

fun Double.formatSymbolDecimals(
	prefix: String = "",
	suffix: String = ""
): String
{
	val tmp = "%,.${SinglentonDrawer.mDecimalsCurrency}f".format(this)
	return "$prefix$tmp$suffix"
}

fun Double.formatDecimals(
	numDecimals: Int
): String
{
	return "%.${numDecimals}f".format(this)
}