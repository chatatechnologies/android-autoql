package chata.can.chata_ai.pojo.chat

import org.json.JSONObject

class QueryBase(private val json: JSONObject)
{
	private val referenceId = json.optString("reference_id") ?: ""
	private val joData = json.optJSONObject("data")
	private val message = json.optString("message") ?: ""

	private var sql: String = ""
	private var queryId = ""
	private var displayType = ""
	private var interpretation = ""

	private val aRows = ArrayList<ArrayList<String>>()
	private var aColumn = ArrayList<ColumnQuery>()

	init {
		joData?.let {
			sql = joData.optString("sql") ?: ""
			queryId = joData.optString("query_id") ?: ""
			displayType = joData.optString("display_type") ?: ""
			interpretation = joData.optString("interpretation") ?: ""

			//region rows
			it.optJSONArray("rows")?.let {
				jaRows ->
				aRows.clear()
				//each row
				for (index in 0 until jaRows.length())
				{
					val newRow = ArrayList<String>()
					val jaLevel2 = jaRows.optJSONArray(index)
					for (index2 in 0 until jaLevel2.length())
					{
						val cell = jaLevel2.optString(index2, "")
						newRow.add(cell)
					}
					aRows.add(newRow)
				}
			}
			//endregion

			//region columns
			it.optJSONArray("columns")?.let {
				jaColumns ->
				aColumn.clear()
				for (index in 0 until jaColumns.length())
				{
					jaColumns.optJSONObject(index)?.let {
						joColumn ->
						val isGroupable = joColumn.optBoolean("groupable", false)
						val type = joColumn.optString("type")
						val name = joColumn.optString("name")
						val isActive = joColumn.optBoolean("active", false)
						val column = ColumnQuery(isGroupable, type, name, isActive)
						aColumn.add(column)
					}
				}
				println("hola")
			}
			//endregion
		}
	}
}