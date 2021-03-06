package chata.can.chata_ai.pojo.dataFormatting

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.activity.dm.Currency
import java.util.*

data class DataFormatting(
	private var _currencyCode: String = "USD",
	var _languageCode: String = "en-US",
	var _currencyDecimals: Int = 2,
	var _quantityDecimals: Int = 1,
	var _monthYearFormat: String = "MMM YYYY",
	var _dayMonthYearFormat: String = "MMM DD, YYYY"
)
{
	var currencyCode: String = _currencyCode
	set(value) {
		Currency.mCurrency[value]?.let {
			SinglentonDrawer.currencyCode = it
			field = it
		} ?: throw Exception("Currency code is incorrect!")
	}

	var languageCode: String = _languageCode
	set(value) {
		val aData = value.split("-")
		if (aData.size > 1)
		{
			if (ValidateLocale.isValid(Locale(aData[0], aData[1])))
			{
				with(SinglentonDrawer)
				{
					localLocale = Locale(aData[0], aData[1])
					aMonths.clear()
					aMonthShorts.clear()
					localLocale?.let { locale ->
						for (i in 1 .. 12)
						{
							val month = ValidateLocale.getMonth(i, locale, "MMMM")
							val monthShort = ValidateLocale.getMonth(i, locale, "MMM")
							aMonths.add(month)
							aMonthShorts.add(monthShort)
						}
					}
					languageCode = value
				}
				field = value
			}
		}
	}

	var currencyDecimals: Int = _currencyDecimals
	set(value) {
		SinglentonDrawer.currencyDecimals = value
		field = value
	}

	var quantityDecimals: Int = _quantityDecimals
	set(value) {
		SinglentonDrawer.quantityDecimals = value
		field = value
	}

	var monthYearFormat: String = _monthYearFormat
	set(value) {
		SinglentonDrawer.monthYearFormat = value
		field = value
	}

	var dayMonthYearFormat: String = _dayMonthYearFormat
	set(value) {
		SinglentonDrawer.dayMonthYearFormat = value
		field = value
	}
}