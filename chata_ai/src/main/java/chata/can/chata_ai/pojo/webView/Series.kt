package chata.can.chata_ai.pojo.webView

import chata.can.chata_ai.extension.formatWithColumn
import chata.can.chata_ai.pojo.chat.ColumnQuery

object Series
{
	fun getDataSeries(
		aRows: ArrayList<ArrayList<String>>,
		aColumn: ArrayList<ColumnQuery>,
		position: Int,
		aNumber: ArrayList<Int>): String
	{
		val mData = linkedMapOf<String, ArrayList<String>>()
		for (row in aRows)
		{
			val header = row[position].formatWithColumn(aColumn[position])
			val aChildNumber = ArrayList<String>()
			for (iNumber in aNumber)
			{
				aChildNumber.add(row[iNumber])
			}
			mData[header] = aChildNumber
		}
		val sb =  StringBuilder("[")
		for (header in mData.keys)
		{
			sb.append("\n{\nname: \"${header}\", ")
			val sData = mData[header]?.joinToString(",", "[", "]")
			sb.append("\ndata: $sData")
			sb.append("},")
		}
		return "${sb.removeSuffix(",")}\n]"
	}
}