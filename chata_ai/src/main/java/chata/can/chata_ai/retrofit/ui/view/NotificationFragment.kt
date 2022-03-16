package chata.can.chata_ai.retrofit.ui.view

import android.os.Bundle
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
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.ui.viewModel.NotificationViewModel

class NotificationFragment: Fragment() {
	companion object {
		const val nameFragment = "Notifications"
	}

	private var notificationViewModel: NotificationViewModel ?= null
	private var fragmentNotificationFragmentBinding: FragmentNotificationBinding? = null
	//totalItems
	private var totalPages = 0

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
	}

	override fun onDestroy() {
		super.onDestroy()
		ThemeColor.aColorMethods.remove(nameFragment)
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
				totalPages = it
			}
		}
	}

	private fun setColors() {
		ThemeColor.currentColor.run {
			fragmentNotificationFragmentBinding?.run {
				llParent.setBackgroundColor(pDrawerBackgroundColor)
				rvNotification.setBackgroundColor(pDrawerColorSecondary)
				rvNotification.itemAnimator = null
				tvLoading.setTextColor(pDrawerTextColorPrimary)
				tvMsg1.setTextColor(pDrawerTextColorPrimary)
				txtTry.setTextColor(pDrawerTextColorPrimary)
				txtTry.background = DrawableBuilder.setGradientDrawable(
					pDrawerBackgroundColor,
					12f,
					3,
					pDrawerBorderColor)
			}
		}
	}

	private fun showMessage() {
		fragmentNotificationFragmentBinding?.run {
			if (AutoQLData.wasLoginIn) {
				llParent.paddingAll(top = 80f)
				iv1.visibility = View.VISIBLE
				tvLoading.visibility = View.VISIBLE
				tvMsg1.visibility = View.VISIBLE
				tvLoading.textSize(18f)
				tvLoading.setText(R.string.empty_notification)
			} else {
				iv1.visibility = View.GONE
				tvMsg1.visibility = View.GONE
				txtTry.visibility = View.VISIBLE
				val msg = "Oh no! Something went wrong while accessing your notifications."
				tvLoading.text = msg
			}
		}
	}

	private fun showRecyclerView() {
		fragmentNotificationFragmentBinding?.run {
			rvNotification.visibility = View.VISIBLE
			llParent.visibility = View.GONE
		}
	}
}