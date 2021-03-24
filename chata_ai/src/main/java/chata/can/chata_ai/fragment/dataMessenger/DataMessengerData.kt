package chata.can.chata_ai.fragment.dataMessenger

import chata.can.chata_ai.pojo.autoQL.AutoQLConfig
import chata.can.chata_ai.pojo.dataFormatting.DataFormatting

object DataMessengerData
{
	var customerName = ""
	var title = ""
	var introMessage = ""
	var inputPlaceholder = ""
	var clearOnClose = false
	var enableVoiceRecord = true
	//components
	var isDataMessenger = true

	var isVisible = true

	var maxMessages = 2
		set(value) {
			field = if (value > 1) value else 2
		}

	var autoQLConfig = AutoQLConfig(
		_enableAutocomplete = true,
		_enableQueryValidation = true,
		_enableQuerySuggestions = true,
		_enableDrilldowns = true,
		enableColumnVisibilityManager = true,
		debug = true)

	var dataFormatting = DataFormatting(
		"USD",
		"en-US",
		2,
		1,
		"MMM YYYY",
		"MMM DD, YYYY")

	var isDarkenBackgroundBehind = true
	var visibleExploreQueries = true
	var visibleNotification = true
	var activeNotifications = true

	const val THEME_LIGHT = true
	const val THEME_DARK = false
}