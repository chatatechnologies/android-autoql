package chata.can.chata_ai.service

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.request.Poll
import chata.can.request_native.StatusResponse
import org.json.JSONArray
import org.json.JSONObject

class PollService: JobIntentService(), StatusResponse
{
	companion object {
		var unacknowledged = 0
		const val DATA = "data"
		const val NOTIFICATION = "chata.can.chata_ai_api.test.PollService"

		fun enqueueWork(context: Context, intent: Intent)
		{
			enqueueWork(context, PollService::class.java, 1, intent)
		}
	}

	override fun onHandleWork(intent: Intent)
	{
		if (AutoQLData.activeNotifications)
		{
			AutoQLData.activeNotifications = false
			Poll.callPoll(this)
		}
	}

	override fun onFailureResponse(jsonObject: JSONObject)
	{
		val intent = Intent(NOTIFICATION)
		intent.putExtra(DATA, "{}")
		sendBroadcast(intent)
	}

	override fun onSuccessResponse(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {
			it.optJSONObject("data")?.let { joData ->
				unacknowledged = joData.optInt("unacknowledged")
			}
			val intent = Intent(NOTIFICATION)
			intent.putExtra(DATA, it.toString())
			sendBroadcast(intent)
		}
	}
}