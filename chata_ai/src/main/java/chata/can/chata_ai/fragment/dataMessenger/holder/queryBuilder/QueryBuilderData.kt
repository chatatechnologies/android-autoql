package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder

object QueryBuilderData
{
	val mQueries = hashMapOf(
		"Sales" to arrayListOf(
			"Total sales last month",
			"Top 5 customer by sales thus year",
			"Total sales by revenue account last year",
			"Total sales by item from services last year",
			"Average sales per month last year"),
		"Items" to arrayListOf(
			"Top 5 items by sales",
			"Which items were sold the least last year",
			"Average items sold per month last year",
			"Total profit per item last month",
			"Total items sold for services last month"),
		"Expenses" to arrayListOf(
			"All expenses last month",
			"Monthly expenses by vendor last year",
			"Total expenses by account last quarter",
			"Total expenses by quarter last year",
			"Show me expenses last year over 1000"),
		"Purchase Orders" to arrayListOf(
			"All purchases over 10000 this year",
			"All open purchase orders",
			"Total purchase orders by vendor this year",
			"Total purchase orders by quarter last year",
			"Top 5 vendor by purchase orders"
		)
	)
}