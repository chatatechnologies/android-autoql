package chata.can.chata_ai_api.screens

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

class InputConfigViewModel(context: Context) : ViewModel() {
	val isSavingPersistence: MutableState<Boolean> = mutableStateOf(false)
	val isAuthenticate: MutableState<Boolean> = mutableStateOf(false)
	val isEnableLogin: MutableState<Boolean> = mutableStateOf(true)
	val updateShowAlert: MutableState<Pair<String, Int>> = mutableStateOf(Pair("", 0))

	private val getLoginUseCase = GetLoginUseCase()
	private val getJwtUseCase = GetJwtUseCase()
	private val relatedQueryTestUseCase = GetRelatedQueryTestUseCase()
	private val topicUseCase = GetTopicUseCase()

	fun login() {
		viewModelScope.launch {
			val token: String = getLoginUseCase.postLogin(AutoQLData.username, AutoQLData.password)
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
				isSavingPersistence.value = true
				topicUseCase.getTopics(AutoQLData.apiKey, AutoQLData.projectId)
				AutoQLData.wasLoginIn = true
				//region live data control
				isAuthenticate.value = true
				isEnableLogin.value = true
				updateShowAlert.value = Pair("Login Successful", R.drawable.ic_done)
//				mToken.value = "_"
				//endregion
			} else {
				notSession()
			}
		}
	}

	private fun notSession() {
		isAuthenticate.value = false
		isEnableLogin.value = true
		updateShowAlert.value = Pair("Invalid Credentials", R.drawable.ic_error)
	}
}