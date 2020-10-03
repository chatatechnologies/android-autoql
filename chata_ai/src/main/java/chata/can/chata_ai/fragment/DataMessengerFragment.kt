package chata.can.chata_ai.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
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
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.dataMessenger.ChatContract
import chata.can.chata_ai.activity.dataMessenger.adapter.AutoCompleteAdapter
import chata.can.chata_ai.activity.dataMessenger.adapter.ChatAdapter
import chata.can.chata_ai.activity.dataMessenger.presenter.ChatServicePresenter
import chata.can.chata_ai.activity.dataMessenger.voice.VoiceRecognition
import chata.can.chata_ai.activity.pager.PagerData
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.fragment.DataMessengerData.clearOnClose
import chata.can.chata_ai.fragment.DataMessengerData.customerName
import chata.can.chata_ai.fragment.DataMessengerData.enableVoiceRecord
import chata.can.chata_ai.fragment.DataMessengerData.inputPlaceholder
import chata.can.chata_ai.fragment.DataMessengerData.introMessage
import chata.can.chata_ai.fragment.DataMessengerData.maxMessages
import chata.can.chata_ai.fragment.DataMessengerData.title
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.SimpleQuery
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs
import chata.can.chata_ai.view.animationAlert.AnimationAlert
import chata.can.chata_ai.view.typing.TypingAutoComplete
import org.json.JSONObject

class DataMessengerFragment: BaseFragment(), ChatContract.View
{
	companion object {
		const val nameFragment = "Data Messenger"
		fun newInstance() = DataMessengerFragment()
//		fun newInstance() = DataMessengerFragment().putArgs {
//			putInt("LAYOUT", R.layout.fragment_data_messenger)
//		}
	}

	private lateinit var llParent: View
	private lateinit var rvChat: RecyclerView
	private lateinit var gifView: View
	private lateinit var tvMsg: TextView
	private lateinit var ivRun: ImageView
	private lateinit var etQuery: TypingAutoComplete
	private lateinit var ivMicrophone: ImageView

	private lateinit var speechRecognizer: SpeechRecognizer
	private lateinit var speechIntent: Intent

	private lateinit var adapterAutoComplete: AutoCompleteAdapter
	private lateinit var chatAdapter: ChatAdapter
	private lateinit var animationAlert: AnimationAlert

	private val model = SinglentonDrawer.mModel
	private lateinit var presenter: ChatServicePresenter

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		activity?.let {
			presenter = ChatServicePresenter(it, this)
		}
		initData()
		initList()
		initSpeechInput()
	}

	override fun initListener()
	{
		animationAlert.hideAlert()
		etQuery.addTextChangedListener(object: TextChanged {
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					if (SinglentonDrawer.mIsEnableAutocomplete)
					{
						presenter.getAutocomplete(string)
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

		activity?.let {
			adapterAutoComplete = AutoCompleteAdapter(it, R.layout.row_spinner)
			etQuery.threshold = 1
			etQuery.setAdapter(adapterAutoComplete)
		}
		etQuery.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
			parent?.let {
				it.adapter?.let { adapter ->
					val text = adapter.getItem(position).toString()
					etQuery.setText(text)
					setRequestQuery()
				}
			}
		}

		etQuery.setFinishAnimationListener {
			val query = etQuery.text.toString()
			if (query.isNotEmpty())
			{
				hideKeyboard()
				etQuery.setText("")
				presenter.getQuery(query)
			}
		}

		etQuery.setOnEditorActionListener { _, _, _ ->
			setRequestQuery()
			false
		}

		val displayMetrics = DisplayMetrics()
		ScreenData.defaultDisplay.getRealMetrics(displayMetrics)
		val width = displayMetrics.widthPixels
		etQuery.dropDownWidth = width
	}

	override fun initViews(view: View)
	{
		with(view)
		{
			llParent = findViewById(R.id.llParent)
			rvChat = findViewById(R.id.rvChat)
			gifView = findViewById(R.id.gifView)
			tvMsg = findViewById(R.id.tvMsg)
			ivRun = findViewById(R.id.ivRun)
			etQuery = findViewById(R.id.etQuery)
			ivMicrophone = findViewById(R.id.ivMicrophone)
			animationAlert = AnimationAlert(findViewById(R.id.rlAlert))
		}
	}

	override fun setColors()
	{
		with(ThemeColor.currentColor)
		{
			activity?.let {
				llParent.setBackgroundColor(it.getParsedColor(drawerBackgroundColor))
				tvMsg.setTextColor(it.getParsedColor(drawerColorPrimary))
				ivRun.setColorFilter(it.getParsedColor(drawerColorPrimary))

				etQuery.setHintTextColor(it.getParsedColor(drawerColorPrimary))
				etQuery.setTextColor(it.getParsedColor(drawerColorPrimary))

				val blue = it.getParsedColor(drawerAccentColor)
				val circleDrawable = GradientDrawable().apply {
					shape = GradientDrawable.OVAL
					setColor(blue)
				}

				val white = it.getParsedColor(drawerBackgroundColor)
				val gray = it.getParsedColor(drawerColorPrimary)
				val rectangleDrawable = DrawableBuilder.setGradientDrawable(white,64f,1, gray)

				ivMicrophone.background = circleDrawable
				etQuery.background = rectangleDrawable

				etQuery.hint = if (PagerData.inputPlaceholder.isNotEmpty())
					PagerData.inputPlaceholder
				else
					getString(R.string.type_queries_here)
			}
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

	override fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>) {}

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
		activity?.let {
			val red = it.getParsedColor(android.R.color.holo_red_dark)
			val circleDrawable = GradientDrawable().apply {
				shape = GradientDrawable.OVAL
				setColor(red)
			}
			ivMicrophone.background = circleDrawable
		}
	}

	override fun setSpeech(message: String)
	{
		etQuery.setText(message)
		etQuery.setSelection(message.length)
	}

	override fun setStopRecorder()
	{
		activity?.let {
			val red = it.getParsedColor(ThemeColor.currentColor.drawerAccentColor)
			val circleDrawable = GradientDrawable().apply {
				shape = GradientDrawable.OVAL
				setColor(red)
			}
			ivMicrophone.background = circleDrawable
		}
	}

	override fun showAlert(message: String, intRes: Int)
	{
		animationAlert.run {
			setText(message)
			setResource(intRes)
			showAlert()
			Handler(Looper.getMainLooper()).postDelayed({ hideAlert() }, 1500)
		}
	}

	private fun initData()
	{
		arguments?.let {
			customerName = it.getString("CUSTOMER_NAME") ?: ""
			title = it.getString("TITLE") ?: ""
			introMessage = it.getString("INTRO_MESSAGE") ?: ""
			inputPlaceholder = it.getString("INPUT_PLACE_HOLDER") ?: ""
			maxMessages = it.getInt("MAX_MESSAGES")
			clearOnClose = it.getBoolean("CLEAR_ON_CLOSE", false)
			enableVoiceRecord = it.getBoolean("ENABLE_VOICE_RECORD", false)
		}
	}

	private fun initList()
	{
		activity?.let {
			chatAdapter = ChatAdapter(model, this, it)
			val introMessageRes = if (PagerData.introMessage.isNotEmpty())
				PagerData.introMessage
			else
				"Hi %s! Let\'s dive into your data. What can I help you discover today?"
			val introMessage = String.format(introMessageRes, PagerData.customerName)
			if (SinglentonDrawer.mModel.countData() == 0)
			{
				model.add(ChatData(TypeChatView.LEFT_VIEW, introMessage))
				model.add(ChatData(TypeChatView.QUERY_BUILDER, ""))
			}
			else
			{
				model[0]?.message = introMessage
			}

			rvChat.layoutManager = LinearLayoutManager(it)
			rvChat.adapter = chatAdapter
			scrollToPosition()
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
				presenter.getSafety(query)
			else
				presenter.getQuery(query)
		}
	}

	private fun initSpeechInput()
	{
		activity?.let {
			speechRecognizer = SpeechRecognizer.createSpeechRecognizer(it)
			speechRecognizer.setRecognitionListener(VoiceRecognition(this))
			speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
				putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
				putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
				putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, it.packageName)
				putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1)
			}
		}
	}

	private fun promptSpeechInput()
	{
		activity?.let {
			if (SpeechRecognizer.isRecognitionAvailable(it))
			{
				if (ContextCompat.checkSelfPermission(it, Manifest.permission.RECORD_AUDIO) != 0)
				{
					AlertDialog.Builder(it)
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
				AlertDialog.Builder(it)
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
	}

	@SuppressLint("ClickableViewAccessibility")
	private fun setTouchListener()
	{
		with(ivMicrophone)
		{
			if (PagerData.enableVoiceRecord)
			{
				setOnClickListener(null)
				setOnTouchListener { _, event ->
					when(event.action)
					{
						MotionEvent.ACTION_DOWN -> promptSpeechInput()
						MotionEvent.ACTION_UP -> speechRecognizer.stopListening()
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

	private fun notifyAdapter()
	{
		chatAdapter.notifyDataSetChanged()
	}

	private fun scrollToPosition()
	{
		while(model.countData() > PagerData.maxMessages)
		{
			model.removeAt(0)
			chatAdapter.notifyItemRemoved(0)
		}
		Handler(Looper.getMainLooper()).postDelayed({
			val position = model.countData() - 1
			rvChat.scrollToPosition(position)
		}, 200)
	}
}