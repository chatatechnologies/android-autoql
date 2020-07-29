package chata.can.chata_ai.model

import chata.can.chata_ai.extension.enumValueOfOrNull
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import org.json.JSONArray
import org.json.JSONObject

class ChatComponent(jsonObject: JSONObject, type: String = "")
{
	init {
		jsonObject.run {
			val message = optString("message", "")
			if (length() > 0)
			{
				val jaColumns = optJSONArray("columns") ?: JSONArray()
				val finalType = if (type.isEmpty()) optString("display_type") else type

				var displayType = enumValueOfOrNull<ChatComponentType>(
					finalType
				) ?: run { ChatComponentType.UNKNOWN }

				val idQuery = optString("query_id", "")
				val jaRows = optJSONArray("rows") ?: JSONArray()
				val columnsFinal = ArrayList<ColumnQuery>()

				var textFinal = ""
				var user = true
				var numRow = 20

				for (index in 0 until jaColumns.length())
				{
					val column = jaColumns.getJSONObject(index)
					//is login
					val name = if (true) column.optString("display_name") else column.optString("name")
					val originalName = column.optString("name", "")
					val typeLocal = column.optString("type")
					val isVisible = column.optBoolean("is_visible", true)

					val typeColumn = enumValueOfOrNull<TypeDataQuery>(
						typeLocal
					) ?: run { TypeDataQuery.UNKNOWN }

					columnsFinal.add(ColumnQuery(false, typeColumn, name, originalName, false, isVisible))
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

				val columnsF = columnsFinal.map { it.name }

				val suggestions = if (displayType == ChatComponentType.SUGGESTION)
				{
					aRows.map { it[0] }
				}
				else arrayListOf()

				var webView = ""
				val chartsBi = displayType == ChatComponentType.PIE || displayType == ChatComponentType.BAR || displayType == ChatComponentType.COLUMN || displayType == ChatComponentType.LINE
				val chartsTri = displayType == ChatComponentType.HEAT_MAP || displayType == ChatComponentType.BUBBLE || displayType == ChatComponentType.STACK_COLUMN || displayType == ChatComponentType.STACK_BAR || displayType == ChatComponentType.STACK_AREA

				val aColumnType = columnsFinal.map { it.type }

				if (columnsF.isEmpty() && aRows.isEmpty())
				{
					displayType = ChatComponentType.INTRODUCTION
					user = false
					textFinal = "Uh oh.. It looks like you don't have access to this resource. Please double check that all the required authentication fields are provided."
				}

				if (displayType == ChatComponentType.WEB_VIEW || displayType == ChatComponentType.TABLE || chartsBi || chartsTri)
				{
					val supportTri = columnsFinal.size == 3
					var datePivotStr = ""
					var dataPivotStr = ""
					var tableBasicStr = ""
				}
			}
		}
	}
}