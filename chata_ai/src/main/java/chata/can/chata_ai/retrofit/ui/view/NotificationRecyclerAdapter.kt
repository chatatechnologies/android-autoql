package chata.can.chata_ai.retrofit.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.retrofit.data.model.NotificationModel
import chata.can.chata_ai.retrofit.ui.viewModel.NotificationViewModel

class NotificationRecyclerAdapter(
	private val notificationViewModel: NotificationViewModel,
	private val resource: Int
): RecyclerView.Adapter<NotificationHolder>() {

	private var aNotifications: List<NotificationModel>? = null

	fun setNotifications(notifications: List<NotificationModel>) {
		this.aNotifications = notifications
	}

	override fun getItemCount(): Int {
		return aNotifications?.size ?: 0
	}

	override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
		holder.setDataCard(notificationViewModel, position)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding: ViewDataBinding = DataBindingUtil.inflate(
			layoutInflater, resource, parent, false
		)
		return NotificationHolder(binding)
	}
}