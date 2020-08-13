package chata.can.chata_ai.pojo.dashboard

import chata.can.chata_ai.pojo.chat.QueryBase
import org.json.JSONObject

data class Dashboard(
	val displayType: String,
	val h: Int,
	val i: String,
	val isNewTile : Boolean,
	val key: String,
	val maxH: Int,
	val minH: Int,
	val minW: Int,
	val moved: Boolean,
	val query: String,
	val splitView: Boolean,
	val static: Boolean,
	val title: String,
	val w: Int,
	val x: Int,
	val y: Int)
{
	var secondQuery = ""
	var secondDisplayType = ""
	var queryBase: QueryBase ?= null
	var jsonPrimary: JSONObject ?= null
//	var queryBase2: QueryBase ?= null
	var jsonSecondary: JSONObject ?= null
	var isWaitingData = false
}