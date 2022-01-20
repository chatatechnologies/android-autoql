package chata.can.chata_ai.retrofit.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import chata.can.chata_ai.databinding.FragmentNotificationBinding
import chata.can.chata_ai.fragment.notification.NotificationContract
import chata.can.chata_ai.fragment.notification.adapter.NotificationAdapter
import chata.can.chata_ai.fragment.notification.model.Notification
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.retrofit.data.model.NotificationModel
import chata.can.chata_ai.retrofit.ui.viewModel.NotificationViewModel

class NotificationFragment: Fragment(), NotificationContract {
	companion object {
		const val nameFragment = "Notifications"
	}

	private lateinit var binding: FragmentNotificationBinding
	private val notificationViewModel: NotificationViewModel by viewModels()
	private lateinit var notificationAdapter: NotificationAdapter
	private val model = BaseModelList<NotificationModel>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentNotificationBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		initObserve()
		initList()
		binding.run {
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

	}

	override fun showNotifications(totalPages: Int, aNotification: ArrayList<Notification>) {

	}

	private fun initObserve() {
		notificationViewModel.run {
			onCreate()
			notificationList.observe(this@NotificationFragment) { listNotification ->
				listNotification.forEach { model.add(it) }
				//show or hide list
			}
		}
	}

	private fun initList() {
		binding.run {
			rvNotification.run {
				notificationAdapter = NotificationAdapter(model, this@NotificationFragment) {

				}
				layoutManager = LinearLayoutManager(requireActivity())
				adapter = notificationAdapter
				itemAnimator = null
			}
		}
	}

	private fun setColors() {
		ThemeColor.currentColor.run {
			binding.run {
				llParent.setBackgroundColor(pDrawerBackgroundColor)
				rvNotification.setBackgroundColor(pDrawerColorSecondary)
				tvLoading.setTextColor(pDrawerTextColorPrimary)
				tvMsg1.setTextColor(pDrawerTextColorPrimary)
			}
		}
	}

	private fun showAtItem(position: Int) {
		Handler(Looper.getMainLooper()).postDelayed({
			binding.rvNotification.smoothScrollToPosition(position)
		}, 200)
	}
}