package chata.can.chata_ai.retrofit.ui.view.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.retrofit.NotificationEntity
import chata.can.chata_ai.retrofit.core.OnBottomReachedListener

class NotificationRecyclerAdapter(
	private val aNotifications: MutableList<NotificationEntity>,
	private val resource: Int,/*R.layout.card_notification*/
	onBottomListener: () -> Unit
): RecyclerView.Adapter<NotificationHolder>(), NotificationAdapterContract {

	private var lastOpenRuleQuery = -1
	private val notificationUi: NotificationUi = NotificationUi()

	private var onBottomReachedListener = object: OnBottomReachedListener {
		override fun onBottomReachedListener(position: Int) {
			onBottomListener()
		}
	}

	override fun getItemCount(): Int {
		return aNotifications.size
	}

	override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
		if (position == aNotifications.size - 1) {
			onBottomReachedListener.onBottomReachedListener(position)
		}
		val notification = aNotifications[position]
		holder.render(notification, notificationUi, notificationAdapterContract = this)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
		val inflater = LayoutInflater.from(parent.context)
		return NotificationHolder(
			inflater.inflate(resource, parent, false)
		)
	}

	override fun getLastOpen(): Int = lastOpenRuleQuery

	override fun setLastOpen(position: Int) {
		lastOpenRuleQuery = position
	}

	override fun getNotificationAt(position: Int): NotificationEntity {
		return aNotifications[position]
	}

	override fun notifyItemChangedAdapter(position: Int) {
		notifyItemChanged(position)
	}
}