package chata.can.chata_ai.retrofit.ui.view

import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BR
import chata.can.chata_ai.databinding.CardNotificationBinding
import chata.can.chata_ai.retrofit.ui.viewModel.NotificationViewModel

class NotificationHolder(_binding: CardNotificationBinding): RecyclerView.ViewHolder(_binding.root) {
	private var binding: CardNotificationBinding? = null
	init {
		binding = _binding
	}

	fun setDataCard(notificationViewModel: NotificationViewModel, position: Int) {
		binding?.run {
			setVariable(BR.model, notificationViewModel)
			setVariable(BR.position, position)
			ivTop.viewTreeObserver.addOnGlobalLayoutListener {
				val params = iView.layoutParams
				params.height = ivTop.measuredHeight
				iView.layoutParams = params
			}
			executePendingBindings()
		}
	}
}