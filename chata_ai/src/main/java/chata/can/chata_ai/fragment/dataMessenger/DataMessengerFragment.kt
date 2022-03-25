package chata.can.chata_ai.fragment.dataMessenger

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.*
import chata.can.chata_ai.fragment.dataMessenger.adapter.AutoCompleteAdapter
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapter
import chata.can.chata_ai.fragment.dataMessenger.presenter.ChatServicePresenter
import chata.can.chata_ai.fragment.dataMessenger.voice.VoiceRecognition
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.setOnTextChanged
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.SimpleQuery
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.suggestion.RequestSuggestion
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.core.AlertCustomBuilder
import chata.can.chata_ai.retrofit.ui.view.DataMessengerViewModel
import chata.can.chata_ai.view.animationAlert.AnimationAlert
import chata.can.chata_ai.view.typing.TypingAutoComplete
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class DataMessengerFragment: BaseFragment(), ChatContract.View
{
	companion object {
		const val nameFragment = "Data Messenger"
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
	private val aTmp = arrayListOf<String>()
	private lateinit var presenter: ChatServicePresenter
	private var dataMessengerTile = "Data Messenger"
	private var isReleaseAutocomplete = true
	private var statusLogin = false
	private var canonical = ""
	private var valueLabel = ""
	private var itemText = ""
	private var start = 0
	private var end = 0

	private var dataMessengerViewModel: DataMessengerViewModel ?= null

	override fun setView(inflater: LayoutInflater, container: ViewGroup?): View
	{
		val view = DataMessenger.getDesign(requireActivity())
		dataMessengerViewModel = DataMessengerViewModel()
		onRenderViews(view)
		return view
	}

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
//			val queryDemo = "Total revenue this year"
			val queryDemo = ""
			etQuery.setText(queryDemo)
		}

		SinglentonDrawer.aThemeMethods[nameFragment] = {
			model.restartData()
			notifyAdapter()
		}

		ThemeColor.aColorMethods[nameFragment] = {
			setColors()
			model.restartData()
			notifyAdapter()
		}

		SinglentonDrawer.aLocaleMethods[nameFragment] = {
			model.restartData()
			notifyAdapter()
		}

		SinglentonDrawer.aCurrencyMethods[nameFragment] = {
			model.restartData()
			notifyAdapter()
		}
	}

	override fun onResume()
	{
		super.onResume()
		val tmpFlagLanguage = Locale.getDefault().language
		val isReplace = SinglentonDrawer.flagLanguage.isNotEmpty()
		if (SinglentonDrawer.flagLanguage != tmpFlagLanguage)
		{
			SinglentonDrawer.flagLanguage = tmpFlagLanguage
			if (isReplace)
			{
				model[0]?.let {
					val introMessageRes = AutoQLData.introMessage.ifEmpty { getString(R.string.discover_today) }

					val introMessage = String.format(introMessageRes, AutoQLData.customerName)
					model[0] = ChatData(TypeChatView.LEFT_VIEW, introMessage)
					chatAdapter.notifyItemChanged(0)
				}
			}
		}
	}

	override fun initListener()
	{
		animationAlert.hideAlert()
		etQuery.setOnTextChanged { string ->
			if (string.isNotEmpty())
			{
				if (statusLogin)
				{
					adapterAutoComplete.clear()
					adapterAutoComplete.notifyDataSetChanged()
					if (SinglentonDrawer.mIsEnableAutocomplete && isReleaseAutocomplete)
					{
						dataMessengerViewModel?.getAutocomplete(string)
//						presenter.getAutocomplete(string)
					}
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
		setTouchListener()

		activity?.let { fragmentActivity ->
//			adapterAutoComplete = AutoCompleteAdapter(fragmentActivity, R.layout.row_spinner)
			adapterAutoComplete = AutoCompleteAdapter(fragmentActivity)
			etQuery.threshold = 1
			etQuery.setAdapter(adapterAutoComplete)
			fragmentActivity.findViewById<ImageView>(R.id.ivClear)?.setOnClickListener {

				AlertCustomBuilder(fragmentActivity)
					.setMessage("Clear all queries & responses?")
					.setPositiveButton("Clear") {
						clearQueriesAndResponses()
					}
					.setNegativeButton("Cancel")
					.show()
			}
		}
		etQuery.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
			val text = aTmp[position]
			etQuery.setText(text)
			setRequestQuery()
		}

		etQuery.setFinishAnimationListener {
			val query = etQuery.text.toString()
			hideKeyboard()
			isReleaseAutocomplete = true
			etQuery.setText("")

			if (canonical != "" && valueLabel != "")
			{
				val mUserSelection = hashMapOf<String, Any>(
					"end" to end,
					"start" to start,
					"value" to itemText,
					"value_label" to valueLabel,
					"canonical" to canonical)
				val mInfoHolder = hashMapOf<String, Any>("user_selection" to mUserSelection)
				canonical = ""
				valueLabel = ""
				itemText = ""
				start = 0
				end = 0
				presenter.getQuery(query, mInfoHolder, "data_messenger.validation")
			}
			else
			{
				if (query.isNotEmpty())
				{
					presenter.getQuery(query)
				}
			}
		}

		etQuery.setOnEditorActionListener { _, _, _ ->
			setRequestQuery()
			false
		}

		context?.resources?.displayMetrics?.let {
			etQuery.dropDownWidth = it.widthPixels
		}
	}

	private fun clearQueriesAndResponses()
	{
		statusLogin = AutoQLData.wasLoginIn
		model.clear()
		val introMessageRes =
			AutoQLData.introMessage.ifEmpty { getString(R.string.discover_today) }

		val introMessage = String.format(introMessageRes, AutoQLData.customerName)
		model.add(ChatData(TypeChatView.LEFT_VIEW, introMessage))
		if (statusLogin)
		{
			model.add(ChatData(TypeChatView.QUERY_BUILDER, ""))
		}
		chatAdapter.notifyItemRangeChanged(0, model.countData())
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
		val chatData = ChatData(TypeChatView.LEFT_VIEW, message)
		setSession(chatData)
		model.add(chatData)
		rvChat.post {
			chatAdapter.notifyItemChanged(model.countData() - 1)
		}
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
		setSession(chatData)
		model.add(chatData)
		rvChat.post {
			chatAdapter.notifyItemChanged(model.countData() - 1)
		}
		scrollToPosition()
	}

	override fun addNewChat(typeView: Int, queryBase: SimpleQuery)
	{
		val chatData = ChatData(typeView, "", queryBase)
		setSession(chatData)
		model.add(chatData)
		rvChat.post {
			chatAdapter.notifyItemChanged(model.countData() - 1)
		}
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
			View.INVISIBLE
		}
	}

	override fun runTyping(text: String)
	{
		isReleaseAutocomplete = false
		etQuery.setCharacterDelay(50)
		etQuery.animateText(text)
	}

	override fun runTyping(requestSuggestion: RequestSuggestion)
	{
		requestSuggestion.let {
			canonical = it.canonical
			valueLabel = it.valueLabel
			itemText = it.itemText
			start = it.start
			end = it.end
			runTyping(it.query)
		}
	}

	override fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>) {}

	override fun setDataAutocomplete(aMatches: ArrayList<String>)
	{
		adapterAutoComplete.clear()
		if (aMatches.isNotEmpty())
		{
			aTmp.clear()
			aTmp.addAll(aMatches)
			adapterAutoComplete.addAll(aMatches)

			context?.resources?.displayMetrics?.let {
				val maxHeight = it.heightPixels * 0.35

				val count = adapterAutoComplete.count
				val height = ScreenData.densityByDP * (if (count < 2) 2 else adapterAutoComplete.count) * 40

				etQuery.dropDownHeight =
					if (height < maxHeight)
						height.toInt()
					else
						maxHeight.toInt()
			}
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
		setStopRecorder()
		etQuery.setText(message)
		etQuery.setSelection(message.length)
	}

	override fun setStopRecorder()
	{
		activity?.let {
			val circleDrawable = GradientDrawable().apply {
				shape = GradientDrawable.OVAL
				setColor(SinglentonDrawer.currentAccent)
			}
			ivMicrophone.background = circleDrawable
		}
	}

	override fun showAlert(message: String, intRes: Int)
	{
		animationAlert.setText(message)
		animationAlert.setResource(intRes)
		animationAlert.showAlert()
		Looper.getMainLooper()?.let {
			Handler(it).postDelayed({
				animationAlert.hideAlert()
			},1500)
		}
	}

	override fun scrollToPosition()
	{
		var countToDown = model.getParseCount()
		val maxMessages = AutoQLData.maxMessages
		while(countToDown > maxMessages)
		{
			model[0]?.let { chatData ->
				if (chatData.simpleQuery == null)
				{
					countToDown--
					model.removeAt(0)
					rvChat.post {
						chatAdapter.notifyItemRemoved(0)
					}
				}
				else
				{
					if (countToDown % maxMessages == 1)
					{
						countToDown--
						chatData.simpleQuery.visibleTop = false
						rvChat.post {
							chatAdapter.notifyItemChanged(0)
						}
					}
					else
					{
						countToDown -= 2
						model.removeAt(0)
					}
				}
			}
		}
		Handler(Looper.getMainLooper()).postDelayed({
			val position = model.countData() - 1
			rvChat.smoothScrollToPosition(position)
		}, 200)
	}

	override fun showToast()
	{
		activity?.let {
			Toast.makeText(it, R.string.limit_row_num, Toast.LENGTH_LONG).show()
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
		if (!statusLogin)
		{
			model.clear()
		}
		SinglentonDrawer.aThemeMethods.remove(nameFragment)
		ThemeColor.aColorMethods.remove(nameFragment)
		SinglentonDrawer.aLocaleMethods.remove(nameFragment)
		SinglentonDrawer.aCurrencyMethods.remove(nameFragment)
	}

	private fun initData()
	{
		arguments?.let {
			AutoQLData.run {
				customerName = it.getString("CUSTOMER_NAME") ?: ""
				title = it.getString("TITLE") ?: ""
				introMessage = it.getString("INTRO_MESSAGE") ?: ""
				inputPlaceholder = it.getString("INPUT_PLACE_HOLDER") ?: ""
				maxMessages = it.getInt("MAX_MESSAGES")
				clearOnClose = it.getBoolean("CLEAR_ON_CLOSE", false)
				enableVoiceRecord = it.getBoolean("ENABLE_VOICE_RECORD", false)
			}
		}
		val title = AutoQLData.title
		dataMessengerTile = title.ifEmpty { getString(R.string.data_messenger) }

		etQuery.hint = AutoQLData.inputPlaceholder.ifEmpty { getString(R.string.type_queries_here) }
	}

	private fun initList()
	{
		activity?.let {
			chatAdapter = ChatAdapter(model, this, it)
			val introMessageRes = AutoQLData.introMessage.ifEmpty { getString(R.string.discover_today) }

			statusLogin = AutoQLData.wasLoginIn

			if (model.countData() == 0)
			{
				val introMessage = String.format(introMessageRes, AutoQLData.customerName)
				model.add(ChatData(TypeChatView.LEFT_VIEW, introMessage))
				if (statusLogin)
				{
					model.add(ChatData(TypeChatView.QUERY_BUILDER, ""))
				}
			}

			val llm = LinearLayoutManager(it)
			llm.orientation = LinearLayoutManager.VERTICAL
			rvChat.layoutManager = llm
			rvChat.adapter = chatAdapter
			rvChat.itemAnimator = null
		}
	}

	private fun setRequestQuery()
	{
		val query = etQuery.text.toString()
		if (query.isNotEmpty())
		{
			hideKeyboard()
			etQuery.setText("")

			if (!AutoQLData.wasLoginIn)
			{
				addChatMessage(TypeChatView.LEFT_VIEW, getString(R.string.it_looks_like), query)
			}
			else
			{
				if (SinglentonDrawer.mIsEnableQuery)
					presenter.getSafety(query)
				else
					presenter.getQuery(query)
			}
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

	//region permissions
	private val aPermission = arrayOf(Manifest.permission.RECORD_AUDIO)
	private val permissionRequiredLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
		permissions ->
		val grated = permissions.entries.all { it.value }
		if (grated) println("Permission grated")
	}

	private fun hasPermission(context: Context, permission: Array<String>): Boolean = permission.all {
		ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
	}
	//endregion

	private fun promptSpeechInput()
	{
		activity?.let {
			if (SpeechRecognizer.isRecognitionAvailable(it))
			{
				if (!hasPermission(it, aPermission))
				{
					AlertCustomBuilder(it)
						.setMessage(R.string.msg_permission_record)
						.setNeutralButton("Ok")
						.setOnDismissListener {
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
								permissionRequiredLauncher.launch(aPermission)
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
				AlertCustomBuilder(it)
					.setMessage(R.string.msg_not_speech)
					.setNeutralButton("Ok")
					.show()
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	private fun setTouchListener()
	{
		with(ivMicrophone)
		{
			if (AutoQLData.enableVoiceRecord)
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

	private fun setSession(chatData: ChatData)
	{
		chatData.simpleQuery?.isSession = statusLogin
	}

	private fun notifyAdapter()
	{
		chatAdapter.notifyItemRangeChanged(0, model.countData())
	}
}