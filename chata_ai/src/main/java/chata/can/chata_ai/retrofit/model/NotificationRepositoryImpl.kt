package chata.can.chata_ai.retrofit.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.NotificationEntity
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRepositoryImpl: NotificationRepository {
	private var notifications = MutableLiveData<List<NotificationEntity>>()

	override fun getNotifications(): MutableLiveData<List<NotificationEntity>> {
		return notifications
	}

	override fun callNotificationsAPI() {
		val apiAdapter = ApiAdapter()
		val apiService = apiAdapter.getClientService()
		val call = apiService.getNotifications(
			"Bearer ${AutoQLData.JWT}",
			AutoQLData.apiKey,
			0,
			0)

		call.enqueue(object : Callback<JsonObject> {
			override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
				response.body()
				Log.e("RESPONSE", response.body().toString())
			}

			override fun onFailure(call: Call<JsonObject>, t: Throwable) {
				Log.e("ERROR", t.message ?: "")
				t.stackTrace
			}

		})
	}
}