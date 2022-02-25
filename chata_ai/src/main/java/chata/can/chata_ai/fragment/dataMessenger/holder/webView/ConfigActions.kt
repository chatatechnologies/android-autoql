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
	 * CASE_2
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
		Triple(R.id.ivPivot, R.drawable.ic_table_data, "Pivot Table"),
		Triple(R.id.ivTable, R.drawable.ic_table, "Table"),
		Triple(R.id.ivBar, R.drawable.ic_bar, "Bar Chart"),
		Triple(R.id.ivColumn, R.drawable.ic_column, "Column Chart"),
		Triple(R.id.ivLine, R.drawable.ic_line, "Line Chart"),
		Triple(R.id.ivHeat, R.drawable.ic_heat, "Heatmap"),
		Triple(R.id.ivBubble, R.drawable.ic_bubble, "Bubble Chart"),
		Triple(R.id.ivStackedColumn, R.drawable.ic_stacked_column, "Stacked Column Chart"),
		Triple(R.id.ivStackedBar, R.drawable.ic_stacked_bar, "Stacked Bar Chart"),
		Triple(R.id.ivStackedArea, R.drawable.ic_stacked_area, "Stacked Area Chart")
	)
}