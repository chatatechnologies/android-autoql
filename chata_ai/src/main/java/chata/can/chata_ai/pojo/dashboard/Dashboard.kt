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
	val query: String,
	val splitView: Boolean,
	val static: Boolean,
	val title: String,
	val w: Int,
	val x: Int,
	val y: Int)
{
	var queryBase: QueryBase ?= null
	var isWaitingData = false

	/**
	 * 0 for start view (execute message)
	 * 1 for loading data (gifView)
	 * 2 for support message
	 * 3 for simple text data
	 * 4 for webView data
	 */
	var iTypeView = 0
}