package chata.can.chata_ai_api.fragment.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.domain.GetJwtUseCase
import chata.can.chata_ai.retrofit.domain.GetLoginUseCase
import chata.can.chata_ai.retrofit.domain.GetRelatedQueryTestUseCase
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
	private val getLoginUseCase = GetLoginUseCase()
	private val getJwtUseCase = GetJwtUseCase()
	private val relatedQueryTestUseCase = GetRelatedQueryTestUseCase()

	fun login() {
		viewModelScope.launch {
			//cal post login
			val token = getLoginUseCase.postLogin(AutoQLData.username, AutoQLData.password)
			AutoQLData.token = token
			//call get jwt
			AutoQLData.JWT = getJwtUseCase.callJwt()
			//save preferences
			relatedQueryTestUseCase.getRelatedQueryTest()?.let {
				""
			}
		}
	}
}