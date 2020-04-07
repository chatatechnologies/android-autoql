package chata.can.chata_ai.pojo.dataFormatting

import chata.can.chata_ai.pojo.currency.Currency

data class DataFormatting(
	private var _currencyCode: String = "USD",
	var languageCode: String = "en-US",
	var currencyDecimals: Int = 2,
	var quantityDecimals: Int = 1,
	var monthYearFormat: String = "MMM YYYY",
	var dayMonthYearFormat: String = "MMM DD, YYYY"
)
{
	var currencyCode: String = _currencyCode
	set(value) {
		Currency.mCurrency[value]?.let {
			field = it
		} ?: throw Exception("Currency code is incorrect!")
	}
}