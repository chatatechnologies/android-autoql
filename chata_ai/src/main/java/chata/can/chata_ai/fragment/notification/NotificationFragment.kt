package chata.can.chata_ai.fragment.notification

import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.fragment.notification.adapter.NotificationAdapter
import chata.can.chata_ai.fragment.notification.model.Notification
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs

class NotificationFragment: BaseFragment(), NotificationContract
{
	companion object {
		const val nameFragment = "Notifications"
		fun newInstance() = NotificationFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_notifications)
		}
	}

	private lateinit var llParent: View
	private lateinit var iv1: ImageView
	private lateinit var tvLoading: TextView
	private lateinit var btnTry: Button
	private lateinit var tvMsg1: TextView
	private lateinit var rvNotification: RecyclerView
	private val model = BaseModelList<Notification>()
	private lateinit var adapter: NotificationAdapter
	private lateinit var presenter: NotificationPresenter
	private var totalPages = 0
	private var countPages = 1

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		if (AutoQLData.wasLoginIn)
		{
			btnTry.visibility = View.GONE
			getNotifications()
		}
		else
		{
			val msg = "No notification yet.\nStay tuned!"
			tvLoading.text = msg
			ThemeColor.currentColor.run {
			btnTry.run {
				context.run {
						background = DrawableBuilder.setGradientDrawable(
							pDrawerBackgroundColor,
							12f,
							3,
							pDrawerBorderColor)
					}
					setTextColor(pDrawerTextColorPrimary)
					visibility = View.VISIBLE
				}
			}
		}
		ThemeColor.aColorMethods[nameFragment] = {
			setColors()
			adapter.notifyItemRangeChanged(0, model.countData() - 1)
		}
	}

	override fun initListener() {}

	override fun initViews(view: View)
	{
		activity?.let {
			llParent = view.findViewById(R.id.llParent)
			iv1 = view.findViewById(R.id.iv1)
			tvLoading = view.findViewById(R.id.tvLoading)
			btnTry = view.findViewById(R.id.btnTry)
			tvMsg1 = view.findViewById(R.id.tvMsg1)
			rvNotification = view.findViewById(R.id.rvNotification)
			presenter = NotificationPresenter(this)
			adapter = NotificationAdapter(model, this) {
				if (countPages < totalPages)
				{
					presenter.getNotifications(countPages++ * 10)
				}
			}
			rvNotification.layoutManager = LinearLayoutManager(it)
			rvNotification.adapter = adapter
			rvNotification.itemAnimator = null
		}
	}

	override fun setColors()
	{
		with(ThemeColor.currentColor)
		{
			activity?.let {
				llParent.setBackgroundColor(pDrawerBackgroundColor)
				rvNotification.setBackgroundColor(pDrawerColorSecondary)
				tvLoading.setTextColor(pDrawerTextColorPrimary)
				tvMsg1.setTextColor(pDrawerTextColorPrimary)
			}
		}
	}

	override fun showNotifications(totalPages: Int, aNotification: ArrayList<Notification>) = if (totalPages == 0)
	{
		llParent.run {
			setPadding(0, dpToPx(80f), 0,0)
		}
		iv1.visibility = View.VISIBLE
		tvMsg1.visibility = View.VISIBLE
		val msgEmpty = getString(R.string.empty_notification)
		tvLoading.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
		tvLoading.text = msgEmpty
	}
	else
	{
		rvNotification.visibility = View.VISIBLE
		this.totalPages = totalPages
		model.addAll(aNotification)
		tvLoading.visibility = View.GONE
		adapter.notifyItemRangeChanged(0, model.countData() - 1)
	}

	override fun onDestroy()
	{
		super.onDestroy()
		ThemeColor.aColorMethods.remove(nameFragment)
	}

	private fun getNotifications()
	{
		rvNotification.visibility = View.GONE
		presenter.getNotifications()
	}

	override fun showItem(position: Int)
	{
		Handler(Looper.getMainLooper()).postDelayed({
			rvNotification.smoothScrollToPosition(position)
		}, 200)
	}
}