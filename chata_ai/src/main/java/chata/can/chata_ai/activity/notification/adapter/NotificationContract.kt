package chata.can.chata_ai.activity.notification.adapter

interface NotificationContract
{
	fun showLoading()
	fun showText(text: String, textSize: Float)
}