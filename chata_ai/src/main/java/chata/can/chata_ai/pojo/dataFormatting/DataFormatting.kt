package chata.can.chata_ai.pojo.dataFormatting

import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.currency.Currency

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
			field = it
		} ?: throw Exception("Currency code is incorrect!")
	}

	var languageCode: String = _languageCode
	set(value) {
		SinglentonDrawer.languageCode = value
		field = value
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