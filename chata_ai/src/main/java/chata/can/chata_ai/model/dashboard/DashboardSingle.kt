package chata.can.chata_ai.model.dashboard

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.dashboard.Dashboard

class DashboardSingle(
	val idDashboard: Int,
	val name: String,
	val mModel: BaseModelList<Dashboard>
)