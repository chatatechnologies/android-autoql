package chata.can.chata_ai.pojo.autoQL

import chata.can.chata_ai.fragment.exploreQuery.ExploreQueriesData
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.dataFormatting.DataFormatting
import chata.can.chata_ai.retrofit.data.model.ExploreQueriesProvider
import chata.can.chata_ai.retrofit.data.model.relatedQueries.emptyRelatedQueryPagination

object AutoQLData
{
	var isRelease = true

	const val THEME_LIGHT = true
	const val THEME_DARK = false

	var projectId = ""
	var userID = ""
	var apiKey = ""
	var domainUrl = ""
	var token = ""
	var username = ""
	var password = ""
	var JWT = ""

	fun clearData()
	{
		projectId = ""
		userID = ""
		username = ""
		password = ""
		apiKey = ""
		domainUrl = ""
		token = ""
		JWT = ""
		wasLoginIn = false

		SinglentonDrawer.mModel.clear()
		SinglentonDashboard.releaseDashboard()
//		ExploreQueriesData.lastWord = ""
		ExploreQueriesProvider.lastQuery = ""
		ExploreQueriesProvider.pagination = emptyRelatedQueryPagination()
//		ExploreQueriesData.lastExploreQuery = null
	}

	var wasLoginIn = false

	var lastName = ""
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
	var isColumnVisibility = true
	var visibleNotification = true
	var activeNotifications = true
}