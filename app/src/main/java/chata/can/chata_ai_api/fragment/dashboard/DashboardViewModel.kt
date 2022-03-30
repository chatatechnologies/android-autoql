package chata.can.chata_ai_api.fragment.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.Executor
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.dashboard.dashboardItemDataToEntity
import chata.can.chata_ai.retrofit.data.model.query.QueryResponse
import chata.can.chata_ai.retrofit.data.model.query.queryResponseDataToQueryEntity
import chata.can.chata_ai.retrofit.domain.GetQueryDashboardUseCase
import chata.can.chata_ai_api.domain.GetDashboardUseCase
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class DashboardViewModel: ViewModel() {
	val hasQueries = MutableLiveData<Boolean>()
	val hasChangesGridAdapter = MutableLiveData<Boolean>()
	val positionNotify = MutableLiveData<Int>()

	private val dashboardUseCase = GetDashboardUseCase()
	private val queryDashboardUseCase = GetQueryDashboardUseCase()

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

		for (index in 0 until 1)//mModel.size) {
		mModel[index].let { dashboard: Dashboard ->
			dashboard.isWaitingData = toClearQuery
			dashboard.isWaitingData2 = toClearQuery
			dashboard.queryBase = null
			dashboard.queryBase2 = null

			getQuery(dashboard)
		}
		//notify loading on holders
		hasChangesGridAdapter.postValue(true)
	}

	private fun getQuery(dashboard: Dashboard) {
		val query = dashboard.query
		if (query.isNotEmpty()) {
			val mInfoHolder = getDataQuery(dashboard,false)
			val body = buildBodyQuery(query)
			viewModelScope.launch {
				val queryEntity = queryDashboardUseCase.getQueryDashboard(body)
				val resultQuery = QueryResponse.getQueryResponse(queryEntity)

				mModel[0].contentFromViewModel = resultQuery

				positionNotify.postValue(0)
			}
		}
		//TODO PENDING val secondQuery = dashboard.secondQuery
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

	private fun buildBodyQuery(query: String): JsonObject {
		return JsonObject().apply {
			addProperty("text", query)
			addProperty("test", true)
			addProperty("source", "dashboards.user")
			addProperty("translation", "include")
		}
	}
}