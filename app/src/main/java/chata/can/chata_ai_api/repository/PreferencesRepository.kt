package chata.can.chata_ai_api.repository

import android.content.Context
import chata.can.chata_ai_api.data.SharePreference

class PreferencesRepository(private val context: Context) {
	suspend fun saveValue(key: String, value: String) {
		SharePreference(context).saveValuePreferences(key, value)
	}

	suspend fun saveValues(mData: LinkedHashMap<String, String>) {
		SharePreference(context).saveListPreferences(mData = mData)
	}

	suspend fun getValue(key: String, defaultValue: String) {
		SharePreference(context).getValuePreferences(key, defaultValue)
	}
}