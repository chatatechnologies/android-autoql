package chata.can.chata_ai_api.test

import android.app.IntentService
import android.content.Intent
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.poll.Poll
import org.json.JSONArray
import org.json.JSONObject

class PollService: IntentService("PollService"), StatusResponse
{
	override fun onHandleIntent(intent: Intent?)
	{
		Poll.callPoll(this)
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		jsonObject?.let {

		}
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject?.let {

		}
	}
}