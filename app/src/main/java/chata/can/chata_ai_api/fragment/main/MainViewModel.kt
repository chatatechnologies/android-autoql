package chata.can.chata_ai_api.fragment.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.retrofit.domain.GetLoginUseCase
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
	private val getLoginUseCase = GetLoginUseCase()

	fun login() {
		viewModelScope.launch {
			val token = getLoginUseCase.postLogin(AutoQLData.username, AutoQLData.password)
			if (token.isEmpty()) {
				//notSession
			} else {
				AutoQLData.token = token
				//
			}

		}
	}
}