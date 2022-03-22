package chata.can.chata_ai.retrofit.ui.view.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.retrofit.core.OnBottomReachedListener
import chata.can.chata_ai.retrofit.ui.viewModel.NotificationViewModel

class NotificationRecyclerAdapter(
	private val notificationViewModel: NotificationViewModel,
	private val resource: Int,/*R.layout.card_notification*/
	onBottomListener: () -> Unit
): RecyclerView.Adapter<NotificationHolder>() {

	private var onBottomReachedListener = object: OnBottomReachedListener {
		override fun onBottomReachedListener(position: Int) {
			onBottomListener()
		}
	}

	private fun getNotifications() = this.notificationViewModel.aNotifications

	override fun getItemCount(): Int {
		return getNotifications().size
	}

	override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
		if (position == getNotifications().size - 1) {
			onBottomReachedListener.onBottomReachedListener(position)
		}
		val notification = getNotifications()[position]
		holder.render(notification)
//		holder.setDataCard(notificationViewModel, position)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
		val inflater = LayoutInflater.from(parent.context)
		return NotificationHolder(
			inflater.inflate(resource, parent, false)
		)
//		val layoutInflater = LayoutInflater.from(parent.context)
//		val binding: CardNotificationBinding = DataBindingUtil.inflate(
//			layoutInflater, resource, parent, false
//		)
//		return NotificationHolder(binding)
	}
}