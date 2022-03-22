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

	fun render(notificationEntity: NotificationEntity) {
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
			//region drawable & colors
			iView.background = notificationEntity.getDrawableLeftView()

			tvTitle.text = notificationEntity.title
			tvTitle.setTextColor(notificationEntity.getColorTitle())

			tvBody.text = notificationEntity.message
			tvBody.customVisibility(notificationEntity.isVisibleMessage())

			tvDate.text = notificationEntity.createdAtFormatted()

			rlBottom.customVisibility(notificationEntity.isBottomVisible)

			tvQuery.text = notificationEntity.query

			rlLoad.customVisibility(notificationEntity.isVisibleLoading)

			tvContent.text = notificationEntity.contentTextView.contentResponse
			tvContent.customVisibility(notificationEntity.isVisibleTextView())

			wbQuery.customVisibility(notificationEntity.isVisibleWebView())

			//endregion
		}
	}
}