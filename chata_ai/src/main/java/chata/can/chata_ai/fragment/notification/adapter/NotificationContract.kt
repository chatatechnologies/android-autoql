package chata.can.chata_ai.fragment.notification.adapter

interface NotificationContract
{
	fun showLoading()
	fun showText(text: String = "", textSize: Float, intRes: Int = 0)
}