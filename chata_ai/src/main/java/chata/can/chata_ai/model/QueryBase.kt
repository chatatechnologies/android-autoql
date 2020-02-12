package chata.can.chata_ai.model

import android.content.Context
import chata.can.chata_ai.pojo.dataKey
import chata.can.chata_ai.pojo.messageKey
import chata.can.chata_ai.pojo.referenceIdKey
import org.json.JSONObject

open class QueryBase(
    private val cContext: Context,
    jsonSource: JSONObject)
{
    val sReferenceId = jsonSource.optString(referenceIdKey, "")
    val joData = jsonSource.optJSONObject(dataKey) ?: JSONObject()
    val sMessage = jsonSource.optString(messageKey, "")


    init {
        val lengthData = joData.length()
    }
}