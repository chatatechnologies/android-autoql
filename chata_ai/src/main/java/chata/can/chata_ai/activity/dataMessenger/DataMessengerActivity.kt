package chata.can.chata_ai.activity.dataMessenger

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.speech.SpeechRecognizer
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BuildConfig
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.pager.PagerData
import chata.can.chata_ai.context.ContextActivity
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.activity.dataMessenger.adapter.AutoCompleteAdapter
import chata.can.chata_ai.activity.dataMessenger.adapter.ChatAdapter
import chata.can.chata_ai.activity.dataMessenger.presenter.ChatRenderPresenter
import chata.can.chata_ai.activity.dataMessenger.presenter.ChatServicePresenter
import chata.can.chata_ai.activity.dataMessenger.voice.VoiceRecognition
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.SimpleQuery
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.view.animationAlert.AnimationAlert
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai.view.typing.MigrateAutoComplete
import org.json.JSONObject

class DataMessengerActivity:
	BaseActivity(R.layout.activity_data_messenger), ChatContract.View, View.OnClickListener
{
	private var tvToolbar: TextView?= null
	private var ivCancel: ImageView ?= null
	private var ivClear: ImageView ?= null

	private lateinit var llParent: View
	private lateinit var rvChat: RecyclerView
	private lateinit var gifView: View
	private lateinit var ivRun: ImageView
	private lateinit var etQuery: MigrateAutoComplete
	private lateinit var ivMicrophone: ImageView

	private lateinit var speechRecognizer: SpeechRecognizer
	private lateinit var speechIntent: Intent

	private val model = SinglentonDrawer.mModel
	private lateinit var adapterAutoComplete: AutoCompleteAdapter
	private lateinit var renderPresenter: ChatRenderPresenter
	private lateinit var servicePresenter: ChatServicePresenter
	private lateinit var chatAdapter: ChatAdapter
	private lateinit var animationAlert: AnimationAlert

	private var dataMessengerTile = "Data Messenger"

	override fun onCreateView()
	{
		ContextActivity.context = this
		initViews()
		initListener()
		setColors()
		initData()
		renderPresenter = ChatRenderPresenter(this, this@DataMessengerActivity)
		servicePresenter = ChatServicePresenter(this, this@DataMessengerActivity)
		initList()
		renderPresenter.setData()
		initSpeechInput()
	}

	private fun initViews()
	{
		llParent = findViewById(R.id.llParent)
		rvChat = findViewById(R.id.rvChat)
		gifView = findViewById(R.id.gifView)
		ivRun = findViewById(R.id.ivRun)
		etQuery = findViewById(R.id.etQuery)
		ivMicrophone = findViewById(R.id.ivMicrophone)

		tvToolbar = findViewById(R.id.tvToolbar)
		ivCancel = findViewById(R.id.ivCancel)
		ivClear = findViewById(R.id.ivClear)

		ivClear?.setColorFilter(getParsedColor(R.color.white))
		animationAlert = AnimationAlert(findViewById(R.id.rlAlert))
	}

	private fun initListener()
	{
		animationAlert.hideAlert()

		ivCancel?.setOnClickListener(this)
		ivClear?.setOnClickListener(this)

		etQuery.addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					if (SinglentonDrawer.mIsEnableAutocomplete)
					{
						servicePresenter.getAutocomplete(string)
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

	private fun setColors()
	{
		with(ThemeColor.currentColor)
		{
			llParent.setBackgroundColor(getParsedColor(drawerBackgroundColor))
			etQuery.setHintTextColor(getParsedColor(drawerHoverColor))
			ivRun.setColorFilter(getParsedColor(R.color.chata_drawer_hover_color_dark))
		}
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
		tvToolbar?.text = dataMessengerTile

		windowManager?.let {
			ScreenData.windowManager = it
			ScreenData.defaultDisplay = it.defaultDisplay
		}
		resources?.let {
			it.displayMetrics?.let { itMetrics ->
				ScreenData.densityByDP = itMetrics.density
			}
		}
		RequestBuilder.initVolleyRequest(this)
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
							notifyAdapter()
						}
						.setNegativeButton("Cancel", null).show()
				}
				else -> {}
			}
		}
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

	override fun addChatMessage(typeView: Int, message: String, query: String)
	{
		val json = JSONObject().apply {
			put("query", query)
		}
		val simpleQuery = SimpleQuery(json)
		simpleQuery.typeView = typeView
		val chatData = ChatData(typeView, message, simpleQuery)
		model.add(chatData)
		chatAdapter.notifyItemChanged(model.countData() - 1)
		scrollToPosition()
	}

	override fun addNewChat(typeView: Int, queryBase: SimpleQuery)
	{
		val chatData = ChatData(typeView, "", queryBase)
		model.add(chatData)
		chatAdapter.notifyItemChanged(model.countData() - 1)
		scrollToPosition()
	}

	override fun isLoading(isVisible: Boolean)
	{
		gifView.visibility = if (isVisible)
			View.VISIBLE
		else
			View.GONE
	}

	override fun runTyping(text: String)
	{
		etQuery.setCharacterDelay(100)
		etQuery.animateText(text)
	}

	override fun showAlert(message: String, intRes: Int)
	{
		animationAlert.setText(message)
		animationAlert.setResource(intRes)
		animationAlert.showAlert()
		Looper.getMainLooper()?.let {
			Handler(it).postDelayed({
				animationAlert.hideAlert()
			}, 1500)
		}
	}

	override fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>)
	{
		etQuery.run {
			setTextColor(context.getParsedColor(ThemeColor.currentColor.drawerColorPrimary))
			background = pDrawable.second
			val hint = if (PagerData.inputPlaceholder.isNotEmpty())
			{
				PagerData.inputPlaceholder
			}
			else
			{
				getString(R.string.type_queries_here)
			}
			setHint(hint)

			adapterAutoComplete = AutoCompleteAdapter(
				this@DataMessengerActivity,
				R.layout.row_spinner)
			threshold = 1
			setAdapter(adapterAutoComplete)
			onItemClickListener = AdapterView.OnItemClickListener {
					parent, _, position, _ ->
				parent?.let {
					it.adapter?.let { adapter ->
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
			dropDownWidth = width

			if (BuildConfig.DEBUG)
			{
//				val queryDemo = "count invoices"
//				val queryDemo = "Total tickets by customer this year"
//				val queryDemo = "How many job by job area by year"
//				val queryDemo = "Average revenue by area last year"
				val queryDemo = "advintage oil"
//				val queryDemo = "total estimates by job type by month last year"
				setText(queryDemo)
			}
			ivMicrophone.background = pDrawable.first
		}
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

	override fun setRecorder()
	{
		val red = getParsedColor(android.R.color.holo_red_dark)
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
		val red = getParsedColor(ThemeColor.currentColor.drawerAccentColor)
		val circleDrawable = GradientDrawable().apply {
			shape = GradientDrawable.OVAL
			setColor(red)
		}
		ivMicrophone.background = circleDrawable
	}

	private val codeExplore = 100
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
	{
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == codeExplore)
		{
			if (resultCode == RESULT_OK)
			{
				data?.let {
					val query = it.getStringExtra("query")
					etQuery.setText(query)
				}
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

	@SuppressLint("ClickableViewAccessibility")
	private fun setTouchListener()
	{
		with(ivMicrophone)
		{
			if (PagerData.enableVoiceRecord)
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
		chatAdapter = ChatAdapter(model, this, servicePresenter, this)

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

		if (SinglentonDrawer.mModel.countData() == 0)
		{
			model.add(ChatData(TypeChatView.LEFT_VIEW, introMessage))
			model.add(ChatData(TypeChatView.QUERY_BUILDER, ""))
		}
		else
		{
			model[0]?.let {
				it.message = introMessage
			}
		}

		rvChat.layoutManager = LinearLayoutManager(this)
		rvChat.adapter = chatAdapter
		scrollToPosition()
	}

	private fun notifyAdapter()
	{
		chatAdapter.notifyDataSetChanged()
	}

	private fun scrollToPosition()
	{
		//region value max number message
		while (model.countData() > PagerData.maxMessages)
		{
			model.removeAt(0)
			chatAdapter.notifyItemRemoved(0)
		}
		//endregion
		Handler().postDelayed({
			val position = model.countData() - 1
			rvChat.scrollToPosition(position)
		}, 200)
	}
}