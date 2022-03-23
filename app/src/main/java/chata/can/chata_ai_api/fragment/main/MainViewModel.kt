package chata.can.chata_ai_api.fragment.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.QueryBuilderData
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.domain.GetJwtUseCase
import chata.can.chata_ai.retrofit.domain.GetLoginUseCase
import chata.can.chata_ai.retrofit.domain.GetRelatedQueryTestUseCase
import chata.can.chata_ai.retrofit.domain.GetTopicUseCase
import chata.can.chata_ai_api.R
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
	val isAuthenticate = MutableLiveData<Boolean>()
	val isEnableLogin = MutableLiveData<Boolean>()
	val updateShowAlert = MutableLiveData< Pair<String, Int> >()

	private val getLoginUseCase = GetLoginUseCase()
	private val getJwtUseCase = GetJwtUseCase()
	private val relatedQueryTestUseCase = GetRelatedQueryTestUseCase()
	private val topicUseCase = GetTopicUseCase()

	fun login() {
		viewModelScope.launch {
			//cal post login
			val token = getLoginUseCase.postLogin(AutoQLData.username, AutoQLData.password)
			AutoQLData.token = token
			//call get jwt
			AutoQLData.JWT = getJwtUseCase.callJwt()
			//save preferences
			relatedQueryTestUseCase.getRelatedQueryTest()?.let {
				val items = topicUseCase.getTopics(AutoQLData.apiKey, AutoQLData.projectId)

				//region fill data Query Builder
				val aMainData = QueryBuilderData.aMainData
				aMainData.clear()
				val mMainQuery = QueryBuilderData.mMainQuery
				mMainQuery.clear()

				for (item in items) {
					val topic = item.topic
					aMainData.add(topic)
					val listQueries = arrayListOf("💡See more...")
					listQueries.addAll(item.queries)
					mMainQuery[topic] = listQueries
				}
				//endregion
				AutoQLData.wasLoginIn = true
				//region live data control
				isAuthenticate.postValue(true)
				isEnableLogin.postValue(true)
				updateShowAlert.postValue(Pair("Login Successful", R.drawable.ic_done))
				//endregion
			}
		}
	}
}