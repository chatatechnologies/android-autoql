package chata.can.chata_ai.retrofit.ui.viewModel

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.retrofit.data.model.NotificationModel
import chata.can.chata_ai.retrofit.domain.GetNotificationUseCase
import chata.can.chata_ai.retrofit.ui.view.NotificationRecyclerAdapter
import kotlinx.coroutines.launch

class NotificationViewModel: ViewModel() {
	val notificationList = MutableLiveData<List<NotificationModel>>()
	val totalItems = MutableLiveData<Int>()

	private var notificationRecyclerAdapter: NotificationRecyclerAdapter? = null

	var getNotificationUseCase = GetNotificationUseCase()

	fun onCreate() {
		viewModelScope.launch {
			val result = getNotificationUseCase()
			notificationList.postValue(result.items)
			totalItems.postValue(result.pagination.totalItems)
		}
	}

	fun getNotificationRecyclerAdapter(): NotificationRecyclerAdapter? {
		notificationRecyclerAdapter = NotificationRecyclerAdapter(
			this, R.layout.card_notification)
		return notificationRecyclerAdapter
	}

	fun setNotificationsInRecyclerAdapter(aNotification: List<NotificationModel>) {
		notificationRecyclerAdapter?.setNotifications(aNotification)
		notificationRecyclerAdapter?.notifyItemRangeChanged(0, aNotification.size)
	}

	fun getTextColorPrimary(): Int {
		return ThemeColor.currentColor.pDrawerTextColorPrimary
	}

	fun getNotificationAt(position: Int): NotificationModel? {
		val aNotification = notificationList.value
		return aNotification?.get(position)
	}
}