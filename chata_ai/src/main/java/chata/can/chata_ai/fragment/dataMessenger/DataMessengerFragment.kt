package chata.can.chata_ai.fragment.dataMessenger

import android.content.Intent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.fragment.exploreQueries.ExploreQueriesFragment
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.putArgs
import java.net.URLEncoder

class DataMessengerFragment: BaseFragment(), View.OnClickListener
{
	companion object {
		const val nameFragment = "Data Messenger"
		fun newInstance() = ExploreQueriesFragment()
			.putArgs {
			putInt("LAYOUT", R.layout.fragment_date_messenger)
		}
	}

	private lateinit var llParent: View
	private lateinit var toolbar: View
	private lateinit var tvToolbar: TextView
	private lateinit var ivCancel: ImageView
	private var isTips = false
	private lateinit var ivLight: ImageView
	private lateinit var ivClear: ImageView
	private lateinit var rvChat: RecyclerView
	private lateinit var gifView: View
	private lateinit var ivRun: ImageView
	private lateinit var etQuery: AutoCompleteTextView
	private lateinit var ivMicrophone: ImageView

	private lateinit var speechRecognizer: SpeechRecognizer
	private lateinit var speechIntent: Intent

	val model = SinglentonDrawer.mModel
//	private lateinit var adapterAutoComplete: AutoCompleteAdapter
//	private val renderPresenter = ChatRenderPresenter(this, this)
//	private val servicePresenter = ChatServicePresenter(this, this)
//	private lateinit var chatAdapter: ChatAdapter

	private var customerName = ""
	private var title = ""
	private var introMessage = ""
	private var inputPlaceholder = ""
	private var maxMessages = 0
	private var clearOnClose = false
	private var enableVoiceRecord = true

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
//		initData(); This data is current in object PagerData
		initList()

		renderPresenter.setData()
		initSpeechInput()
	}

	override fun initViews(view: View)
	{
		with(view)
		{
			llParent = findViewById(R.id.llParent)
			toolbar = findViewById(R.id.toolbar)
			tvToolbar = findViewById(R.id.tvToolbar)
			ivCancel = findViewById(R.id.ivCancel)
			ivLight = findViewById(R.id.ivLight)
			ivClear = findViewById(R.id.ivClear)
			rvChat = findViewById(R.id.rvChat)
			gifView = findViewById(R.id.gifView)
			ivRun = findViewById(R.id.ivRun)
			etQuery = findViewById(R.id.etQuery)
			ivMicrophone = findViewById(R.id.ivMicrophone)
		}
	}

	override fun initListener()
	{
		ivCancel.setOnClickListener(this)
		ivLight.setOnClickListener(this)
		ivClear.setOnClickListener(this)

		etQuery.addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					if (SinglentonDrawer.mIsEnableAutocomplete)
					{
//						servicePresenter.getAutocomplete(URLEncoder.encode(string, "UTF-8"))
					}
					with(ivMicrophone)
					{
						setImageResource(R.drawable.ic_send)
						setOnTouchListener(null)
						setOnClickListener { setRequestQuery() }
					}
				}
				else
				{
					ivMicrophone.setImageResource(R.drawable.ic_microphone)
					setTouchListener()
				}
			}
		})
		setTouchListener()
	}

	override fun setColors()
	{
		activity?.let {
			activity ->
			with(ThemeColor.currentColor)
			{
				llParent.setBackgroundColor(ContextCompat.getColor(
					activity, drawerBackgroundColor))
				toolbar.setBackgroundColor(ContextCompat.getColor(
					activity, drawerAccentColor))
				tvToolbar.setTextColor(ContextCompat.getColor(
					activity, R.color.white))
				etQuery.setHintTextColor(ContextCompat.getColor(
					activity, drawerHoverColor))
				ivRun.setColorFilter(ContextCompat.getColor(
					activity, R.color.chata_drawer_hover_color_dark))
			}
		}
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{

			}
		}
	}

	private fun setRequestQuery()
	{
		val query = etQuery.text.toString()
		if (query.isNotEmpty())
		{
			hideKeyboard()
			etQuery.setText("")
			model.add(ChatData(2, query))
			scrollToPosition()

			chatAdapter.notifyItemChanged(model.countData() - 1)

			//val queryEncode = URLEncoder.encode(query, "UTF-8")
			if (SinglentonDrawer.mIsEnableQuery)
			{
				servicePresenter.getSafety(query)
			}
			else
			{
				servicePresenter.getQuery(query)
			}
		}
	}

	private fun setTouchListener()
	{
		with(ivMicrophone)
		{
			if (enableVoiceRecord)
			{
				setOnClickListener(null)
				setOnTouchListener {
						_, event ->
					when(event.action)
					{
						MotionEvent.ACTION_DOWN ->
						{
							promptSpeechInput()
						}
						MotionEvent.ACTION_UP ->
						{
							speechRecognizer.stopListening()
						}
					}
					true
				}
			}
			else
			{
				setImageResource(R.drawable.ic_send)
				setOnTouchListener(null)
				setOnClickListener { setRequestQuery() }
			}
		}
	}

	private fun initList()
	{
		//TODO method for to build
	}
}