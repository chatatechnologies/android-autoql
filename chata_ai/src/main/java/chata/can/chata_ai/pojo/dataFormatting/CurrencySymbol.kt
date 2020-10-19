package chata.can.chata_ai.pojo.dataFormatting

import java.util.*

object CurrencySymbol
{
	fun init()
	{
		val pound = Currency.getInstance("INR")
		pound.symbol
		println(pound)
	}
}