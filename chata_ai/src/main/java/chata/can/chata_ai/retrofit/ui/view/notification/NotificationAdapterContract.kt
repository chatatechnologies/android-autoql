package chata.can.chata_ai.retrofit.ui.view.notification

import chata.can.chata_ai.retrofit.NotificationEntity

interface NotificationAdapterContract {
	fun getLastOpen(): Int
	fun setLastOpen(position: Int)
	fun getNotificationAt(position: Int): NotificationEntity
	fun notifyItemChangedAdapter(position: Int)
}