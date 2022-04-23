package chata.can.chata_ai.retrofit.ui.view.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import chata.can.chata_ai.compose.screens.NotificationViewModel
import chata.can.chata_ai.databinding.FragmentNotificationBinding
import chata.can.chata_ai.screens.notification.ContentNotification
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment: Fragment() {
	companion object {
		const val nameFragment = "Notifications"
	}

//	private val notificationViewModel: NotificationViewModel by viewModels()
	private lateinit var fragmentNotificationFragmentBinding: FragmentNotificationBinding
	//totalItems
	private var totalPages = 0

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		fragmentNotificationFragmentBinding = FragmentNotificationBinding.inflate(
			inflater,
			container,
			false
		)
//		fragmentNotificationFragmentBinding.model = notificationViewModel

		fragmentNotificationFragmentBinding.composeView.apply {
			setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
			setContent {
//				val viewModel: NotificationViewModel by viewModels()
				ContentNotification()
			}
		}

		return fragmentNotificationFragmentBinding.root
	}

//	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//		setColors()
//		initObserve()
//	}
//
//	override fun onDestroy() {
//		super.onDestroy()
//		ThemeColor.aColorMethods.remove(nameFragment)
//	}
//
//	private fun initObserve() {
//		notificationViewModel.run {
//			onCreate()
//
//			notificationList.observe(viewLifecycleOwner) { listNotification ->
//				if (listNotification.isNotEmpty()) {
//					notificationViewModel.setNotificationsInRecyclerAdapter(listNotification)
//					showRecyclerView()
//				} else showMessage()
//			}
//			totalItems.observe(viewLifecycleOwner) {
//				totalPages = it
//			}
//		}
//	}
//
//	private fun setColors() {
//		ThemeColor.currentColor.run {
//			fragmentNotificationFragmentBinding.run {
//				llParent.setBackgroundColor(pDrawerBackgroundColor)
//				rvNotification.setBackgroundColor(pDrawerColorSecondary)
//				rvNotification.itemAnimator = null
//				tvLoading.setTextColor(pDrawerTextColorPrimary)
//				tvMsg1.setTextColor(pDrawerTextColorPrimary)
//				txtTry.setTextColor(pDrawerTextColorPrimary)
//				txtTry.background = DrawableBuilder.setGradientDrawable(
//					pDrawerBackgroundColor,
//					12f,
//					3,
//					pDrawerBorderColor)
//			}
//		}
//	}
//
//	private fun showMessage() {
//		fragmentNotificationFragmentBinding.run {
//			if (AutoQLData.wasLoginIn) {
//				llParent.paddingAll(top = 80f)
//				iv1.visibility = View.VISIBLE
//				tvLoading.visibility = View.VISIBLE
//				tvMsg1.visibility = View.VISIBLE
//				tvLoading.textSize(18f)
//				tvLoading.setText(R.string.empty_notification)
//			} else {
//				iv1.visibility = View.GONE
//				tvMsg1.visibility = View.GONE
//				txtTry.visibility = View.VISIBLE
//				val msg = "Oh no! Something went wrong while accessing your notifications."
//				tvLoading.text = msg
//			}
//		}
//	}
//
//	private fun showRecyclerView() {
//		fragmentNotificationFragmentBinding.run {
//			rvNotification.visibility = View.VISIBLE
//			llParent.visibility = View.GONE
//		}
//	}
}