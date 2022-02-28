package chata.can.chata_ai.retrofit.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import chata.can.chata_ai.R
import chata.can.chata_ai.databinding.FragmentNotificationBinding
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.extension.textSize
import chata.can.chata_ai.fragment.notification.NotificationContract
import chata.can.chata_ai.fragment.notification.model.Notification
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.retrofit.ui.viewModel.NotificationViewModel

class NotificationFragment: Fragment(), NotificationContract {
	companion object {
		const val nameFragment = "Notifications"
	}

	private var notificationViewModel: NotificationViewModel ?= null
	private var fragmentNotificationFragmentBinding: FragmentNotificationBinding? = null
	private var backTotalItems = 0

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		fragmentNotificationFragmentBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_notification,
			container,
			false
		)
		notificationViewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
		fragmentNotificationFragmentBinding?.model = notificationViewModel
		return fragmentNotificationFragmentBinding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setColors()
		initObserve()
		fragmentNotificationFragmentBinding?.run {
			if (AutoQLData.wasLoginIn) {
				btnTry.visibility = View.GONE
			} else {
				val msg = "No notification yet.\nStay tuned!"
				tvLoading.text = msg
				ThemeColor.currentColor.run {
					btnTry.run {
						context.run {
							setTextColor(pDrawerTextColorPrimary)
							visibility = View.VISIBLE
						}
					}
				}
			}
			ThemeColor.aColorMethods[nameFragment] = {
				setColors()
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		ThemeColor.aColorMethods.remove(nameFragment)
	}

	override fun showItem(position: Int) {
		Handler(Looper.getMainLooper()).postDelayed({
			fragmentNotificationFragmentBinding?.rvNotification?.smoothScrollToPosition(position)
		}, 200)
	}

	override fun showNotifications(totalPages: Int, aNotification: ArrayList<Notification>) {

	}

	private fun initObserve() {
		notificationViewModel?.run {
			onCreate()

			notificationList.observe(viewLifecycleOwner) { listNotification ->
				if (listNotification.isNotEmpty()) {
					notificationViewModel?.setNotificationsInRecyclerAdapter(listNotification)
					showRecyclerView()
				} else showMessage()
			}
			totalItems.observe(viewLifecycleOwner) {
				backTotalItems = it
			}
		}
	}

	private fun setColors() {
		ThemeColor.currentColor.run {
			fragmentNotificationFragmentBinding?.run {
				llParent.setBackgroundColor(pDrawerBackgroundColor)
				rvNotification.setBackgroundColor(pDrawerColorSecondary)
				tvLoading.setTextColor(pDrawerTextColorPrimary)
				tvMsg1.setTextColor(pDrawerTextColorPrimary)
			}
		}
	}

	private fun showMessage() {
		fragmentNotificationFragmentBinding?.run {
			llParent.paddingAll(top = 80f)
			iv1.visibility = View.VISIBLE
			tvMsg1.visibility = View.VISIBLE
			tvLoading.textSize(18f)
			tvLoading.text = getString(R.string.empty_notification)
		}
	}

	private fun showRecyclerView() {
		fragmentNotificationFragmentBinding?.run {
			rvNotification.visibility = View.VISIBLE
			tvLoading.visibility = View.GONE
		}
	}
}