package chata.can.chata_ai.fragment.notification

import chata.can.chata_ai.fragment.notification.model.Notification

interface NotificationContract
{
	fun showItem(position: Int)
	fun showNotifications(totalPages: Int, aNotification: ArrayList<Notification>)
}