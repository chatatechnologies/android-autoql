package chata.can.chata_ai.retrofit.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.Executor
import chata.can.chata_ai.R
import chata.can.chata_ai.retrofit.NotificationEntity
import chata.can.chata_ai.retrofit.domain.GetNotificationUseCase
import chata.can.chata_ai.retrofit.notificationModelToEntity
import chata.can.chata_ai.retrofit.ui.view.notification.NotificationRecyclerAdapter
import kotlinx.coroutines.launch

class NotificationViewModel: ViewModel() {
	val notificationList = MutableLiveData<List<NotificationEntity>>()
	val totalItems = MutableLiveData<Int>()

	private var aNotifications = mutableListOf<NotificationEntity>()

	private var totalPages = 0
	private var countPages = 1

	private var notificationRecyclerAdapter: NotificationRecyclerAdapter? = null

	private var getNotificationUseCase = GetNotificationUseCase()

	fun onCreate(offset: Int = 0) {
		viewModelScope.launch {
			val newList = mutableListOf<NotificationEntity>()
			val result = getNotificationUseCase.getNotifications(offset)

			Executor({
				result.items.forEach { notificationModel ->
					newList.add(notificationModel.notificationModelToEntity())
				}
			}, {
				notificationList.postValue(newList)
				totalPages = result.pagination.totalItems
				totalItems.postValue( totalPages )
			}).execute()
		}
	}

	fun getNotificationRecyclerAdapter(): NotificationRecyclerAdapter? {
		notificationRecyclerAdapter = NotificationRecyclerAdapter(
			aNotifications,
			R.layout.card_notification
		) {
			if (countPages < totalPages) {
				onCreate(countPages++ * 10)
			}
		}
		return notificationRecyclerAdapter
	}

	fun setNotificationsInRecyclerAdapter(aNotification: List<NotificationEntity>) {
		notificationRecyclerAdapter?.let {
			aNotifications.addAll(aNotification)
			it.notifyItemRangeChanged(0, aNotification.size)
		}
	}
}