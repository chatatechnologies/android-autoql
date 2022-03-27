package chata.can.chata_ai_api.fragment.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.Executor
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai_api.domain.GetDashboardUseCase
import kotlinx.coroutines.launch

class DashboardViewModel: ViewModel() {
	private val dashboardUseCase = GetDashboardUseCase()

	fun getDashboards() {
		viewModelScope.launch {
			val dashboards = dashboardUseCase.getDashboards()
			Executor({
				dashboards.forEach { dashboardItem ->
					val idDashboard = dashboardItem.id
					val nameDashboard = dashboardItem.name

					val mModel = BaseModelList<Dashboard>()
				}
			},{

			}).execute()
		}
	}
}