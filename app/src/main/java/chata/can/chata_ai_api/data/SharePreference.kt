package chata.can.chata_ai_api.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharePreference {
	private fun getSharePreferences(context: Context): SharedPreferences {
		return context.applicationContext.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
	}

	fun saveValuePreferences(context: Context, key: String, value: String) {
		val sharedPreferences = getSharePreferences(context)
		sharedPreferences.edit {
			putString(key, value)
		}
	}

	fun saveListPreferences(context: Context, mData: LinkedHashMap<String, String>) {
		val sharedPreferences = getSharePreferences(context)
		for ((key, value) in mData) {
			sharedPreferences.edit {
				putString(key, value)
			}
		}
	}

	fun getValuePreferences(context: Context, key: String, defaultValue: String): String {
		val sharedPreferences = getSharePreferences(context)
		return sharedPreferences.getString(key, defaultValue) ?: ""
	}
}