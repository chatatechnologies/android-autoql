package chata.can.chata_ai.pojo

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ChatData
import java.util.*
import kotlin.collections.ArrayList

/**
 * Class with data for config queries and Drawer
 */
object SinglentonDrawer
{
	val mModel = BaseModelList<ChatData>()

	//region DataFormatting External
	var currencyCode = "$"
	var languageCode = "en-US"
	var currencyDecimals = 2
	var quantityDecimals = 1
	var monthYearFormat = "MMM YYYY"
	var dayMonthYearFormat = "MMM DD, YYYY"
	var localeLanguage: Locale ?= null
	var aMonths = arrayListOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
	//endregion

	//region colors
	var themeColor = "light"
	var lightThemeColor = "#28A8E0"
	var darkThemeColor = "#525252"
	val aChartColors = ArrayList<String>()
	//endregion

	//region for autoQLConfig
	var mIsEnableAutocomplete = true
	var mIsEnableQuery = true
	var mIsEnableSuggestion = true
	var mIsEnableDrillDown = true
	//endregion
}