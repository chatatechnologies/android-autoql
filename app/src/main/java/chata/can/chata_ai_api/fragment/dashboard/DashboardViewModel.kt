package chata.can.chata_ai_api.fragment.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai_api.domain.GetDashboardUseCase
import kotlinx.coroutines.launch

class DashboardViewModel: ViewModel() {
	private val dashboardUseCase = GetDashboardUseCase()

	fun getDashboards() {
		viewModelScope.launch {
			dashboardUseCase.getDashboards()
		}
	}
}