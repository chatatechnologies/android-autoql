package chata.can.chata_ai.model

import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.TypeDataQuery

fun getColumns()
{

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
			if (columnsFinal[index].type == TypeDataQuery.DATE_STRING)
			{
				val sDate = element.toString()
				finalRow.add(sDate)
				finalRowClean.add(sDate)
			}
			else
			{
				finalRow.add("$element")
				finalRowClean.add("$element")
			}
		}
	}

	rowsFinal.toString()
	rowsFinalClean.toString()
}