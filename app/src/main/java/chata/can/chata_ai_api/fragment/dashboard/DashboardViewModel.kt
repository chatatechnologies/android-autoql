package chata.can.chata_ai_api.fragment.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.Executor
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.dashboard.dashboardItemDataToEntity
import chata.can.chata_ai_api.domain.GetDashboardUseCase
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class DashboardViewModel: ViewModel() {
	private val dashboardUseCase = GetDashboardUseCase()

	fun getDashboards() {
		viewModelScope.launch {
			val dashboards = dashboardUseCase.getDashboards()

			Executor({
				val aKeys = ArrayList< Pair <Int, Int> >()
				val mPartial = ConcurrentHashMap<String, Dashboard>()

				dashboards.forEach { dashboardItem ->
					val idDashboard = dashboardItem.id
					val nameDashboard = dashboardItem.name
					dashboardItem.data.forEach { dashboardItemDataResponse ->
						val axisX = dashboardItemDataResponse.x
						val axisY = dashboardItemDataResponse.y

						val dashboard = dashboardItemDataResponse.dashboardItemDataToEntity()
						if (dashboardItemDataResponse.splitView) {
							dashboard.secondQuery = dashboard.secondQuery.ifEmpty { dashboard.query }
						}

						aKeys.add(Pair(axisY, axisX))
						mPartial["${axisY}_$axisX"] = dashboard
					}

					val mModel = BaseModelList<Dashboard>()
					aKeys.sortedWith(compareBy({it.first}, {it.second})).let { list: List<Pair<Int, Int>> ->
						list.forEach { pairKeys ->
							val searchKey = "${pairKeys.first}_${pairKeys.second}"
							mPartial[searchKey]?.let { dashboard: Dashboard ->
								mModel.add(dashboard)
							}
						}
					}

					SinglentonDashboard.add(idDashboard, nameDashboard, mModel)
				}

				SinglentonDashboard.sortData()
			},{
				//release dashboard list
			}).execute()
		}
	}
}