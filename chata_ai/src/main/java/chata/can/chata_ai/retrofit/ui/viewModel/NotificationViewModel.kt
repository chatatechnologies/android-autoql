package chata.can.chata_ai.retrofit.ui.viewModel

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.webkit.WebView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.Executor
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.NotificationEntity
import chata.can.chata_ai.retrofit.data.model.ruleQuery.RuleQueryResponse
import chata.can.chata_ai.retrofit.domain.GetNotificationUseCase
import chata.can.chata_ai.retrofit.domain.GetRuleQueryUseCase
import chata.can.chata_ai.retrofit.model.NotificationObservable
import chata.can.chata_ai.retrofit.notificationModelToEntity
import chata.can.chata_ai.retrofit.ui.view.NotificationRecyclerAdapter
import kotlinx.coroutines.launch

class NotificationViewModel: ViewModel() {
	//region notifications observable
	private var notificationObservable: NotificationObservable = NotificationObservable()

	fun callNotifications() {
		notificationObservable.callNotifications()
	}

	fun getNotifications(): MutableLiveData<List<NotificationEntity>> {
		return notificationObservable.getNotifications()
	}
	//endregion


	val notificationList = MutableLiveData<List<NotificationEntity>>()
	val totalItems = MutableLiveData<Int>()

	private var notificationRecyclerAdapter: NotificationRecyclerAdapter? = null

	private var lastOpenRuleQuery = -1
	private var blue = 0
	private var gray = 0
	private var white = 0

	init {
		ThemeColor.currentColor.run {
			blue = SinglentonDrawer.currentAccent
			gray = pDrawerTextColorPrimary
			white = pDrawerBackgroundColor
		}
	}

	var getNotificationUseCase = GetNotificationUseCase()
	private val ruleQueryUseCase = GetRuleQueryUseCase()

	fun onCreate() {
		viewModelScope.launch {
			val newList = mutableListOf<NotificationEntity>()
			val result = getNotificationUseCase()

			Executor({
				result.items.forEach { notificationModel ->
					newList.add(notificationModel.notificationModelToEntity())
				}
			}, {
				notificationList.postValue(newList)
				totalItems.postValue(result.pagination.totalItems)
			}).execute()
		}
	}

	fun getNotificationRecyclerAdapter(): NotificationRecyclerAdapter? {
		notificationRecyclerAdapter = NotificationRecyclerAdapter(
			this, R.layout.card_notification)
		return notificationRecyclerAdapter
	}

	fun setNotificationsInRecyclerAdapter(aNotification: List<NotificationEntity>) {
		notificationRecyclerAdapter?.let {
			it.setNotifications(aNotification)
			it.notifyItemRangeChanged(0, aNotification.size)
		}
	}

	fun getTextColorPrimary() = gray

	fun getDrawableParent(): GradientDrawable {
		return DrawableBuilder.setGradientDrawable(white, 18f,0, gray)
	}

	fun getNotificationAt(position: Int): NotificationEntity? {
//		val aNotification = notificationObservable.getNotifications().value
		val aNotification = notificationList.value
		return aNotification?.get(position)
	}

	fun changeVisibility(position: Int) {
		getNotificationAt(position)?.let { notification ->
			notification.isBottomVisible = !notification.isBottomVisible
			if (notification.isBottomVisible) {

				if (lastOpenRuleQuery != position) {
					//region last open rule Query is major that Zero
					if (lastOpenRuleQuery >= 0) {
						getNotificationAt(lastOpenRuleQuery)?.let {
							it.isBottomVisible = false
							notificationRecyclerAdapter?.notifyItemChanged(lastOpenRuleQuery)
						}
					}
					//endregion
					lastOpenRuleQuery = position
				}

				//region process data
				if (!notification.hasData()) {
					viewModelScope.launch {
						val queryResultEntity = ruleQueryUseCase.getRuleQuery(notification.id)
						val resultRuleQuery = RuleQueryResponse.getRuleQueryResponse(queryResultEntity)

						notificationRecyclerAdapter?.let {
							notification.setData(resultRuleQuery)
							notification.isVisibleLoading = false
							//async refresh from process data
							it.notifyItemChanged(position)
						}
					}
				}
				//endregion
			}
		}
		notificationRecyclerAdapter?.notifyItemChanged(position)
	}

	fun getUrl(position: Int): String {
		return getNotificationAt(position)?.contentWebView?.contentResponse ?: "no load"
	}

	companion object {
		@JvmStatic
		@BindingAdapter("loadData")
		fun setUrl(webView: WebView, webViewData: String) {
			webView.loadDataWithBaseURL(
				null,
				webViewData,
				"text/html",
				"UTF-8",
				null
			)
		}

		@JvmStatic
		@BindingAdapter("android:visibility")
		fun setVisibility(view: View, isVisible: Boolean) {
			view.visibility = if (isVisible) View.VISIBLE else View.GONE
		}
	}
}