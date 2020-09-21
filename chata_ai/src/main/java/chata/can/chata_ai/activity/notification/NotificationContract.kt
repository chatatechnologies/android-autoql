package chata.can.chata_ai.activity.notification

import chata.can.chata_ai.activity.notification.model.Notification

interface NotificationContract
{
	fun showNotifications(aNotification: ArrayList<Notification>)
}