package chata.can.chata_ai.activity.dm

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.addFragment
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerData
import chata.can.chata_ai.fragment.dataMessenger.DataMessengerFragment
import chata.can.chata_ai.pojo.base.BaseActivity

class DMActivity: BaseActivity(R.layout.view_pager_options)
{
	private lateinit var llMenu: LinearLayout
	private lateinit var rlChat: View
	private lateinit var ivChat: ImageView
	private lateinit var rlTips: View
	private lateinit var ivTips: ImageView
	private lateinit var rlNotify: View
	private lateinit var ivNotify: ImageView
	private lateinit var tvNotification: TextView
	private lateinit var rlLocal: View
	private lateinit var toolbar: View
	private lateinit var ivClose: ImageView
	private lateinit var tvTitle: TextView
	private lateinit var ivClear: ImageView

	private var rlSelected: View ?= null
	private var ivSelected: ImageView ?= null

	override fun onCreateView()
	{
		llMenu = findViewById(R.id.llMenu)
		rlChat = findViewById(R.id.rlChat)
		ivChat = findViewById(R.id.ivChat)
		rlTips = findViewById(R.id.rlTips)
		ivTips = findViewById(R.id.ivTips)
		rlNotify = findViewById(R.id.rlNotify)
		ivNotify = findViewById(R.id.ivNotify)
		tvNotification = findViewById(R.id.tvNotification)
		rlLocal = findViewById(R.id.rlLocal)
		toolbar = findViewById(R.id.toolbar)
		ivClose = findViewById(R.id.ivClose)
		tvTitle = findViewById(R.id.tvTitle)
		ivClear = findViewById(R.id.ivClear)

		rlSelected = rlChat
		ivSelected = ivChat

		openChat()
	}

	private fun openChat()
	{
		val fragment = DataMessengerFragment.newInstance()
		fragment.arguments?.let {
			DataMessengerData.run {
				it.putString("CUSTOMER_NAME", customerName)
				it.putString("TITLE", title)
				it.putString("INTRO_MESSAGE", introMessage)
				it.putString("INPUT_PLACE_HOLDER", inputPlaceholder)
				it.putInt("MAX_MESSAGES", maxMessages)
				it.putBoolean("CLEAR_ON_CLOSE", clearOnClose)
				it.putBoolean("ENABLE_VOICE_RECORD", enableVoiceRecord)
			}
		}
		addFragment(supportFragmentManager, fragment, DataMessengerFragment.nameFragment)
	}

	private fun configGUI()
	{
		DataMessengerData.run {

		}
	}

	private fun updateTitle()
	{

	}

	override fun finish() {
		super.finish()
		overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out)
	}
}