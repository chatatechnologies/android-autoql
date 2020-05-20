package chata.can.chata_ai.dialog.drillDown

import chata.can.chata_ai.pojo.chat.QueryBase

interface DrillDownContract
{
	fun loadDrillDown(queryBase: QueryBase)
}