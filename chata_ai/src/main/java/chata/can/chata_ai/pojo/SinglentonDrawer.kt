package chata.can.chata_ai.pojo

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ChatData

/**
 * Class with data for config queries and Drawer
 */
object SinglentonDrawer
{
	var mModel = BaseModelList<ChatData>()

	var mCurrencyCode = "$"
	var mLanguageCode = "en-U"
	var mFormatMonthYear = "MMM YYYY"
	var mFormatDayMonthYear = "MMM DD, YYYY"
	var mDecimalsCurrency = 2
	var mDecimalsQuantity = 1
	var mIntroMessage = ""
	var mQueryPlaceholder = ""
	var mIsClearMessage = false
}