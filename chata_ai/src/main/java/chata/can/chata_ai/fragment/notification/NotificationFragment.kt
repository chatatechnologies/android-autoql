package chata.can.chata_ai.fragment.notification

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.notification.NotificationContract
import chata.can.chata_ai.activity.notification.NotificationPresenter
import chata.can.chata_ai.activity.notification.adapter.NotificationAdapter
import chata.can.chata_ai.activity.notification.model.Notification
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.putArgs

class NotificationFragment: BaseFragment(), NotificationContract
{
	companion object {
		const val nameFragment = "Notifications"
		fun newInstance() = NotificationFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_notifications)
		}
	}

	private lateinit var tvLoading: TextView
	private lateinit var rvNotification: RecyclerView
	private val model = BaseModelList<Notification>()
	private lateinit var adapter: NotificationAdapter
	private lateinit var presenter: NotificationPresenter
	private var totalPages = 0
	private var countPages = 1

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		getNotifications()
		ThemeColor.aMethod[nameFragment] = {
			setColors()
			adapter.notifyDataSetChanged()
		}
	}

	override fun initListener() {}

	override fun initViews(view: View)
	{
		activity?.let {
			tvLoading = view.findViewById(R.id.tvLoading)
			rvNotification = view.findViewById(R.id.rvNotification)
			presenter = NotificationPresenter(this)
			adapter = NotificationAdapter(model) {
				if (countPages < totalPages)
				{
					presenter.getNotifications(countPages++ * 10)
				}
			}
			rvNotification.layoutManager = LinearLayoutManager(it)
			rvNotification.adapter = adapter
		}
	}

	override fun setColors()
	{
		with(ThemeColor.currentColor)
		{
			activity?.let {
				rvNotification.setBackgroundColor(it.getParsedColor(drawerColorSecondary))
				tvLoading.setBackgroundColor(it.getParsedColor(drawerColorSecondary))
				tvLoading.setTextColor(it.getParsedColor(drawerTextColorPrimary))
			}
		}
	}

	override fun showNotifications(totalPages: Int, aNotification: ArrayList<Notification>)
	{
		rvNotification.visibility = View.VISIBLE
		this.totalPages = totalPages
		model.addAll(aNotification)
		tvLoading.visibility = View.GONE
		adapter.notifyDataSetChanged()
	}

	private fun getNotifications()
	{
		rvNotification.visibility = View.GONE
		presenter.getNotifications()
	}
}