package chata.can.chata_ai_api.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreferencesRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {
	suspend fun saveValue(key: String, value: String) {
		withContext(Dispatchers.IO) {
			sharedPreferences.edit {
				putString(key, value)
			}
		}
	}

	suspend fun saveValues(mData: LinkedHashMap<String, String>) {
		withContext(Dispatchers.IO) {
			sharedPreferences.edit {
				for ((key, value) in mData) {
					putString(key, value)
				}
			}
		}
	}

	suspend fun getValue(key: String, defaultValue: String): String {
		return withContext(Dispatchers.IO) {
			sharedPreferences.getString(key, defaultValue) ?: ""
		}
	}
}