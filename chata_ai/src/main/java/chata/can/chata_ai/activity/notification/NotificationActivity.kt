package chata.can.chata_ai.activity.notification

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.notification.adapter.NotificationAdapter
import chata.can.chata_ai.activity.notification.model.Notification
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.base.BaseActivity

class NotificationActivity: BaseActivity(R.layout.activity_notification), NotificationContract
{
	private lateinit var rvNotification: RecyclerView
	private val model = BaseModelList<Notification>()
	private lateinit var adapter: NotificationAdapter
	private lateinit var presenter: NotificationPresenter
	private var totalPages = 0
	private var countPages = 1

	override fun onCreateView()
	{
		initViews()

		getNotifications()
	}

	private fun initViews()
	{
		rvNotification = findViewById(R.id.rvNotification)

		presenter = NotificationPresenter(this)
		adapter = NotificationAdapter(model)
		rvNotification.layoutManager = LinearLayoutManager(this)
		rvNotification.adapter = adapter

		adapter.setOnBottomReachedListener(object: OnBottomReachedListener
		{
			override fun onBottomReachedListener(position: Int)
			{
				if (countPages < totalPages)
				{
					presenter.getNotifications(countPages++ * 10)
				}
			}
		})
	}

	override fun showNotifications(totalPages: Int, aNotification: ArrayList<Notification>)
	{
		this.totalPages = totalPages
		//model.clear()
		model.addAll(aNotification)
		adapter.notifyDataSetChanged()
	}

	private fun getNotifications()
	{
		presenter.getNotifications()
	}
}