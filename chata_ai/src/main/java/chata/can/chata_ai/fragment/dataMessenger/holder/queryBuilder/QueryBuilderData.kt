package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder

object QueryBuilderData
{
	val aDataAccounting = arrayListOf("Sales", "Items", "Expenses", "Purchase Orders")
	val mQueriesAccounting = linkedMapOf(
		"Sales" to arrayListOf(
			"Total sales last month",
			"Top 5 customer by sales this year",
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

	val aDataSpira = arrayListOf("Revenue", "Cost", "Estimates", "Utilization", "Jobs", "Tickets")
	val mQueriesSpira = linkedMapOf(
		"Revenue" to arrayListOf(
			"Average revenue by area last year",
			"Average revenue by item code name last six months",
			"Average revenue by job type last year",
			"Average revenue by month last six months",
			"Average revenue by ticket type last 6 months",
			"Total revenue by area last year",
			"Total revenue by customer last six months",
			"Total revenue by job type last year",
			"Total revenue by ticket type this year",
			"Total revenue this year"),
		"Cost" to arrayListOf(
			"All costs with actual amount over 10000 added this year",
			"All costs added last month",
			"Last cost",
			"Last cost with actual amount over 500",
			"Total cost by month last year",
			"Total cost by item category this year",
			"Total cost by resource last month",
			"Average cost by ticket type last year",
			"Average cost by area last year",
			"Average cost by item category last year"),
		"Estimates" to arrayListOf(
			"Last estimate",
			"Last estimate over 10000",
			"All estimates last month",
			"All estimates this year over 30000",
			"All cost estimates added this year",
			"Total estimates by ticket type this year",
			"Total estimates by customer this year",
			"Total estimates by month last year",
			"Total estimates by area last year",
			"Total estimates by job type by month last year"),
		"Utilization" to arrayListOf(
			"Total hours utilization by resource category this year",
			"Total hours utilization by job last month",
			"Total utilization by resource last month",
			"Last utilization for equipment",
			"Last utilization for personnel",
			"Total hours utilization by resource category by month last year",
			"Total hours utilization by area by resource last year",
			"Total hours utilization by job by resource category last month",
			"Average hours utilization by area by resource code last year",
			"Average hours utilization by area by resource category last year",
			"Average hours utilization by resource category by month this year"
		),
		"Jobs" to arrayListOf(
			"All jobs in bid state",
			"All jobs scheduled to start this year",
			"All open jobs",
			"All jobs that ended late that started last two years",
			"Number of jobs this year",
			"Number of jobs by job type this year",
			"Number of jobs by province last year",
			"Number of jobs by status this year",
			"Number of jobs by resource this year",
			"Number of jobs by customer this month"
		),
		"Tickets" to arrayListOf(
			"All tickets that ended early",
			"All tickets that ended late",
			"All void tickets over 10000",
			"Total tickets by ticket type by month last six months",
			"Total tickets by item code name",
			"Total tickets by item category this year",
			"Total tickets by customer this year",
			"Total tickets by month last year",
			"Average ticket by ticket type last year",
			"Average ticket by area last year"
		)
	)
}