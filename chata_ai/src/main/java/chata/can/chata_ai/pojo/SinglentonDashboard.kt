package chata.can.chata_ai.pojo

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.model.dashboard.DashboardSingle
import chata.can.chata_ai.pojo.dashboard.Dashboard

object SinglentonDashboard
{
	private var dashboardSelect = 0

	private val aDashboardModel = ArrayList<DashboardSingle>()

	fun add(idDashboard: Int, nameDashboard: String, mModel: BaseModelList<Dashboard>)
	{
		aDashboardModel.add(DashboardSingle(idDashboard, nameDashboard, mModel))
	}

	fun setDashboardSelect(index: Int)
	{
		dashboardSelect = index
	}

	fun sortData()
	{
		aDashboardModel.sortBy { it.idDashboard }
	}

	fun getCurrentDashboard() = aDashboardModel[dashboardSelect].mModel

	fun getDashboardNames() = aDashboardModel.map { it.name }
}