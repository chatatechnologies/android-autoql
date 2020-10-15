package chata.can.chata_ai_api.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import chata.can.chata_ai.model.BubbleData
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.view.PagerOptions
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.test.PollService
import com.google.android.material.tabs.TabLayout
import org.json.JSONObject

class PagerActivity: BaseActivity(R.layout.pager_activity)
{
	private lateinit var viewPager: ViewPager
	private lateinit var tabLayout: TabLayout
	private lateinit var pagerOption: PagerOptions
	private lateinit var adapter: SlidePagerAdapter

	private val overlayPermission = 1000

	private val receiver = object: BroadcastReceiver()
	{
		override fun onReceive(context: Context?, intent: Intent?)
		{
			intent?.extras?.let {
				it.getString(PollService.DATA)?.let { data ->
					try {
						val json = JSONObject(data)
						json.optJSONObject("data")?.let { joData ->
							val unacknowledged = joData.optInt("unacknowledged")
							pagerOption.showNotify(unacknowledged > 0)
						}
					} catch (ex: Exception) {}
				}
			}
		}
	}

	override fun onCreateView()
	{
		viewPager = findViewById(R.id.viewPager)
		tabLayout = findViewById(R.id.tabLayout)
		pagerOption = findViewById(R.id.pagerOption)
		pagerOption.fragmentManager = supportFragmentManager

		tabLayout.setupWithViewPager(viewPager)
		if (isMarshmallow())
		{
			if (!canDrawOverlays())
			{
				with(
					Intent(
						Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
						Uri.parse("package:$packageName"))
				)
				{
					startActivityForResult(this, overlayPermission)
				}
			}
			else
			{
				initBubble()
			}
		}
		else
		{
			initBubble()
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
	{
		super.onActivityResult(requestCode, resultCode, data)
		if (isMarshmallow())
		{
			if (canDrawOverlays())
			{
				initBubble()
			}
			else
			{
				Toast.makeText(this, "canDrawOverlays is not enable", Toast.LENGTH_SHORT)
					.show()
			}
		}
	}

	override fun onResume()
	{
		super.onResume()
		if (BubbleHandle.isInitialize() && !pagerOption.isVisible)
		{
			BubbleHandle.instance.isVisible = true
		}
		registerReceiver(receiver, IntentFilter(PollService.NOTIFICATION))
	}

	override fun onPause()
	{
		super.onPause()
		if (!pagerOption.isVisible)
		{
			BubbleHandle.instance.isVisible = false
		}
		unregisterReceiver(receiver)
	}

	override fun onBackPressed()
	{
		if (pagerOption.isVisible)
		{
			BubbleHandle.isOpenChat = false
			BubbleHandle.instance.isVisible = true
			pagerOption.setStatusGUI(false)
		}
		else
		{
			if (supportFragmentManager.backStackEntryCount > 0)
				finishAffinity()
			else
				super.onBackPressed()
		}
	}

	override fun onDestroy()
	{
		super.onDestroy()
		pagerOption.onDestroy()
	}

	fun setStatusGUI(isVisible: Boolean, bubbleData: BubbleData ?= null)
	{
		pagerOption.bubbleData = bubbleData
		hideKeyboard()
		pagerOption.setStatusGUI(isVisible)
		pagerOption.paintViews()
	}

	private fun initBubble()
	{
		windowManager?.let {
			ScreenData.windowManager = it
			ScreenData.defaultDisplay = it.defaultDisplay
		}
		resources?.let {
			it.displayMetrics?.let { itMetrics ->
				ScreenData.densityByDP = itMetrics.density
			}
		}

		adapter = SlidePagerAdapter(supportFragmentManager, 1)
		viewPager.adapter = adapter
		RequestBuilder.initVolleyRequest(this)
	}

	/**
	 * Build.VERSION_CODES.M is 23
	 */
	private fun isMarshmallow() = Build.VERSION.SDK_INT >= 23

	/**
	 * Build.VERSION_CODES.M is 23
	 * M is for Marshmallow!
	 */
	@RequiresApi(api = Build.VERSION_CODES.M)
	private fun canDrawOverlays() = Settings.canDrawOverlays(this)

	var isVisibleTabLayout: Boolean = true
	set(value) {
		val visible = if (value) {
			adapter.numPages = 3
			View.VISIBLE
		} else {
			adapter.numPages = 1
			View.GONE
		}
		adapter.notifyDataSetChanged()
		tabLayout.visibility = visible
		field = value
	}
}