package chata.can.chata_ai.activity.notification

import chata.can.chata_ai.activity.notification.model.Notification

interface NotificationContract
{
	fun showItem(position: Int)
	fun showNotifications(totalPages: Int, aNotification: ArrayList<Notification>)
}