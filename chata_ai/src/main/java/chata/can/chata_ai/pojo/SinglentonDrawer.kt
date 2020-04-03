package chata.can.chata_ai.pojo

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ChatData

/**
 * Class with data for config queries and Drawer
 */
object SinglentonDrawer
{
	val mModel = BaseModelList<ChatData>()

	var mCurrencyCode = "$"
	var mLanguageCode = "en-U"
	var mFormatMonthYear = "MMM YYYY"
	var mFormatDayMonthYear = "MMM DD, YYYY"
	var mDecimalsCurrency = 2
	var mDecimalsQuantity = 1
	var mIntroMessage = ""
	var mQueryPlaceholder = ""
	var mIsClearMessage = false
	var mTitle = ""
	val aChartColors = ArrayList<String>()
	var mLightThemeColor = "#28A8E0"
	var mDarkThemeColor = "#525252"
	var mMaxNumberMessage = 6
	var mIsEnableAutocomplete = true
	var mIsEnableQuery = true
}