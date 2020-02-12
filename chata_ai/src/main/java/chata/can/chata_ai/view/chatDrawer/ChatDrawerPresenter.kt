package chata.can.chata_ai.view.chatDrawer

import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.pojo.urlBase
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class ChatDrawerPresenter: StatusResponse
{
    fun autocomplete(sAutocomplete: String)
    {
        val url = "$urlBase${api1}autocomplete?q=$sAutocomplete&projectid=1"
        RequestBuilder.callStringRequest(
            Request.Method.GET,
            url,
            typeJSON,
            listener = this
        )
    }

    override fun onFailure(jsonObject: JSONObject?)
    {
        jsonObject?.let {

        }
    }

    override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
    {
        jsonObject?.let {

        }

        jsonArray?.let {

        }
    }
}