package chata.can.chata_ai.pojo

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ChatData

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

	//region for autoQLConfig
	var mIsEnableAutocomplete = true //enableAutocomplete
	var mIsEnableQuery = true //enableQueryValidation
	var mIsEnableSuggestion = true //enableQuerySuggestions
	var mIsEnableDrillDown = true //enableDrilldowns
	//enableColumnVisibilityManager ?
	//debug ?
	//endregion
}