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

	//var userDisplayName = "there"
	//var introMessage =
	//var inputPlaceholder = "Type your queries here"

	var currencyCode = "$"
	var languageCode = "en-U"
	var currencyDecimals = 2
	var quantityDecimals = 1
	var monthYearFormat = "MMM YYYY"
	var dayMonthYearFormat = "MMM DD, YYYY"
	//replace param with userDisplayName
	//var introMessage = "Hi param! Let\'s dive into your data. What can I help you discover today?"
	//var mQueryPlaceholder = ""
	//var mIsClearMessage = false
	//var mTitle = "Data Messenger"
	var mLightThemeColor = "#28A8E0"
	var mDarkThemeColor = "#525252"
	//var mMaxNumberMessage = 6
	var mIsEnableAutocomplete = true
	var mIsEnableQuery = true
	var mIsEnableSuggestion = true
	var mIsEnableDrillDown = true
	//var enableVoiceRecord = true

	val aChartColors = ArrayList<String>()
	var themeConfig = ThemeConfig(
		"light", "#28A8E0", "#525252", aChartColors)
}