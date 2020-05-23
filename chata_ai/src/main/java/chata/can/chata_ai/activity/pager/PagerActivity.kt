package chata.can.chata_ai.activity.pager

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.PropertyChatActivity
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerFragment
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai.pojo.base.PageSelectedListener
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle

class PagerActivity: BaseActivity(R.layout.pager_queries_activity), View.OnClickListener
{
	private var tvToolbar: TextView ?= null
	private var ivLight: ImageView ?= null
	private var ivCancel: ImageView ?= null
	private var ivClear: ImageView ?= null
	private var viewPager: ViewPager ?= null
	private var slidePagerAdapter: SlidePagerAdapter ?= null
	private val numPages = 2

	val model = SinglentonDrawer.mModel
	var dataMessengerTile = "Data Messenger"
	val exploreQueriesTile = "Explore Queries"

	override fun onCreateView()
	{
		PropertyChatActivity.context = this
		tvToolbar = findViewById(R.id.tvToolbar)
		ivCancel = findViewById(R.id.ivCancel)
		ivLight = findViewById(R.id.ivLight)
		ivClear = findViewById(R.id.ivClear)
		viewPager = findViewById(R.id.viewPager)

		initListener()
		initData()

		tvToolbar?.text = dataMessengerTile
		slidePagerAdapter = SlidePagerAdapter(supportFragmentManager, numPages)
		viewPager?.adapter = slidePagerAdapter

		initConfig()
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
		if (PagerData.clearOnClose)
		{
			SinglentonDrawer.mModel.clear()
		}
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.ivCancel ->
				{
					finish()
				}
				R.id.ivLight ->
				{
					viewPager?.run {
						currentItem = if (currentItem == 0) 1
						else 0
					}
				}
				R.id.ivClear ->
				{
					model.clear()
					val introMessageRes =
						if (PagerData.introMessage.isNotEmpty())
						{
							PagerData.introMessage
						}
						else
						{
							"Hi %s! Let\'s dive into your data. What can I help you discover today?"
						}

					val introMessage = String.format(introMessageRes, PagerData.customerName)
					model.add(ChatData(TypeChatView.LEFT_VIEW, introMessage))
					model.add(ChatData(TypeChatView.QUERY_BUILDER, ""))

					slidePagerAdapter?.getRegisteredFragment(0)?.let {
						dataMessengerFragment ->
						if (dataMessengerFragment is DataMessengerFragment)
						{
							dataMessengerFragment.notifyAdapter()
						}
					}
				}
				else -> {}
			}
		}
	}

	fun selectPage(index: Int)
	{
		viewPager?.currentItem = index
	}

	private fun initListener()
	{
		ivCancel?.setOnClickListener(this)
		ivLight?.setOnClickListener(this)
		ivClear?.setOnClickListener(this)

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

	private fun initConfig()
	{
		windowManager?.let {
			ScreenData.defaultDisplay = it.defaultDisplay
		}
		resources?.let {
			it.displayMetrics?.let {
					itMetrics ->
				ScreenData.densityByDP = itMetrics.density
			}
		}
		RequestBuilder.initVolleyRequest(this)
	}

	private fun initData()
	{
		intent?.let {
			PagerData.run {
				customerName = it.getStringExtra("CUSTOMER_NAME") ?: ""
				title = it.getStringExtra("TITLE") ?: ""
				introMessage = it.getStringExtra("INTRO_MESSAGE") ?: ""
				inputPlaceholder = it.getStringExtra("INPUT_PLACE_HOLDER") ?: ""
				maxMessages = it.getIntExtra("MAX_MESSAGES", 0)
				clearOnClose = it.getBooleanExtra("CLEAR_ON_CLOSE", false)
				enableVoiceRecord = it.getBooleanExtra("ENABLE_VOICE_RECORD", true)
			}
		}

		val title = PagerData.title
		dataMessengerTile = if (title.isNotEmpty())
		{
			title
		}
		else
		{
			getString(R.string.data_messenger)
		}
	}
}