package chata.can.chata_ai.fragment.dataMessenger

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.speech.SpeechRecognizer
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.BuildConfig
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.pager.PagerData
import chata.can.chata_ai.fragment.dataMessenger.adapter.AutoCompleteAdapter
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapter
import chata.can.chata_ai.fragment.dataMessenger.voice.VoiceRecognition
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.SimpleQuery
import chata.can.chata_ai.pojo.chat.TypeChatView
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.putArgs
import java.net.URLEncoder

class DataMessengerFragment: BaseFragment(), ChatContract.View
{
	companion object {
		//const val nameFragment = "Data Messenger"
		fun newInstance() = DataMessengerFragment()
			.putArgs {
			putInt("LAYOUT", R.layout.fragment_date_messenger)
		}
	}

	private lateinit var llParent: View
	private lateinit var rvChat: RecyclerView
	private lateinit var gifView: View
	private lateinit var ivRun: ImageView
	private lateinit var etQuery: AutoCompleteTextView
	private lateinit var ivMicrophone: ImageView

	private lateinit var speechRecognizer: SpeechRecognizer
	private lateinit var speechIntent: Intent

	private val model = SinglentonDrawer.mModel
	private lateinit var adapterAutoComplete: AutoCompleteAdapter
	private lateinit var renderPresenter: ChatRenderPresenter
	private lateinit var servicePresenter: ChatServicePresenter
	private lateinit var chatAdapter: ChatAdapter

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		activity?.run {
			renderPresenter = ChatRenderPresenter(this, this@DataMessengerFragment)
			servicePresenter = ChatServicePresenter(this, this@DataMessengerFragment)
		}

		initList()
		renderPresenter.setData()
		initSpeechInput()
	}

	override fun initViews(view: View)
	{
		with(view)
		{
			llParent = findViewById(R.id.llParent)
			rvChat = findViewById(R.id.rvChat)
			gifView = findViewById(R.id.gifView)
			ivRun = findViewById(R.id.ivRun)
			etQuery = findViewById(R.id.etQuery)
			ivMicrophone = findViewById(R.id.ivMicrophone)
		}
	}

	override fun initListener()
	{
		etQuery.addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					if (SinglentonDrawer.mIsEnableAutocomplete)
					{
						servicePresenter.getAutocomplete(URLEncoder.encode(string, "UTF-8"))
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
				etQuery.setHintTextColor(ContextCompat.getColor(
					activity, drawerHoverColor))
				ivRun.setColorFilter(ContextCompat.getColor(
					activity, R.color.chata_drawer_hover_color_dark))
			}
		}
	}

	override fun addChatMessage(typeView: Int, message: String)
	{
		val chatData = ChatData(typeView, message)
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

	override fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>)
	{
		etQuery.run {
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

			activity?.let {
				activity ->
				adapterAutoComplete = AutoCompleteAdapter(activity, R.layout.row_spinner)
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
				val urlDemo = "total revenue by month by tracking category"
				//val urlDemo = ""
				setText(urlDemo)
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
		activity?.run {
			val red = ContextCompat.getColor(this, android.R.color.holo_red_dark)
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
		activity?.run {
			val red = ContextCompat.getColor(
				this,
				ThemeColor.currentColor.drawerAccentColor)
			val circleDrawable = GradientDrawable().apply {
				shape = GradientDrawable.OVAL
				setColor(red)
			}
			ivMicrophone.background = circleDrawable
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
		activity?.run {
			speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
			speechRecognizer.setRecognitionListener(VoiceRecognition(this@DataMessengerFragment))

			speechIntent = renderPresenter.initSpeechInput()
		}
	}

	private fun promptSpeechInput()
	{
		activity?.run {
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
		activity?.let {
			activity ->
			chatAdapter = ChatAdapter(model, this, servicePresenter, activity)

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
//				model.add(ChatData(TypeChatView.QUERY_BUILDER, ""))
			}
			else
			{
				model[0]?.let {
					it.message = introMessage
				}
			}

			rvChat.layoutManager = LinearLayoutManager(activity)
			rvChat.adapter = chatAdapter
		}
	}

	fun notifyAdapter()
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