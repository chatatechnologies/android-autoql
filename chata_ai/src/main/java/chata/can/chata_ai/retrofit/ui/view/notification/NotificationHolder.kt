package chata.can.chata_ai.retrofit.ui.view.notification

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.databinding.CardNotificationBinding
import chata.can.chata_ai.retrofit.NotificationEntity
import chata.can.chata_ai.retrofit.core.extension.customVisibility
import chata.can.chata_ai.retrofit.core.extension.isVisible

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

			tvTitle.text = notificationEntity.title
			tvTitle.setTextColor(notificationEntity.getColorTitle())

			tvBody.text = notificationEntity.message
			tvBody.customVisibility(notificationEntity.isVisibleMessage())
			tvBody.setTextColor(notificationUi.getTextColorPrimary())

			tvDate.text = notificationEntity.createdAtFormatted()
			tvDate.setTextColor(notificationUi.getTextColorPrimary())

			rlBottom.customVisibility(notificationEntity.isBottomVisible)

			tvQuery.text = notificationEntity.query
			tvQuery.setTextColor(notificationUi.getTextColorPrimary())

			rlLoad.customVisibility(notificationEntity.isVisibleLoading)

			tvContent.text = notificationEntity.contentTextView.contentResponse
			tvContent.customVisibility(notificationEntity.isVisibleTextView())
			tvContent.setTextColor(notificationUi.getTextColorPrimary())

			wbQuery.customVisibility(notificationEntity.isVisibleWebView())
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
				}

				notificationAdapterContract.notifyItemChangedAdapter(adapterPosition)
			}
			//endregion
		}
	}
}