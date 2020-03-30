package chata.can.chata_ai.activity.chat

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.speech.SpeechRecognizer
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BuildConfig
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.adapter.AutoCompleteAdapter
import chata.can.chata_ai.activity.chat.adapter.ChatAdapter
import chata.can.chata_ai.activity.chat.voice.VoiceRecognition
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.SimpleQuery
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import com.android.volley.toolbox.Volley
import java.net.URLEncoder

class ChatActivity: BaseActivity(R.layout.chat_activity), View.OnClickListener, ChatContract.View
{
	private lateinit var ivCancel: ImageView
	private lateinit var ivClear: ImageView
	private lateinit var rvChat: RecyclerView
	private lateinit var etQuery: AutoCompleteTextView
	private lateinit var ivMicrophone: ImageView

	private lateinit var speechRecognizer: SpeechRecognizer
	private lateinit var speechIntent: Intent

	private lateinit var model: BaseModelList<ChatData>
	private lateinit var adapterAutoComplete: AutoCompleteAdapter
	private val renderPresenter = ChatRenderPresenter(this, this)
	private val servicePresenter = ChatServicePresenter(this, this)
	private lateinit var chatAdapter: ChatAdapter

	private var customerName = ""

	override fun onCreateView()
	{
		initConfig()
		initViews()
		initData()
		initListener()
		initList()

		renderPresenter.setData()
		initSpeechInput()
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean
	{
		menuInflater.inflate(R.menu.chat_menu, menu)
		return true
	}

	override fun finish()
	{
		super.finish()
		BubbleHandle.isOpenChat = false
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_top)
	}

	override fun setRecorder()
	{
		val red = ContextCompat.getColor(this, android.R.color.holo_red_dark)
		val circleDrawable = GradientDrawable().apply {
			shape = GradientDrawable.OVAL
			setColor(red)
		}
		ivMicrophone.background = circleDrawable
	}

	override fun setSpeech(message: String)
	{
		etQuery.setText(message)
		etQuery.setSelection(message.length)
	}

	override fun setStopRecorder()
	{
		val red = ContextCompat.getColor(this, R.color.chata_drawer_accent_color)
		val circleDrawable = GradientDrawable().apply {
			shape = GradientDrawable.OVAL
			setColor(red)
		}
		ivMicrophone.background = circleDrawable
	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray)
	{
		if (requestCode == 801)
		{
			//showAlert("Sorry, that didn't seem to work. Please try again.")
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
				R.id.ivClear ->
				{
					model.clearData()

					val introMessageRes = (this as Context).getStringResources(R.string.intro_message_chata_drawer)
					val introMessage = String.format(introMessageRes, customerName)

					model.addData(ChatData(1, introMessage))
					chatAdapter.notifyDataSetChanged()
				}
				//TODO REMOVE
				R.id.ivMicrophone ->
				{
					setRequestQuery()
				}
			}
		}
	}

	override fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>)
	{
		with(etQuery)
		{
			background = pDrawable.second
			//setDropDownBackgroundDrawable(pDrawable.second)

			adapterAutoComplete = AutoCompleteAdapter(this@ChatActivity, R.layout.row_spinner)

			threshold = 1
			setAdapter(adapterAutoComplete)
			etQuery.onItemClickListener = AdapterView.OnItemClickListener {
				parent, _, position, _ ->
				parent?.let {
					it.adapter?.let {
						adapter ->
						val text = adapter.getItem(position).toString()
						etQuery.setText(text)
						setRequestQuery()
					}
				}
			}

			setOnEditorActionListener {
				_, _, _ ->
				setRequestQuery()
				false
			}

			val displayMetrics = DisplayMetrics()
			ScreenData.defaultDisplay.getMetrics(displayMetrics)
			val width = displayMetrics.widthPixels
			etQuery.dropDownWidth = width

			if (BuildConfig.DEBUG)
			{
				etQuery.setText("all sales")
			}
		}

		ivMicrophone.background = pDrawable.first
	}

	override fun setDataAutocomplete(aMatches: ArrayList<String>)
	{
		adapterAutoComplete.clear()
		if (aMatches.isNotEmpty())
		{
			adapterAutoComplete.addAll(aMatches)

			val size = Point()
			ScreenData.defaultDisplay.getSize(size)
			val maxHeight = size.y * 0.35

			val count = adapterAutoComplete.count
			val height = ScreenData.densityByDP * (if (count < 2) 2 else adapterAutoComplete.count) * 40

			etQuery.dropDownHeight =
				if (height < maxHeight)
					height.toInt()
				else
					maxHeight.toInt()
		}
		adapterAutoComplete.notifyDataSetChanged()
	}

	override fun addChatMessage(typeView: Int, message: String)
	{
		val chatData = ChatData(typeView, message)
		model.addData(chatData)
		chatAdapter.notifyItemChanged(model.countData() - 1)
	}

	override fun addNewChat(typeView: Int, queryBase: SimpleQuery)
	{
		val chatData = ChatData(typeView, "", queryBase)
		model.addData(chatData)
		chatAdapter.notifyItemChanged(model.countData() - 1)
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
		RequestBuilder.requestQueue = Volley.newRequestQueue(this)
	}

	private fun initViews()
	{
		ivCancel = findViewById(R.id.ivCancel)
		ivClear = findViewById(R.id.ivClear)
		rvChat = findViewById(R.id.rvChat)
		etQuery = findViewById(R.id.etQuery)
		ivMicrophone = findViewById(R.id.ivMicrophone)
	}

	private fun initListener()
	{
		ivCancel.setOnClickListener(this)
		ivClear.setOnClickListener(this)

		etQuery.addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					servicePresenter.getAutocomplete(URLEncoder.encode(string, "UTF-8"))
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

	private fun initList()
	{
		model = BaseModelList()
		chatAdapter = ChatAdapter(model, this, servicePresenter)

		val introMessageRes = (this as Context).getStringResources(R.string.intro_message_chata_drawer)
		val introMessage = String.format(introMessageRes, customerName)

		model.addData(ChatData(1, introMessage))
		rvChat.layoutManager = LinearLayoutManager(this)
		rvChat.adapter = chatAdapter
	}

	private fun initData()
	{
		customerName = intent?.getStringExtra("CUSTOMER_NAME") ?: ""
	}

	private fun setRequestQuery()
	{
		val query = etQuery.text.toString()
		if (query.isNotEmpty())
		{
			hideKeyboard()
			etQuery.setText("")
			model.addData(ChatData(2, query))

			chatAdapter.notifyItemChanged(model.countData() - 1)

			servicePresenter.getSafety(query)
		}
	}

	private fun initSpeechInput()
	{
		speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
		speechRecognizer.setRecognitionListener(VoiceRecognition(this))

		speechIntent = renderPresenter.initSpeechInput()
	}

	private fun promptSpeechInput()
	{
		if (SpeechRecognizer.isRecognitionAvailable(this))
		{
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != 0)
			{
				AlertDialog.Builder(this)
					.setMessage(R.string.msg_permission_record)
					.setNeutralButton("Ok", null)
					.setOnDismissListener {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
						{
							requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO),801)
						}
					}.show()
			}
			else
			{
				speechRecognizer.startListening(speechIntent)
			}
		}
		else
			{
				AlertDialog.Builder(this)
					.setMessage("This device does not count an App with speech recognition.")
					.setNeutralButton("Ok", null)
					.setOnDismissListener {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
						{
							requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO),801)
						}
					}.show()
			}
	}

	private fun setTouchListener()
	{
		with(ivMicrophone)
		{
			setOnClickListener(null)
			setOnTouchListener {
					_, event ->
				when(event.action)
				{
					MotionEvent.ACTION_DOWN ->
					{
						promptSpeechInput()
						//if (isOpenFAB) changeFAB(false)
						//btnMenu.isEnabled = false
					}
					MotionEvent.ACTION_UP ->
					{
						speechRecognizer.stopListening()
						//btnMenu.isEnabled = true
					}
				}
				true
			}
		}
	}
}