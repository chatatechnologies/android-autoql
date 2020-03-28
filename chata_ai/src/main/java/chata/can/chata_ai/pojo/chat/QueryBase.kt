package chata.can.chata_ai.pojo.chat

import chata.can.chata_ai.DoAsync
import chata.can.chata_ai.holder.HolderContract
import chata.can.chata_ai.view.extension.enumValueOfOrNull
import chata.can.chata_ai.view.extension.formatWithColumn
import org.json.JSONObject

class QueryBase(json: JSONObject): SimpleQuery(json)
{
	private val referenceId = json.optString("reference_id") ?: ""
	private val joData = json.optJSONObject("data")
	var message = json.optString("message") ?: ""

	private var sql: String = ""
	private var queryId = ""
	var displayType = ""
	private var interpretation = ""

	val aRows = ArrayList<ArrayList<String>>()
	private var aColumn = ArrayList<ColumnQuery>()

	val numColumns: Int
	get() {
		return aColumn.size
	}

	val isSimpleText: Boolean
	get() {
		val sizeLevel1 = aRows.size
		return aRows.firstOrNull()?.let {
			val sizeLevel2 = aRows.size
			return sizeLevel1 == 1 && sizeLevel2 == 1
		} ?: run { false }
	}

	val simpleText: String
	get() {
		return aRows.firstOrNull()?.let {
			it.firstOrNull()?: run { "" }
		} ?: run { "" }
	}

	var contentHTML = ""
	private var view: HolderContract? = null
	var isLoadingHTML = false

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

						val typeColumn = enumValueOfOrNull<TypeDataQuery>(type) ?: run { TypeDataQuery.UNKNOWN }

						val column = ColumnQuery(isGroupable, typeColumn, name, isActive)
						aColumn.add(column)
					}
				}
			}
			//endregion

			DoAsync({
				isLoadingHTML = true
				contentHTML = when
				{
					isSimpleText ->
					{
						aColumn.firstOrNull()?.let {
							column ->
							simpleText.formatWithColumn(column, "$", ",")
						} ?: run { "" }
					}
					else ->
					{
						"""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body style="background-color: royalblue;">
    hello, it's a test
</body>
</html>"""
					}
				}
			},{
				isLoadingHTML = false
				showData()
			}).execute()
		}
	}

	fun checkData(view: HolderContract)
	{
		this.view = view
		if (!isLoadingHTML)
		{
			showData()
		}
	}

	private fun showData()
	{
		view?.onBind(this)
	}
}