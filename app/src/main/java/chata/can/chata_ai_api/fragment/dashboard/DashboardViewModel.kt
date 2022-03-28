package chata.can.chata_ai_api.fragment.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.Executor
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.dashboard.dashboardItemDataToEntity
import chata.can.chata_ai_api.domain.GetDashboardUseCase
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class DashboardViewModel: ViewModel() {
	val hasQueries = MutableLiveData<Boolean>()

	private val dashboardUseCase = GetDashboardUseCase()

	private var mModel = mutableListOf<Dashboard>()

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

					val mModel = mutableListOf<Dashboard>()
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
				hasQueries.postValue(true)
			}).execute()
		}
	}

	fun getQueryDashboard() {
		//region presenter.updateModel()
		mModel = SinglentonDashboard.getCurrentDashboard()

		viewModelScope.launch {

		}
	}

	private fun getDataQuery(dashboard: Dashboard, wantSplitView: Boolean): HashMap<String, Any>
	{
		with(dashboard)
		{
			val mInfoHolder = hashMapOf<String, Any>(
				"key" to key,
				"isSplitView" to splitView,
				"title" to title,
				"nameService" to "getDashboardQueries")

			if (wantSplitView && splitView)
			{
				val secondQuery = secondQuery.ifEmpty { query }
				mInfoHolder["isSecondaryQuery"] = true
				mInfoHolder["primaryQuery"] = query
				mInfoHolder["query"] = secondQuery
			}
			else
			{
				mInfoHolder["query"] = query
				if (value.isNotEmpty() && valueLabel.isNotEmpty())
				{
					val mUserSelection = hashMapOf(
						"value" to value,
						"value_label" to valueLabel,
						"canonical" to "ORIGINAL_TEXT")
					mInfoHolder["user_selection"] = mUserSelection
				}
			}
			return mInfoHolder
		}
	}
}