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

	fun getParamJSON(mParams: HashMap<*,*>): String
	{
		val root = StringBuilder()
		for ((key, value) in mParams)
		{
			when(value)
			{
				is ArrayList<*> ->
				{
					val pairNode = StringBuilder("{\"$key\": [")
					//region loop for all nodes included
					for (node in value)
					{
						when(node)
						{
							is HashMap<*,*> ->
							{
								val pairNode1 = StringBuilder("{")
								for ((key1, node1) in node)
								{
									when(node1)
									{
										is ArrayList<*> ->
										{

										}
										//node is String
										else ->
										{
											pairNode1.append("\"$key1\":\"$node1\"")
										}
									}
									pairNode1.append(",")
								}
								pairNode1.deleteAt(pairNode1.length - 1)
								pairNode1.append("}")

								pairNode.append(pairNode1)
							}
							else ->
							{

							}
						}
						pairNode.append(",")
					}

					pairNode.deleteAt(pairNode.length - 1)
					pairNode.append("]}")
					//endregion

					pairNode
					root.append(pairNode)
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