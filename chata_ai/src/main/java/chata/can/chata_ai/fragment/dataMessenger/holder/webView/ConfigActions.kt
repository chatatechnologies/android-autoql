package chata.can.chata_ai.fragment.dataMessenger.holder.webView

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
		R.id.ivStackedColumn,
		R.id.ivHeat,
		R.id.ivBubble,
		R.id.ivStackedBar,
		R.id.ivStackedArea
	)

	/**
	 * configActions = 6
	 */
	val triBiBarColumnConfig = arrayListOf(
		R.id.ivPivot,
		R.id.ivTable,
		R.id.ivStackedColumn,
		R.id.ivBar,
		R.id.ivColumn,
		R.id.ivLine,
		R.id.ivHeat,
		R.id.ivBubble,
		R.id.ivStackedBar,
		R.id.ivStackedArea
	)

	val pBiTriConfig = arrayListOf(
		Pair(R.id.ivPivot, R.drawable.ic_table_data),
		Pair(R.id.ivTable, R.drawable.ic_table),
		Pair(R.id.ivBar, R.drawable.ic_bar),
		Pair(R.id.ivColumn, R.drawable.ic_column),
		Pair(R.id.ivLine, R.drawable.ic_line),
		Pair(R.id.ivHeat, R.drawable.ic_heat),
		Pair(R.id.ivBubble, R.drawable.ic_bubble),
		Pair(R.id.ivStackedColumn, R.drawable.ic_stacked_column),
		Pair(R.id.ivStackedBar, R.drawable.ic_stacked_bar),
		Pair(R.id.ivStackedArea, R.drawable.ic_stacked_area)
	)
}