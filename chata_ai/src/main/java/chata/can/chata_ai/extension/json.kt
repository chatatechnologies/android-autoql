package chata.can.chata_ai.extension

import org.json.JSONArray
import org.json.JSONObject

fun JSONObject.hasInList(array: ArrayList<String>): Boolean
{
	for (key in array)
	{
		if (has(key))
		{
			return true
		}
	}
	return false
}

fun JSONObject.optStringInList(array: ArrayList<String>): String
{
	var value = ""
	for (key in array)
	{
		if (has(key))
		{
			value = optString(key)
			break
		}
	}
	return value
}

fun JSONObject.optJSONArrayInList(array: ArrayList<String>): JSONArray
{
	for (key in array)
	{
		if (has(key))
		{
			return optJSONArray(key) ?: JSONArray()
		}
	}
	return JSONArray()
}