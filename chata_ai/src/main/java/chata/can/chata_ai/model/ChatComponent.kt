package chata.can.chata_ai.model

import chata.can.chata_ai.extension.enumValueOfOrNull
import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.pojo.webView.DashboardMaker
import org.json.JSONArray
import org.json.JSONObject

class ChatComponent(jsonObject: JSONObject, type: String = "", split: Boolean = false)
{
	init {
		jsonObject.run {
			val message = optString("message", "")
			val joData = optJSONObject("data") ?: JSONObject()
			joData.run {
				if (length() > 0)
				{
					val jaColumns = optJSONArray("columns") ?: JSONArray()
					val finalType = if (type.isEmpty()) optString("display_type") else type

					var displayType = enumValueOfOrNull(finalType) ?: run {
						when(finalType)
						{
							"data" -> ChatComponentType.WEB_VIEW
							"table" -> ChatComponentType.TABLE
							else -> ChatComponentType.UNKNOWN
						}
					}

					val idQuery = optString("query_id", "")
					val jaRows = optJSONArray("rows") ?: JSONArray()

					var textFinal = ""
					var user = true
					var numRow = 20

					val columnsFinal = getColumns(jaColumns)
					//region jaRows to aRows
					val aRows = ArrayList<ArrayList<Any>>()
					for (index in 0 until jaRows.length())
					{
						val newRow = ArrayList<Any>()
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
					//first is rowsFinal; second is rowsFinalClean
					val pRowFinal = getRows(aRows, columnsFinal)
					//region check case when rows is one
					if (pRowFinal.second.size == 1)
					{
						val aTmp = pRowFinal.second
						textFinal = aTmp[0][0].formatWithColumn(columnsFinal[0])
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
						val existsDatePivot = isSupportPivot(aColumnType)
						val supportTri = columnsFinal.size == 3
						var dataPivotStr = ""
						var tableBasicStr = ""
						val drills = ArrayList<String>()

						//region existsDatePivot
						if (existsDatePivot)
						{

						}
						//endregion

						if (supportTri)
						{

						}
						tableBasicStr = tableString(
							pRowFinal.first,
							columnsF,
							"idTableBasic",
							columnsFinal,
							false)
						val typeFinal = if (type.isEmpty()) "#idTableBasic" else type

						webView = """
							${DashboardMaker.getHeader(supportTri)}
							$tableBasicStr
							${DashboardMaker.getHTMLFooter(pRowFinal.first, columnsFinal, aColumnType, drills, type)}
						"""
					}
				}
			}
		}
	}
}