package chata.can.chata_ai.pojo.dashboard

import chata.can.chata_ai.pojo.chat.QueryBase

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
	var query: String,
	val splitView: Boolean,
	val static: Boolean,
	var title: String,
	val w: Int,
	val x: Int,
	val y: Int)
{
	var secondQuery = ""
	var secondDisplayType = ""

	var queryBase: QueryBase ?= null

	var queryBase2: QueryBase ?= null

	var isWaitingData = false
	var isWaitingData2 = false

	var value = ""
	var valueLabel = ""
}