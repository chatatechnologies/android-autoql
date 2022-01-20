package chata.can.chata_ai.retrofit.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
	val loginModel = MutableLiveData<String>()

//	var postLoginUseCase = PostLoginUseCase()

	fun onCreate() {
		viewModelScope.launch {
//			val result = postLoginUseCase("admin", "admin123")
//			result
		}
	}
}