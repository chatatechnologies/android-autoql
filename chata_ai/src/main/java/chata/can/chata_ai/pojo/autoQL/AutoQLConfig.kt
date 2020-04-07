package chata.can.chata_ai.pojo.autoQL

import chata.can.chata_ai.pojo.SinglentonDrawer

data class AutoQLConfig(
	var _enableAutocomplete: Boolean = true,
	var _enableQueryValidation: Boolean = true,
	var _enableQuerySuggestions: Boolean = true,
	var _enableDrilldowns: Boolean = true,
	var enableColumnVisibilityManager: Boolean = true,
	var debug: Boolean = false)
{
	var enableAutocomplete: Boolean = _enableAutocomplete
	set(value) {
		SinglentonDrawer.mIsEnableAutocomplete = value
		field = value
	}

	var enableQueryValidation: Boolean = _enableQueryValidation
	set(value) {
		SinglentonDrawer.mIsEnableQuery = value
		field = value
	}

	var enableQuerySuggestions: Boolean = _enableQuerySuggestions
	set(value) {
		SinglentonDrawer.mIsEnableSuggestion = value
		field = value
	}

	var enableDrilldowns: Boolean = _enableDrilldowns
	set(value) {
		SinglentonDrawer.mIsEnableDrillDown = value
		field = value
	}
}