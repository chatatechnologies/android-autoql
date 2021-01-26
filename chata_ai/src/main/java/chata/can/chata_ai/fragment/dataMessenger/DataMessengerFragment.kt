package chata.can.chata_ai.fragment.dataMessenger

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
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
import chata.can.chata_ai.*
import chata.can.chata_ai.fragment.dataMessenger.adapter.AutoCompleteAdapter
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapter
import chata.can.chata_ai.fragment.dataMessenger.presenter.ChatServicePresenter
import chata.can.chata_ai.fragment.dataMessenger.voice.VoiceRecognition
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.SimpleQuery
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.animationAlert.AnimationAlert
import chata.can.chata_ai.view.typing.TypingAutoComplete
import org.json.JSONObject

class DataMessengerFragment: BaseFragment(), ChatContract.View
{
	companion object {
		const val nameFragment = "Data Messenger"
		fun newInstance() = DataMessengerFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_data_messenger)
		}
		var exploreQueriesMethod: (() -> Unit)? = null
		var queryToTyping = ""
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
	private var dataMessengerTile = "Data Messenger"
	var isReleaseAutocomplete = true

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		activity?.let {
			presenter = ChatServicePresenter(it, this)
		}
		initData()
		initList()
		initSpeechInput()
		if (queryToTyping.isNotEmpty())
		{
			val tmp = queryToTyping
			queryToTyping = ""
			runTyping(tmp)
		}
		if (BuildConfig.DEBUG)
		{
//			val queryDemo = "all sales"
//			val queryDemo = "total hours utilization for equipment by month in 2017 for water trailer"
//			val queryDemo = "Total revenue by ticket type for Q1 2019"
//			val queryDemo = "Total revenue by month in 2019"
			val queryDemo = "all ticket"
			etQuery.setText(queryDemo)
		}

		SinglentonDrawer.aThemeMethods[nameFragment] = {
			model.restartData()
			chatAdapter.notifyDataSetChanged()
		}

		ThemeColor.aColorMethods[nameFragment] = {
			setColors()
			model.restartData()
			chatAdapter.notifyDataSetChanged()
		}

		SinglentonDrawer.aLocaleMethods[nameFragment] = {
			model.restartData()
			chatAdapter.notifyDataSetChanged()
		}

		SinglentonDrawer.aCurrencyMethods[nameFragment] = {
			model.restartData()
			chatAdapter.notifyDataSetChanged()
		}
	}

	override fun initListener()
	{
		animationAlert.hideAlert()
		etQuery.addTextChangedListener(object: TextChanged {
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					adapterAutoComplete.clear()
					adapterAutoComplete.notifyDataSetChanged()
					if (SinglentonDrawer.mIsEnableAutocomplete && isReleaseAutocomplete)
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

		activity?.let { fragmentActivity ->
			adapterAutoComplete = AutoCompleteAdapter(fragmentActivity, R.layout.row_spinner)
			etQuery.threshold = 1
			etQuery.setAdapter(adapterAutoComplete)
			fragmentActivity.findViewById<ImageView>(R.id.ivClear)?.setOnClickListener {
				AlertDialog.Builder(fragmentActivity)
					.setMessage("Clear all queries & responses?")
					.setPositiveButton("Clear") { _, _ ->
						clearQueriesAndResponses()
					}
					.setNegativeButton("Cancel", null).show()
			}
		}
		etQuery.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
			val text = aTmp[position]
			etQuery.setText(text)
			setRequestQuery()
		}

		etQuery.setFinishAnimationListener {
			val query = etQuery.text.toString()
			if (query.isNotEmpty())
			{
				hideKeyboard()
				isReleaseAutocomplete = true
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

	fun updateData(arguments: Bundle)
	{
		arguments.let {
			DataMessengerData.run {
				customerName = it.getString("CUSTOMER_NAME") ?: ""
				title = it.getString("TITLE") ?: ""
				introMessage = it.getString("INTRO_MESSAGE") ?: ""
				inputPlaceholder = it.getString("INPUT_PLACE_HOLDER") ?: ""
				maxMessages = it.getInt("MAX_MESSAGES")
				clearOnClose = it.getBoolean("CLEAR_ON_CLOSE", false)
				enableVoiceRecord = it.getBoolean("ENABLE_VOICE_RECORD", false)
			}
			val title = DataMessengerData.title
			dataMessengerTile = if (title.isNotEmpty())
				title
			else getString(R.string.data_messenger)

			etQuery.hint = if (DataMessengerData.inputPlaceholder.isNotEmpty())
				DataMessengerData.inputPlaceholder
			else
				getString(R.string.type_queries_here)
		}
		if (model.countData() == 2)
		{
			clearQueriesAndResponses()
		}
		else
		{
			val introMessageRes =
				if (DataMessengerData.introMessage.isNotEmpty())
					DataMessengerData.introMessage
				else
					"Hi %s! Let\'s dive into your data. What can I help you discover today?"
			val introMessage = String.format(introMessageRes, DataMessengerData.customerName)
			model[0]?.message = introMessage
			chatAdapter.notifyItemChanged(0)
		}
		setTouchListener()
		setColors()
	}

	fun clearQueriesAndResponses()
	{
		model.clear()
		val introMessageRes =
			if (DataMessengerData.introMessage.isNotEmpty())
				DataMessengerData.introMessage
			else
				"Hi %s! Let\'s dive into your data. What can I help you discover today?"

		val introMessage = String.format(introMessageRes, DataMessengerData.customerName)
		model.add(ChatData(TypeChatView.LEFT_VIEW, introMessage))
		model.add(ChatData(TypeChatView.QUERY_BUILDER, ""))
		chatAdapter.notifyDataSetChanged()
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
				llParent.setBackgroundColor(pDrawerColorSecondary)
				rvChat.setBackgroundColor(pDrawerColorSecondary)
				tvMsg.setTextColor(it.getParsedColor(R.color.we_run))
				ivRun.setColorFilter(it.getParsedColor(R.color.we_run))

				etQuery.setHintTextColor(it.getParsedColor(R.color.place_holder))
				etQuery.setTextColor(pDrawerTextColorPrimary)

				val circleDrawable = GradientDrawable().apply {
					shape = GradientDrawable.OVAL
					setColor(SinglentonDrawer.currentAccent)
				}

				val rectangleDrawable = DrawableBuilder.setGradientDrawable(
					pDrawerBackgroundColor,
					64f,
					1,
					pDrawerTextColorPrimary)

				ivMicrophone.background = circleDrawable
				etQuery.background = rectangleDrawable

				etQuery.setDropDownBackgroundDrawable(DrawableBuilder.setGradientDrawable(
					pDrawerBackgroundColor,
					64f,
					1,
					pDrawerTextColorPrimary))
			}
		}
	}

	override fun addSimpleText(message: String)
	{
		model.add(ChatData(TypeChatView.LEFT_VIEW, message))
		chatAdapter.notifyItemChanged(model.countData() - 1)
		scrollToPosition()
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
		{
			etQuery.isEnabled = false
			View.VISIBLE
		}
		else
		{
			etQuery.isEnabled = true
			View.GONE
		}
	}

	override fun runTyping(text: String)
	{
		isReleaseAutocomplete = false
		etQuery.setCharacterDelay(50)
		etQuery.animateText(text)
	}

	override fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>) {}

	private val aTmp = arrayListOf<String>()
	override fun setDataAutocomplete(aMatches: ArrayList<String>)
	{
		adapterAutoComplete.clear()
		if (aMatches.isNotEmpty())
		{
			aTmp.clear()
			aTmp.addAll(aMatches)
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
			val circleDrawable = GradientDrawable().apply {
				shape = GradientDrawable.OVAL
				setColor(ThemeColor.currentColor.pDrawerAccentColor)
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

	override fun onPause()
	{
		super.onPause()
		hideKeyboard()
	}

	override fun onDestroy()
	{
		super.onDestroy()
		SinglentonDrawer.aThemeMethods.remove(nameFragment)
		ThemeColor.aColorMethods.remove(nameFragment)
		SinglentonDrawer.aLocaleMethods.remove(nameFragment)
		SinglentonDrawer.aCurrencyMethods.remove(nameFragment)
	}

	private fun initData()
	{
		arguments?.let {
			DataMessengerData.run {
				customerName = it.getString("CUSTOMER_NAME") ?: ""
				title = it.getString("TITLE") ?: ""
				introMessage = it.getString("INTRO_MESSAGE") ?: ""
				inputPlaceholder = it.getString("INPUT_PLACE_HOLDER") ?: ""
				maxMessages = it.getInt("MAX_MESSAGES")
				clearOnClose = it.getBoolean("CLEAR_ON_CLOSE", false)
				enableVoiceRecord = it.getBoolean("ENABLE_VOICE_RECORD", false)
			}
		}
		val title = DataMessengerData.title
		dataMessengerTile = if (title.isNotEmpty())
			title
		else getString(R.string.data_messenger)

		etQuery.hint = if (DataMessengerData.inputPlaceholder.isNotEmpty())
			DataMessengerData.inputPlaceholder
		else
			getString(R.string.type_queries_here)
	}

	private fun initList()
	{
		activity?.let {
			chatAdapter = ChatAdapter(model, this, it)
			val introMessageRes = if (DataMessengerData.introMessage.isNotEmpty())
				DataMessengerData.introMessage
			else
				"Hi %s! Let\'s dive into your data. What can I help you discover today?"
			val introMessage = String.format(introMessageRes, DataMessengerData.customerName)
			if (model.countData() == 0)
			{
				model.add(ChatData(TypeChatView.LEFT_VIEW, introMessage))
				model.add(ChatData(TypeChatView.QUERY_BUILDER, ""))
			}
			else
			{
				model[0]?.message = introMessage
			}

			val llm = LinearLayoutManager(it)
			llm.orientation = LinearLayoutManager.VERTICAL
			rvChat.layoutManager = llm
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
			if (DataMessengerData.enableVoiceRecord)
			{
				setImageResource(R.drawable.ic_microphone)
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

	private fun scrollToPosition()
	{
		//while(model.getParseCount() > DataMessengerData.maxMessages)
		while(model.countData() > DataMessengerData.maxMessages)
		{
			model.removeAt(0)
			chatAdapter.notifyItemRemoved(0)
		}
		Handler(Looper.getMainLooper()).postDelayed({
			val position = model.countData() - 1
			rvChat.smoothScrollToPosition(position)
//			rvChat.scrollToPosition(position)
		}, 200)
	}
}