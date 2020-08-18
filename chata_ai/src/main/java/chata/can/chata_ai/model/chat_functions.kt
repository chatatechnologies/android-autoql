package chata.can.chata_ai.model

import chata.can.chata_ai.extension.enumValueOfOrNull
import chata.can.chata_ai.extension.toDateV2
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import org.json.JSONArray

fun getColumns(jaColumns: JSONArray): ArrayList<ColumnQuery>
{
	val columnsFinal = ArrayList<ColumnQuery>()
	for (index in 0 until jaColumns.length())
	{
		val column = jaColumns.getJSONObject(index)
		//is login
		//let finalType = type == "" ? (data["display_type"] as? String ?? "") : type
		val name =
			if (DataMessenger.isDemo()) column.optString("display_name")
			else column.optString("name")
		val isGroupable = column.optBoolean("groupable", false)
		val originalName = column.optString("name", "")
		val typeLocal = column.optString("type")
		val isVisible = column.optBoolean("is_visible", true)

		val typeColumn = enumValueOfOrNull<TypeDataQuery>(
			typeLocal
		) ?: run { TypeDataQuery.UNKNOWN }

		val columnQuery = ColumnQuery(isGroupable, typeColumn, name, originalName, isVisible)
		//region generate format date
		if (typeColumn == TypeDataQuery.DATE_STRING)
		{
			val index1 = originalName.indexOf("'")
			val index2 = originalName.lastIndexOf("'")
			val sDate = originalName.substring(index1 + 1, index2)
			columnQuery.formatDate = sDate
		}
		//endregion
		columnsFinal.add(columnQuery)
	}
	return columnsFinal
}

fun getRows(rows: ArrayList<ArrayList<Any>>, columnsFinal: ArrayList<ColumnQuery>)
	//: Pair< ArrayList<ArrayList<String>>, ArrayList<ArrayList<String>> >
{
	val rowsFinal = ArrayList<ArrayList<String>>()
	val rowsFinalClean = ArrayList<ArrayList<String>>()
	for (row in rows)
	{
		val finalRow = ArrayList<String>()
		val finalRowClean = ArrayList<String>()
		//By each column on row
		for ((index, element) in row.withIndex())
		{
			val column = columnsFinal[index]
			if (column.type == TypeDataQuery.DATE_STRING)
			{
				val sDate = element.toString()
				finalRow.add(sDate.toDateV2(column.formatDate))
				finalRowClean.add(sDate)
			}
			else
			{
				finalRow.add("$element")
				finalRowClean.add("$element")
			}
		}
		rowsFinalClean.add(finalRowClean)
		rowsFinal.add(finalRow)
	}

	rowsFinal.toString()
	rowsFinalClean.toString()
}