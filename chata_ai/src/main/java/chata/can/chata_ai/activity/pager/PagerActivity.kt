package chata.can.chata_ai.activity.pager

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.BaseActivity
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

	val model = SinglentonDrawer.mModel
	var dataMessengerTile = "Data Messenger"

	override fun onCreateView()
	{
		tvToolbar = findViewById(R.id.tvToolbar)
		ivCancel = findViewById(R.id.ivCancel)
		ivLight = findViewById(R.id.ivLight)
		ivClear = findViewById(R.id.ivClear)

		ivClear?.setColorFilter(getParsedColor(R.color.white))

		initListener()
		initData()

		tvToolbar?.text = dataMessengerTile

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
		BubbleHandle.instance?.isVisible = true
		if (PagerData.clearOnClose)
		{
			model.clear()
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

				}
				R.id.ivClear ->
				{
					AlertDialog.Builder(this)
						.setMessage("Clear all queries & responses?")
						.setPositiveButton("Clear") { _, _ ->
							model.clear()
							val introMessageRes =
								if (PagerData.introMessage.isNotEmpty()) {
									PagerData.introMessage
								} else {
									"Hi %s! Let\'s dive into your data. What can I help you discover today?"
								}

							val introMessage = String.format(introMessageRes, PagerData.customerName)
							model.add(ChatData(TypeChatView.LEFT_VIEW, introMessage))
							model.add(ChatData(TypeChatView.QUERY_BUILDER, ""))

							//TODO call method with supportManager
//							slidePagerAdapter?.getRegisteredFragment(0)?.let { dataMessengerFragment ->
//								if (dataMessengerFragment is DataMessengerFragment) {
//									dataMessengerFragment.notifyAdapter()
//								}
//							}
						}
						.setNegativeButton("Cancel", null).show()
				}
				else -> {}
			}
		}
	}

	private fun initListener()
	{
		ivCancel?.setOnClickListener(this)
		ivLight?.setOnClickListener(this)
		ivClear?.setOnClickListener(this)
	}

	private fun initConfig()
	{
		windowManager?.let {
			ScreenData.windowManager = it
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
				maxMessages = it.getIntExtra("MAX_MESSAGES", 2)
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