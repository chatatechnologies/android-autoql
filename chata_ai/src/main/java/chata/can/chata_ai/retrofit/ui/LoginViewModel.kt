package chata.can.chata_ai.retrofit.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.retrofit.domain.PostLoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
	val loginModel = MutableLiveData<String>()

	var postLoginUseCase = PostLoginUseCase()

	fun onCreate() {
		viewModelScope.launch {
			val result = postLoginUseCase("admin", "admin123")

			result
		}
	}
}