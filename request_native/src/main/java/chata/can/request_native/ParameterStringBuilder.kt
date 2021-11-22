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
	val encode = ArrayList<String>()
	fun encodeJSON(map: HashMap<*, *>)
	{
		encode.add("{")
		for((key, value) in map)
		{
			encodeSubJSON(encode, map, "$key", value)
		}
		encode.add("}")
		"$encode"
	}

	private fun encodeSubJSON(encode: ArrayList<String>, map: HashMap<*, *>, key: String, value: Any)
	{
		when(value)
		{
			is String,
			is Int,
			is Boolean,
			is Double ->
			{
				encode.add("$key:$value,")
			}

			is ArrayList<*> ->
			{
				for (value1 in value)
				{
					encodeSubJSON(encode, map, key, value1)
				}
			}

			is HashMap<*, *> ->
			{
				encodeJSON(value)
			}

			else ->
			{
				encode.add("null")
			}
		}
	}
	//endregion

	fun getParamJSON(mParams: HashMap<*,*>): String
	{
		val root = StringBuilder()
		for ((key, value) in mParams)
		{
			when(value)
			{
				is ArrayList<*> ->
				{
					val pairNodeArray = StringBuilder("{\"$key\": [")
					//region loop for all nodes included
					for (node in value)
					{
						when(node)
						{
							is HashMap<*,*> ->
							{
								val pairNodeMap = StringBuilder("{")
								for ((key1, node1) in node)
								{
									when(node1)
									{
										is ArrayList<*> ->
										{
											//
										}
										//node is String
										else ->
										{
											val nodeParsed = when(node1)
											{
												is String -> "\"$node1\""
												else -> node1
											}
											pairNodeMap.append("\"$key1\":$nodeParsed")
										}
									}
									pairNodeMap.append(",")
								}
								pairNodeMap.deleteAt(pairNodeMap.length - 1)
								pairNodeMap.append("}")

								pairNodeArray.append(pairNodeMap)
							}
							else ->
							{

							}
						}
						pairNodeArray.append(",")
					}

					pairNodeArray.deleteAt(pairNodeArray.length - 1)
					pairNodeArray.append("]}")
					//endregion
					root.append(pairNodeArray)
				}
				else ->
				{

				}
			}
			//add key for node name
			toString()
		}
		return "$root"
	}
}