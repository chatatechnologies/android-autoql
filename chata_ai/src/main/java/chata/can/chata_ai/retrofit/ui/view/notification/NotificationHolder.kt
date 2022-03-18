package chata.can.chata_ai.retrofit.ui.view.notification

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BR
import chata.can.chata_ai.databinding.CardNotificationBinding
import chata.can.chata_ai.retrofit.ui.viewModel.NotificationViewModel

class NotificationHolder(_binding: CardNotificationBinding): RecyclerView.ViewHolder(_binding.root) {
	private var binding: CardNotificationBinding = _binding

	fun setDataCard(notificationViewModel: NotificationViewModel, position: Int) {
		binding.run {
			setVariable(BR.model, notificationViewModel)
			setVariable(BR.position, position)
			rlBottom.viewTreeObserver.addOnGlobalLayoutListener {
				val params = iView.layoutParams
				val bottomHeight = if (rlBottom.visibility == View.VISIBLE) rlBottom.measuredHeight else 0
				params.height = ivTop.measuredHeight + bottomHeight
				iView.layoutParams = params
			}
			executePendingBindings()
		}
	}
}