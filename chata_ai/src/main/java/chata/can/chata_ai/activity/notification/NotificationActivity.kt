package chata.can.chata_ai.activity.notification

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.notification.model.Notification
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.base.BaseActivity

class NotificationActivity: BaseActivity(R.layout.activity_notification), NotificationContract
{
	private lateinit var rvNotification: RecyclerView
	private val model = BaseModelList<Notification>()
	private lateinit var presenter: NotificationPresenter

	override fun onCreateView()
	{
		initViews()

		getNotifications()
	}

	private fun initViews()
	{
		rvNotification = findViewById(R.id.rvNotification)

		presenter = NotificationPresenter(this)

		rvNotification.layoutManager = LinearLayoutManager(this)
//		rvNotification.adapter = adapter
	}

	override fun showNotifications(aNotification: ArrayList<Notification>)
	{
		model.clear()
		model.addAll(aNotification)
//		adapter.notifyDataSetChanged()
	}

	fun getNotifications()
	{
		presenter.getNotifications()
	}
}