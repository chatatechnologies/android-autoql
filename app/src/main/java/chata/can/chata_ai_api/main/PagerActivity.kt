package chata.can.chata_ai_api.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerFragment
import chata.can.chata_ai.model.BubbleData
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.service.PollService
import chata.can.chata_ai.view.PagerOptions
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai_api.R
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

	override fun onCreate(savedInstanceState: Bundle?)
	{
		ThemeColor.parseColor(this)
		super.onCreate(savedInstanceState)
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
			BubbleHandle.instance?.isVisible = true
		}
		registerReceiver(receiver, IntentFilter(PollService.NOTIFICATION))
	}

	override fun onPause()
	{
		super.onPause()
		if (BubbleHandle.isInitialize() && !pagerOption.isVisible)
		{
			BubbleHandle.instance?.isVisible = false
		}
		unregisterReceiver(receiver)
	}

	override fun onBackPressed()
	{
		if (pagerOption.isVisible)
		{
			BubbleHandle.isOpenChat = false
			BubbleHandle.instance?.isVisible = true
			pagerOption.setStatusGUI(false)
		}
		else
		{
			if (supportFragmentManager.backStackEntryCount > 0)
				finishAffinity()
			else
			//minimize App
				moveTaskToBack(true)
		}
	}

	override fun onDestroy()
	{
		super.onDestroy()
		BubbleHandle.instance = null
		pagerOption.onDestroy()
	}

	fun setStatusGUI(isVisible: Boolean, bubbleData: BubbleData ?= null)
	{
		pagerOption.bubbleData = bubbleData
		hideKeyboard()
		pagerOption.setStatusGUI(isVisible)
		Toast.makeText(this, "(2) Screen is ${pagerOption.isVisible}", Toast.LENGTH_SHORT).show()
		//pagerOption.paintViews()
	}

	fun clearDataMessenger(bubbleData: BubbleData ?= null)
	{
		pagerOption.bubbleData = bubbleData
		pagerOption.fragmentManager?.findFragmentByTag(DataMessengerFragment.nameFragment)?.let {
			if (it is DataMessengerFragment)
			{
				pagerOption.bubbleData?.let { bubble ->
					val argument = Bundle().apply {
						putString("CUSTOMER_NAME", bubble.customerName)
						putString("TITLE", bubble.title)
						putString("INTRO_MESSAGE", bubble.introMessage)
						putString("INPUT_PLACE_HOLDER", bubble.inputPlaceholder)
						putInt("MAX_MESSAGES", bubble.maxMessage)
						putBoolean("CLEAR_ON_CLOSE", bubble.clearOnClose)
						putBoolean("ENABLE_VOICE_RECORD", bubble.enableVoiceRecord)
					}
					it.updateData(argument)
				}
				it.clearQueriesAndResponses()
			}
		}
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
	private fun canDrawOverlays() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
	{
		Settings.canDrawOverlays(this)
	} else false

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