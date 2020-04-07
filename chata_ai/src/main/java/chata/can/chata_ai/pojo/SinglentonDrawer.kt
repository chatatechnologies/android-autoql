package chata.can.chata_ai.pojo

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.color.ThemeConfig
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle

/**
 * Class with data for config queries and Drawer
 */
object SinglentonDrawer
{
	val mModel = BaseModelList<ChatData>()

	//region DataFormatting External
	var currencyCode = "$"
	var languageCode = "en-U"
	var currencyDecimals = 2
	var quantityDecimals = 1
	var monthYearFormat = "MMM YYYY"
	var dayMonthYearFormat = "MMM DD, YYYY"
	//endregion

	var mLightThemeColor = "#28A8E0"
	var mDarkThemeColor = "#525252"

	var mIsEnableAutocomplete = true
	var mIsEnableQuery = true
	var mIsEnableSuggestion = true
	var mIsEnableDrillDown = true

	val aChartColors = ArrayList<String>()
	var themeConfig = ThemeConfig(
		"light", "#28A8E0", "#525252", aChartColors)
}