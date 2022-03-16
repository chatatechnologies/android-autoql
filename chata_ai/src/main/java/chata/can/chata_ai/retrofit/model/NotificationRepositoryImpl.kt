package chata.can.chata_ai.retrofit.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import chata.can.chata_ai.Executor
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.NotificationEntity
import com.google.gson.JsonElement
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
		val notificationsList: ArrayList<NotificationEntity> = arrayListOf()

		val apiAdapter = ApiAdapter()
		val apiService = apiAdapter.getClientService()
		val call = apiService.getNotifications(
			"Bearer ${AutoQLData.JWT}",
			AutoQLData.apiKey,
			0,
			0)

		call.enqueue(object : Callback<JsonObject> {
			override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
				Executor({
					val notificationsJsonArray = response.body()
						?.getAsJsonObject("data")
						?.getAsJsonArray("items")
					notificationsJsonArray?.forEach { jsonElement: JsonElement ->
						val jsonObject = jsonElement.asJsonObject
						val createdAt = jsonObject.get("created_at").asInt
						val id = jsonObject.get("id").asString
						val message = jsonObject.get("message").asString
						val title = jsonObject.get("title").asString
						val dataReturnQuery = jsonObject.get("data_return_query").asString
						val state = jsonObject.get("state").asString

						val notification = NotificationEntity(
							createdAt, id, message, title, dataReturnQuery, state)
						notificationsList.add(notification)
					}
				}, {
					notifications.value = notificationsList
				}).execute()
			}

			override fun onFailure(call: Call<JsonObject>, t: Throwable) {
				Log.e("ERROR", t.message ?: "")
				t.stackTrace
			}

		})
	}
}