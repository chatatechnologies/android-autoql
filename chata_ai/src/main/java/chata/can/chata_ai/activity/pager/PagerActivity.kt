package chata.can.chata_ai.activity.pager

import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai.pojo.base.PageSelectedListener
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle

class PagerActivity: BaseActivity(R.layout.pager_queries_activity)
{
	private var tvToolbar: TextView ?= null
	private var ivLight: ImageView ?= null
	private var viewPager: ViewPager ?= null
	private val numPages = 2

	val dataMessengerTile = "Data Messenger"
	val exploreQueriesTile = "Explore Queries"

	override fun onCreateView()
	{
		tvToolbar = findViewById(R.id.tvToolbar)
		ivLight = findViewById(R.id.ivLight)
		viewPager = findViewById(R.id.viewPager)

		val adapter = SlidePagerAdapter(supportFragmentManager, numPages)
		viewPager?.adapter = adapter

		tvToolbar?.text = dataMessengerTile

		initListener()
	}

	override fun finish()
	{
		super.finish()
		BubbleHandle.isOpenChat = false
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_top)
	}

	override fun onDestroy()
	{
		super.onDestroy()
		BubbleHandle.instance.isVisible = true
//		if (clearOnClose)
//		{
//			SinglentonDrawer.mModel.clear()
//		}
	}

	private fun initListener()
	{
		viewPager?.addOnPageChangeListener(object: PageSelectedListener
		{
			override fun onSelected(position: Int)
			{
				val pData = when(position)
				{
					0 -> Pair(dataMessengerTile, R.drawable.ic_light)
					1 -> Pair(exploreQueriesTile, R.drawable.ic_chat_white)
					else -> Pair(dataMessengerTile, R.drawable.ic_light)
				}
				tvToolbar?.text = pData.first
				ivLight?.setImageResource(pData.second)
			}
		})
	}
}