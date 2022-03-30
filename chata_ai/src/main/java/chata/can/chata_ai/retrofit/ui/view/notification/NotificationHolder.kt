package chata.can.chata_ai.retrofit.ui.view.notification

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.databinding.CardNotificationBinding
import chata.can.chata_ai.retrofit.NotificationEntity
import chata.can.chata_ai.retrofit.core.extension.customVisibility
import chata.can.chata_ai.retrofit.core.extension.isVisible
import chata.can.chata_ai.retrofit.data.model.ruleQuery.RuleQueryResponse
import kotlinx.coroutines.runBlocking

class NotificationHolder(
	view: View
): RecyclerView.ViewHolder(view) {

	private val binding = CardNotificationBinding.bind(view)

	fun render(
		notificationEntity: NotificationEntity,
		notificationUi: NotificationUi,
		notificationAdapterContract: NotificationAdapterContract
	) {
		binding.run {
			//region Tree Observer
			rlBottom.viewTreeObserver.addOnGlobalLayoutListener {
				val params = iView.layoutParams
				val bottomHeight = if (rlBottom.isVisible())
					rlBottom.measuredHeight
				else 0
				params.height = ivTop.measuredHeight + bottomHeight
				iView.layoutParams = params
			}
			//endregion
			//region drawable / colors / data
			rlParent.background = notificationUi.getDrawableParent()

			iView.background = notificationEntity.getDrawableLeftView()
			iView2.setBackgroundColor(notificationUi.getTextColorPrimary())
			iView3.setBackgroundColor(notificationUi.getTextColorPrimary())

			tvTitle.run {
				text = notificationEntity.title
				setTextColor(notificationEntity.getColorTitle())
			}

			tvBody.run {
				text = notificationEntity.message
				customVisibility(notificationEntity.isVisibleMessage())
				setTextColor(notificationUi.getTextColorPrimary())
			}

			tvDate.run {
				text = notificationEntity.createdAtFormatted()
				setTextColor(notificationUi.getTextColorPrimary())
			}

			rlBottom.customVisibility(notificationEntity.isBottomVisible)

			tvQuery.run {
				text = notificationEntity.query
				setTextColor(notificationUi.getTextColorPrimary())
			}

			rlLoad.customVisibility(notificationEntity.isVisibleLoading)

			tvContent.run {
				text = notificationEntity.contentTextView.contentResponse
				customVisibility(notificationEntity.isVisibleTextView())
				setTextColor(notificationUi.getTextColorPrimary())
			}

			wbQuery.run {
				customVisibility(notificationEntity.isVisibleWebView())
				loadDataWithBaseURL(
					null,
					notificationEntity.contentWebView.contentResponse,
					"text/html",
					"UTF-8",
					null
				)
			}
			//endregion

			//region listener
			ivTop.setOnClickListener {
				notificationEntity.isBottomVisible = !notificationEntity.isBottomVisible
				if (notificationEntity.isBottomVisible) {
					//region manage last query opened
					val lastOpenRuleQuery = notificationAdapterContract.getLastOpen()
					if (lastOpenRuleQuery != adapterPosition) {
						if (lastOpenRuleQuery >= 0) {
							notificationAdapterContract.getNotificationAt(lastOpenRuleQuery).isBottomVisible = false
							notificationAdapterContract.notifyItemChangedAdapter(lastOpenRuleQuery)
						}
						notificationAdapterContract.setLastOpen(adapterPosition)
					}
					//endregion
					//region process data
					if (!notificationEntity.hasData()) {
						runBlocking {
							val queryResultEntity = notificationUi.ruleQueryUseCase.getRuleQuery(notificationEntity.id)
							val resultRuleQuery = RuleQueryResponse.getRuleQueryResponse(queryResultEntity)

							notificationEntity.setData(resultRuleQuery)
							notificationEntity.isVisibleLoading = false
							notificationAdapterContract.notifyItemChangedAdapter(adapterPosition)
						}
					}
					//endregion
				}

				notificationAdapterContract.notifyItemChangedAdapter(adapterPosition)
			}
			//endregion
		}
	}
}