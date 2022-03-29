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
	val notifyIndexQuery = MutableLiveData<Int>()

	private val dashboardUseCase = GetDashboardUseCase()

	private var mModel = mutableListOf<Dashboard>()

	fun getDashboards() {
		viewModelScope.launch {
			val dashboards = dashboardUseCase.getDashboards()

			Executor({
				dashboards.forEach { dashboardItem ->
					val aKeys = ArrayList< Pair <Int, Int> >()
					val mPartial = ConcurrentHashMap<String, Dashboard>()

					val idDashboard = dashboardItem.id
					val nameDashboard = dashboardItem.name
					val mModel = mutableListOf<Dashboard>()

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

	fun getQueryDashboard(toClearQuery: Boolean = true) {
		//region presenter.updateModel()
		mModel = SinglentonDashboard.getCurrentDashboard()

		viewModelScope.launch {
			for (index in 0 until mModel.size) {
				mModel[index].let { dashboard: Dashboard ->
					dashboard.isWaitingData = toClearQuery
					dashboard.isWaitingData2 = toClearQuery
					dashboard.queryBase = null
					dashboard.queryBase2 = null

					notifyIndexQuery.postValue(index)
				}
			}
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