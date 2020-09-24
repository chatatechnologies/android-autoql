package chata.can.chata_ai_api.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai_api.R
import com.google.android.material.tabs.TabLayout

class PagerActivity: AppCompatActivity(), PagerContract
{
	private lateinit var viewPager: ViewPager
	private lateinit var tabLayout: TabLayout
	private lateinit var adapter: SlidePagerAdapter

	private lateinit var llMenu: View
	private lateinit var rlChat: View
	private lateinit var ivChat: ImageView
	private lateinit var rlTips: View
	private lateinit var ivTips: ImageView
	private lateinit var rlNotify: View
	private lateinit var ivNotify: ImageView

	private lateinit var frmLocal: View

	private val overlayPermission = 1000

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.pager_activity)
		viewPager = findViewById(R.id.viewPager)
		tabLayout = findViewById(R.id.tabLayout)

		llMenu = findViewById(R.id.llMenu)
		rlChat = findViewById(R.id.rlChat)
		ivChat = findViewById(R.id.ivChat)
		rlTips = findViewById(R.id.rlTips)
		ivTips = findViewById(R.id.ivTips)
		rlNotify = findViewById(R.id.rlNotify)
		ivNotify = findViewById(R.id.ivNotify)

		frmLocal = findViewById(R.id.frmLocal)

		tabLayout.setupWithViewPager(viewPager)
		setColors()

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

	override fun setStatusGUI(isVisible: Boolean)
	{
		val iVisible = if (isVisible) View.VISIBLE else View.GONE
		llMenu.visibility = iVisible
		frmLocal.visibility = iVisible
	}

	private fun initBubble()
	{
		adapter = SlidePagerAdapter(supportFragmentManager, 1)
		viewPager.adapter = adapter

		RequestBuilder.initVolleyRequest(this)
	}

	private fun setColors()
	{
		ivChat.setColorFilter(getParsedColor(R.color.black))
		ivTips.setColorFilter(getParsedColor(R.color.white))
		ivNotify.setColorFilter(getParsedColor(R.color.white))
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
			adapter.numPages = 2
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