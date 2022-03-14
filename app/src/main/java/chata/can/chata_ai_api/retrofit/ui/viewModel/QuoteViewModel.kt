package chata.can.chata_ai_api.retrofit.ui.viewModel

import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai_api.retrofit.data.model.QuoteModel
import chata.can.chata_ai_api.retrofit.domain.GetQuotesUseCase
import kotlinx.coroutines.launch

class QuoteViewModel: ViewModel() {
//	val quoteModel = MutableLiveData<QuoteModel>()
//	val isLoading = MutableLiveData<Boolean>()

	val manageNum = ManageNum(0)

//	var getQuotesUseCase = GetQuotesUseCase()

	fun onCreate() {
		viewModelScope.launch {

//			isLoading.postValue(true)
//			val result = getQuotesUseCase()
//
//			if (!result.isNullOrEmpty()) {
//				quoteModel.postValue(result[0])
//				isLoading.postValue(false)
//			}
		}
	}
}

class ManageNum(value: Int): ObservableInt(value) {
	fun increment() {
		set(get() + 1)
	}

	fun decrement() {
		set(get() - 1)
	}
}