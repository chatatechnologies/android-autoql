package chata.can.request_native

import java.net.URLEncoder

object ParameterStringBuilder
{
	fun getParamsString(params: HashMap<String, Any>): String
	{
		val result = StringBuilder()

		for (param in params)
		{
			result.append(URLEncoder.encode(param.key, "UTF-8"))
			result.append("=")
			result.append(URLEncoder.encode("${param.value}", "UTF-8"))
			result.append("&")
		}

		val out = "$result"
		return if (out.isNotEmpty()) out.substring(0, out.length - 1) else out
	}

	//region encodeJSON with Map
	private val mEncode = HashMap<String, StringBuilder>()

	private fun getEncode(keyEncode: String) = mEncode[keyEncode] ?: StringBuilder()

	fun getParamsJSON(key: String, map: HashMap<*, *>): String
	{
		mEncode[key] = StringBuilder()
		encodeJSON(key, map)
		val string = "${mEncode[key]}"
		mEncode.remove(key)
		return string
	}

	private fun encodeJSON(keyEncode: String, map: HashMap<*, *>): String
	{
		val encoded = getEncode(keyEncode)
		encoded.append("{")
		for((key, value) in map)
		{
			encodeSubJSON(keyEncode, map, "$key", value)
		}
		clearComma(keyEncode)
		encoded.append("}")
		return "$encoded"
	}

	private fun encodeSubJSON(keyEncode: String, map: HashMap<*, *>, key: String, value: Any)
	{
		val encoded = getEncode(keyEncode)
		when(value)
		{
			is String,
			is Int,
			is Boolean,
			is Double ->
			{
				val tmp = if (value is String) "\"$value\"" else value
				encoded.append("\"$key\":$tmp,")
			}

			is ArrayList<*> ->
			{
				encoded.append("\"$key\" :[")
				for (value1 in value)
				{
					encodeSubJSON(keyEncode, map, key, value1)
					encoded.append(",")
				}
				clearComma(keyEncode)
				encoded.append("]")
				encoded.append(",")
			}

			is HashMap<*, *> ->
			{
				encodeJSON(keyEncode, value)
			}

			else ->
			{
				encoded.append("null")
			}
		}
	}

	private fun clearComma(keyEncode: String)
	{
		val encoded = getEncode(keyEncode)
		encoded.lastOrNull()?.let {
			if (it == ',')
			{
				encoded.deleteCharAt(encoded.length - 1)
			}
		}
	}
	//endregion
}