package chata.can.chata_ai.pojo

import chata.can.chata_ai.model.dashboard.DashboardSingle
import chata.can.chata_ai.pojo.dashboard.Dashboard

object SinglentonDashboard
{
	var dashboardColor = "#FAFAFA"
	private var dashboardSelect = 0

	private val aDashboardModel = ArrayList<DashboardSingle>()
//	private val aDashboardModelEntity = ArrayList<DashboardSingleEntity>()

	fun add(idDashboard: Int, nameDashboard: String, mModel: MutableList<Dashboard>)
	{
		aDashboardModel.add(DashboardSingle(idDashboard, nameDashboard, mModel))
//		aDashboardModelEntity.add(DashboardSingleEntity(idDashboard, nameDashboard, mModel))
	}

	fun releaseDashboard()
	{
//		aDashboardModel.clear()
		aDashboardModel.clear()
	}

	fun isEmpty() = aDashboardModel.isEmpty()
//	fun isEmpty() = aDashboardModelEntity.isEmpty()

	fun clearDashboard()
	{
		val model = getCurrentDashboard()

		for (index in 0 until model.size)
		{
			model[index].run {
				isWaitingData = false
				queryBase = null
			}
		}
	}

	fun setDashboardIndex(index: Int)
	{
		dashboardSelect = index
	}

	fun sortData()
	{
		aDashboardModel.sortBy { it.idDashboard }
//		aDashboardModelEntity.sortBy { it.idDashboard }
	}

	fun getCurrentDashboard(): MutableList<Dashboard>
	{
		return aDashboardModel[dashboardSelect].mModel
//		return aDashboardModelEntity[dashboardSelect].mModel
	}

	fun getDashboardNames() = aDashboardModel.map { it.name }
//	fun getDashboardNames() = aDashboardModelEntity.map { it.name }

	fun indexDashboard(dashboard: Dashboard) = getCurrentDashboard().indexOfFirst { it == dashboard }
}