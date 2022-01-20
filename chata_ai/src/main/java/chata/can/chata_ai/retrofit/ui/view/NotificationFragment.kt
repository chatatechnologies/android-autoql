package chata.can.chata_ai.retrofit.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import chata.can.chata_ai.databinding.FragmentNotificationBinding
import chata.can.chata_ai.fragment.notification.adapter.NotificationAdapter
import chata.can.chata_ai.fragment.notification.model.Notification
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.color.ThemeColor

class NotificationFragment: Fragment() {
	companion object {
		const val nameFragment = "Notifications"
	}

	private lateinit var binding: FragmentNotificationBinding
	private lateinit var adapter: NotificationAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentNotificationBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
				//setColors()
			}
		}
	}

	private fun initList(model: BaseModelList<Notification>) {
		binding.run {
			rvNotification.run {
				layoutManager = LinearLayoutManager(requireActivity())

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