package chata.can.chata_ai.model

import chata.can.chata_ai.extension.enumValueOfOrNull
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import org.json.JSONArray
import org.json.JSONObject

class ChatComponent(jsonObject: JSONObject)
{
	init {
		jsonObject.run {
			val message = optString("message", "")
			if (length() > 0)
			{
				val jaColumns = optJSONArray("columns") ?: JSONArray()
				val finalType = optString("display_type")

				var displayType = enumValueOfOrNull<TypeDataQuery>(
					finalType
				) ?: run { TypeDataQuery.UNKNOWN }

				val idQuery = optString("query_id", "")
				val jaRows = optJSONArray("rows") ?: JSONArray()
				val aColumn = ArrayList<ColumnQuery>()

				val user = true
				var numRow = 20

				for (index in 0 until jaColumns.length())
				{
					val column = jaColumns.getJSONObject(index)
					//is login
					val name = if (true) column.optString("display_name") else column.optString("name")
					val originalName = column.optString("name", "")
					val type = column.optString("type")
					val isVisible = column.optBoolean("is_visible", true)

					val typeColumn = enumValueOfOrNull<TypeDataQuery>(
						type
					) ?: run { TypeDataQuery.UNKNOWN }

					aColumn.add(ColumnQuery(false, typeColumn, name, originalName, false, isVisible))
				}

				//region Rows
				val aRows = ArrayList<ArrayList<String>>()
				for (index in 0 until jaRows.length())
				{
					val newRow = ArrayList<String>()
					val jaLevel2 = jaRows.optJSONArray(index)
					for (index2 in 0 until jaLevel2.length())
					{
						val cell = if (jaLevel2.isNull(index2)) ""
						else jaLevel2.optString(index2, "")
						newRow.add(cell)
					}
					aRows.add(newRow)
				}
				//endregion
				numRow = aRows.size

				val columnsF = aColumn.map { it.name }

				if (displayType == TypeDataQuery.)
//				let suggestions: [String] = displayType == ChatComponentType.Suggestion ?
//				rows.map{ (element) -> String in
//					return "\(element[0])"
//				} : []
				var webView = ""
			}
		}
	}
}