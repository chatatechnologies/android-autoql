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
	private val encoded = StringBuilder()
	fun encodeJSON(map: HashMap<*, *>): String
	{
		encoded.append("{")
		for((key, value) in map)
		{
			encodeSubJSON(map, "$key", value)
		}
		clearComma()
		encoded.append("}")
		return "$encoded"
	}

	private fun encodeSubJSON(map: HashMap<*, *>, key: String, value: Any)
	{
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
					encodeSubJSON(map, key, value1)
					encoded.append(",")
				}
				clearComma()
				encoded.append("]")
			}

			is HashMap<*, *> ->
			{
				encodeJSON(value)
			}

			else ->
			{
				encoded.append("null")
			}
		}
	}

	private fun clearComma()
	{
		encoded.lastOrNull()?.let {
			if (it == ',')
			{
				encoded.deleteCharAt(encoded.length - 1)
			}
		}
	}
	//endregion
}