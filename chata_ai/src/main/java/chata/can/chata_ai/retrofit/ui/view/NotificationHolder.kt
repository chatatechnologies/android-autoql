package chata.can.chata_ai.retrofit.ui.view

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BR
import chata.can.chata_ai.retrofit.ui.viewModel.NotificationViewModel

class NotificationHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
	private var binding: ViewDataBinding? = null
	init {
		this.binding = binding
	}

	fun setDataCard(notificationViewModel: NotificationViewModel, position: Int) {
		binding?.run {
			setVariable(BR.model, notificationViewModel)
			setVariable(BR.position, position)
			executePendingBindings()
		}
	}
}