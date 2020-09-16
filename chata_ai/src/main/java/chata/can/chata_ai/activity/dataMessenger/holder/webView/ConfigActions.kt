package chata.can.chata_ai.activity.dataMessenger.holder.webView

import chata.can.chata_ai.R

object ConfigActions
{
	/**
	 * configActions = 1
	 */
	val biConfig = arrayListOf(
		R.id.ivPivot,
		R.id.ivTable,
		R.id.ivColumn,
		R.id.ivBar,
		R.id.ivLine,
		R.id.ivPie)

	/**
	 * configActions = 2
	 */
	val triReduceConfig = arrayListOf(
		R.id.ivTable,
		R.id.ivColumn,
		R.id.ivBar,
		R.id.ivLine)

	/**
	 * configActions = 3
	 */
	val triConfig = arrayListOf(
		R.id.ivPivot,
		R.id.ivTable,
		R.id.ivColumn,
		R.id.ivBar,
		R.id.ivHeat,
		R.id.ivBubble)

	/**
	 * CASE_1
	 * CASE_5
	 * configActions = 4
	 */
	val biConfigReduce = arrayListOf(
		R.id.ivTable,
		R.id.ivColumn,
		R.id.ivBar,
		R.id.ivLine,
		R.id.ivPie)

	/**
	 * configActions = 5
	 */
	val triStackedConfig = arrayListOf(
		R.id.ivPivot,
		R.id.ivTable,
		R.id.ivHeat,
		R.id.ivBubble,
		R.id.ivStackedBar,
		R.id.ivStackedColumn,
		R.id.ivStackedArea
	)

	/**
	 * configActions = 6
	 */
	val triBiBarColumnConfig = arrayListOf(
		R.id.ivPivot,
		R.id.ivTable,
		R.id.ivHeat,
		R.id.ivBubble,
		R.id.ivBar,
		R.id.ivColumn,
		R.id.ivStackedArea
	)
}