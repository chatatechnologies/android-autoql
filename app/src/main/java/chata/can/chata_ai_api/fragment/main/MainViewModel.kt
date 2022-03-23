package chata.can.chata_ai_api.fragment.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.data.model.relatedQueries.emptyRelatedQueryData
import chata.can.chata_ai.retrofit.domain.GetJwtUseCase
import chata.can.chata_ai.retrofit.domain.GetLoginUseCase
import chata.can.chata_ai.retrofit.domain.GetRelatedQueryTestUseCase
import chata.can.chata_ai.retrofit.domain.GetTopicUseCase
import chata.can.chata_ai_api.R
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
	val isSavingPersistence = MutableLiveData<Boolean>()
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
			if (token.isEmpty()) {
				notSession()
			} else {
				AutoQLData.token = token
				callJwt()
			}
		}
	}

	private fun callJwt() {
		viewModelScope.launch {
			val jwt = getJwtUseCase.callJwt()
			if (jwt.isEmpty()) {
				notSession()
			} else {
				AutoQLData.JWT = jwt
				callRelatedQueries()
			}
		}
	}

	private fun callRelatedQueries() {
		viewModelScope.launch {
			val relatedQueryTest = relatedQueryTestUseCase.getRelatedQueryTest()
			if (relatedQueryTest != emptyRelatedQueryData()) {
				isSavingPersistence.postValue(true)
				topicUseCase.getTopics(AutoQLData.apiKey, AutoQLData.projectId)
				AutoQLData.wasLoginIn = true
				//region live data control
				isAuthenticate.postValue(true)
				isEnableLogin.postValue(true)
				updateShowAlert.postValue(Pair("Login Successful", R.drawable.ic_done))
				//endregion
			} else {
				notSession()
			}
		}
	}

	private fun notSession() {
		//When data is wrong
		isAuthenticate.postValue(false)
		isEnableLogin.postValue(true)
		updateShowAlert.postValue(Pair("Invalid Credentials", R.drawable.ic_error))
	}
}