package chata.can.chata_ai.retrofit.ui.viewModel

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.data.model.NotificationModel
import chata.can.chata_ai.retrofit.domain.GetNotificationUseCase
import chata.can.chata_ai.retrofit.ui.view.NotificationRecyclerAdapter
import kotlinx.coroutines.launch

class NotificationViewModel: ViewModel() {
	val notificationList = MutableLiveData<List<NotificationModel>>()
	val totalItems = MutableLiveData<Int>()

	private var notificationRecyclerAdapter: NotificationRecyclerAdapter? = null

	private var white = 0
	private var gray = 0
	private var blue = 0

	init {
		ThemeColor.currentColor.run {
			white = pDrawerBackgroundColor
			gray = pDrawerTextColorPrimary
			blue = SinglentonDrawer.currentAccent
		}
	}

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

	fun getTextColorPrimary() = gray

	fun getDrawableParent(): GradientDrawable {
		return DrawableBuilder.setGradientDrawable(white, 18f,0, gray)
	}

	fun getDrawableLeftView(position: Int): GradientDrawable? {
		val aNotifications = notificationList.value
		return aNotifications?.get(position)?.let { notification ->
			val color = if (notification.state == "DISMISSED") Color.TRANSPARENT else blue
			DrawableBuilder.setGradientDrawable(
				color,
				aCornerRadius = floatArrayOf(15f, 15f, 0f, 0f, 0f, 0f, 15f, 15f)
			)
		}
	}

	fun getWidthLeft(): Int {
		return 12
	}

	fun getHeightLeft(): Int {
		return 20
	}

	fun getNotificationAt(position: Int): NotificationModel? {
		val aNotification = notificationList.value
		return aNotification?.get(position)
	}
}