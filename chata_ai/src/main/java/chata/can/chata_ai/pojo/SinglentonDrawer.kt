package chata.can.chata_ai.pojo

import android.graphics.Color
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerModel
import java.util.*

/**
 * Class with data for config queries and Drawer
 */
object SinglentonDrawer
{
	val mModel = DataMessengerModel()

	//region DataFormatting External
	var currencyCode = "$"
	set(value) {
		field = value
		for ((_, method) in aCurrencyMethods)
		{
			method()
		}
	}
	val aCurrencyMethods = LinkedHashMap<String, () -> Unit>()

	var languageCode = "en-US"
	var currencyDecimals = 2
	var quantityDecimals = 1
	var monthYearFormat = "MMM YYYY"
	var dayMonthYearFormat = "MMM DD, YYYY"

	var localLocale: Locale ?= Locale("en", "US")
	set(value) {
		field = value
		for ((_, method) in aLocaleMethods)
		{
			method()
		}
	}
	val aLocaleMethods = LinkedHashMap<String, () -> Unit>()

	var aMonths = arrayListOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
	var aMonthShorts = arrayListOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec")
	//endregion

	//region colors
	var themeColor = "dark"
	var lightThemeColor = "#26A7DF"
	var pLightThemeColor = Color.parseColor(lightThemeColor)
	var darkThemeColor = "#525252"
	var pDarkThemeColor = Color.parseColor(darkThemeColor)
	val currentAccent: Int
		get() {
		return if (themeColor == "dark") pDarkThemeColor
			else pLightThemeColor
	}
	val aChartColors = ArrayList<String>()
	//endregion

	//region for autoQLConfig
	var mIsEnableAutocomplete = true
	var mIsEnableQuery = true
	var mIsEnableSuggestion = true
	var mIsEnableDrillDown = true
	//endregion
}