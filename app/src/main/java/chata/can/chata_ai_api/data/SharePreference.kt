package chata.can.chata_ai_api.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharePreference(private val context: Context) {
	private fun getSharePreferences(): SharedPreferences {
		return context.applicationContext.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
	}

	suspend fun saveValuePreferences(key: String, value: String) {
		val sharedPreferences = getSharePreferences()
		sharedPreferences.edit {
			putString(key, value)
		}
	}

	suspend fun saveListPreferences(mData: LinkedHashMap<String, String>) {
		val sharedPreferences = getSharePreferences()
		for ((key, value) in mData) {
			sharedPreferences.edit {
				putString(key, value)
			}
		}
	}

	suspend fun getValuePreferences(key: String, defaultValue: String): String {
		val sharedPreferences = getSharePreferences()
		return sharedPreferences.getString(key, defaultValue) ?: ""
	}
}