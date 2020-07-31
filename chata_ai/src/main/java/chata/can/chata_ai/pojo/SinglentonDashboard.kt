package chata.can.chata_ai.pojo

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.model.dashboard.DashboardSingle
import chata.can.chata_ai.pojo.dashboard.Dashboard

object SinglentonDashboard
{
	val aDashboardModel = ArrayList<DashboardSingle>()
	val mModel = BaseModelList<Dashboard>()
}