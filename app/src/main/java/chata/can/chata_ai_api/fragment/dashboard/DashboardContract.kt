package chata.can.chata_ai_api.fragment.dashboard

interface DashboardContract
{
	fun setDashboards()
	fun reloadQueries()
	fun notifyQueryAtIndex(index: Int)
}