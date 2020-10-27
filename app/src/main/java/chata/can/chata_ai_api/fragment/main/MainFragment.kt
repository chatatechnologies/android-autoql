package chata.can.chata_ai_api.fragment.main

import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.isColor
import chata.can.chata_ai.extension.setOnTextChanged
import chata.can.chata_ai.model.BubbleData
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.putArgs
import chata.can.chata_ai.view.ProgressWait
import chata.can.chata_ai.view.animationAlert.AnimationAlert
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.apiKey
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.domainUrl
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.password
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.projectId
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.userID
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.username
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.loginIsComplete
import chata.can.chata_ai_api.*
import chata.can.chata_ai_api.main.PagerActivity

class MainFragment: BaseFragment(), View.OnClickListener, MainContract
{
	companion object {
		const val nameFragment = "Data Messenger"
		fun newInstance() = MainFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_main)
		}
		private var bubbleHandle: BubbleHandle ?= null
	}

	private lateinit var llContainer: LinearLayout
	//private var swDemoData: Switch ?= null
	private var hProjectId: TextView ?= null
	private var tvProjectId: EditText?= null
	private var hUserId: TextView ?= null
	private var tvUserId: EditText ?= null
	private var hApiKey: TextView ?= null
	private var tvApiKey: EditText ?= null
	private var hDomainUrl: TextView ?= null
	private var tvDomainUrl: EditText ?= null
	private var hUsername: TextView ?= null
	private var tvUsername: EditText ?= null
	private var hPassword: TextView ?= null
	private var tvPassword: EditText ?= null
	private var btnAuthenticate: TextView ?= null

	private var btnReloadDrawer: TextView ?= null
	private var btnOpenDrawer: TextView ?= null
	private var swDrawerHandle: SwitchCompat ?= null
	private var etCurrencyCode: EditText ?= null
	private var etFormatMonthYear: EditText ?= null
	private var etFormatDayMonthYear: EditText ?= null
	private var etLanguageCode: EditText ?= null
	private var etDecimalsCurrency: EditText ?= null
	private var etDecimalsQuantity: EditText ?= null
	private var etCustomerMessage: EditText ?= null
	private var etIntroMessage: EditText ?= null
	private var etQueryPlaceholder: EditText ?= null
	private var swClearMessage: SwitchCompat ?= null
	private var etTitle: EditText ?= null
	private var llColors: LinearLayout ?= null
	private var etAddColor: EditText ?= null
	private var etLightThemeColor: EditText ?= null
	private var etDarkThemeColor: EditText ?= null
	private var etMaxNumberMessage: EditText ?= null
	private var swEnableAutocomplete: SwitchCompat ?= null
	private var swEnableQuery: SwitchCompat ?= null
	private var swEnableSuggestion: SwitchCompat ?= null
	private var swEnableDrillDown: SwitchCompat ?= null
	private var swEnableSpeechText: SwitchCompat ?= null
	private lateinit var animationAlert: AnimationAlert
	//import module https://developer.android.com/studio/projects/android-library

	private lateinit var renderPresenter: MainRenderPresenter
	private lateinit var servicePresenter: MainServicePresenter
	private val mViews = CustomViews.mViews

	private val mTheme = hashMapOf(
		R.id.tvLight to BubbleHandle.THEME_LIGHT,
		R.id.tvDark to BubbleHandle.THEME_DARK)

	private val mPlacement = hashMapOf(
		R.id.tvTop to ConstantDrawer.TOP_PLACEMENT,
		R.id.tvBottom to ConstantDrawer.BOTTOM_PLACEMENT,
		R.id.tvLeft to ConstantDrawer.LEFT_PLACEMENT,
		R.id.tvRight to ConstantDrawer.RIGHT_PLACEMENT)

	private var isAuthenticate = false

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
//		activity?.let {
//			val sharePreferences = it.getPreferences(Context.MODE_PRIVATE) ?: return
//			with(sharePreferences)
//			{
//				val projectId = getString("PROJECT_ID", "") ?: ""
//				val apiKey = getString("API_KEY", "") ?: ""
//				val domainUrl = getString("DOMAIN_URL", "") ?: ""
//				tvProjectId?.setText(projectId)
//				tvApiKey?.setText(apiKey)
//				tvDomainUrl?.setText(domainUrl)
//			}
//		}

		if (BuildConfig.DEBUG)
		//if (true)
		{
			//\u0020\u00A0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u2028\u205F\u3000c
			val projectId = "spira-demo3"
//			val projectId = "accounting-demo"
			tvProjectId?.setText(projectId)
			val domainUrl = "https://spira-staging.chata.io"
//			val domainUrl = "https://accounting-demo-staging.chata.io"
			tvDomainUrl?.setText(domainUrl)
			val apiKey = "AIzaSyD4ewBvQdgdYfXl3yIzXbVaSyWGOcRFVeU"
//			val apiKey = "AIzaSyDX28JVW248PmBwN8_xRROWvO0a2BWH67o"
			tvApiKey?.setText(apiKey)
			val userId = "carlos@rinro.com.mx"
			tvUserId?.setText(userId)
			val username = "admin"
			tvUsername?.setText(username)
			val password = "admin123"
			tvPassword?.setText(password)
			tvPassword?.setSelection(password.length)

			val customerMessage = "Carlos"
			etCustomerMessage?.setText(customerMessage)
			val title = nameFragment
			etTitle?.setText(title)
			val maxMessage = 10
			etMaxNumberMessage?.setText("$maxMessage")
			etLanguageCode?.setText("es-MX")
			DataMessenger.projectId = (tvProjectId?.text ?: "").toString().trim()
			userID = (tvUserId?.text ?: "").toString().trim()
			DataMessenger.apiKey = (tvApiKey?.text ?: "").toString().trim()
			DataMessenger.domainUrl = (tvDomainUrl?.text ?: "").toString().prepareDomain()
			DataMessenger.username = (tvUsername?.text ?: "").toString().trim()
			DataMessenger.password = (tvPassword?.text ?: "").toString().trim()

			servicePresenter.createAuthenticate()
			showDialog()
		}
		else
		{
			etCustomerMessage?.setText((etCustomerMessage?.text ?: "").trim())
			etTitle?.setText((etTitle?.text ?: "").trim())
			etIntroMessage?.setText((etIntroMessage?.text ?: "").trim())
			etQueryPlaceholder?.setText((etQueryPlaceholder?.text ?: "").trim())
			etMaxNumberMessage?.setText((etMaxNumberMessage?.text ?: "").trim())
		}
	}

	override fun initViews(view: View)
	{
		with(view)
		{
			llContainer = findViewById(R.id.llContainer)
			parentActivity?.let { context ->
				if (bubbleHandle == null)
				{
					bubbleHandle = BubbleHandle(context) {
						bubbleHandle?.let { bubbleHandle ->
							val bubbleData = BubbleData(
								bubbleHandle.userDisplayName,
								bubbleHandle.title,
								bubbleHandle.introMessage,
								bubbleHandle.inputPlaceholder,
								bubbleHandle.maxMessages,
								bubbleHandle.clearOnClose,
								bubbleHandle.enableVoiceRecord)
							(parentActivity as? PagerActivity)?.setStatusGUI(true, bubbleData)
						}
					}
				}
				bubbleHandle?.let {
					renderPresenter = MainRenderPresenter(context, this@MainFragment, it)
				}
				servicePresenter = MainServicePresenter(this@MainFragment)
			}
			renderPresenter.initViews(llContainer)
			animationAlert = AnimationAlert(findViewById(R.id.rlAlert))

			//swDemoData = findViewById(R.id.swDemoData)
			hProjectId = findViewById(R.id.hProjectId)
			tvProjectId = findViewById(R.id.etProjectId)
			hUserId = findViewById(R.id.hUserId)
			tvUserId = findViewById(R.id.etUserId)
			hApiKey = findViewById(R.id.hApiKey)
			tvApiKey = findViewById(R.id.etApiKey)
			hDomainUrl = findViewById(R.id.hDomainUrl)
			tvDomainUrl = findViewById(R.id.etDomainUrl)
			hUsername = findViewById(R.id.hUsername)
			tvUsername = findViewById(R.id.etUsername)
			hPassword = findViewById(R.id.hPassword)
			tvPassword = findViewById(R.id.etPassword)
			btnAuthenticate = findViewById(R.id.btnAuthenticate)
			btnReloadDrawer = findViewById(R.id.btnReloadDrawer)
			btnOpenDrawer = findViewById(R.id.btnOpenDrawer)
			swDrawerHandle = findViewById(R.id.swDrawerHandle)
			etCurrencyCode = findViewById(R.id.etCurrencyCode)
			etFormatMonthYear = findViewById(R.id.etFormatMonthYear)
			etFormatDayMonthYear = findViewById(R.id.etFormatDayMonthYear)
			etLanguageCode = findViewById(R.id.etLanguageCode)
			etDecimalsCurrency = findViewById(R.id.etDecimalsCurrency)
			etDecimalsQuantity = findViewById(R.id.etDecimalsQuantity)
			etCustomerMessage = findViewById(R.id.etCustomerMessage)
			etIntroMessage = findViewById(R.id.etIntroMessage)
			etQueryPlaceholder = findViewById(R.id.etQueryPlaceholder)
			swClearMessage = findViewById(R.id.swClearMessage)
			etTitle = findViewById(R.id.etTitle)
			//region llColors
			llColors = findViewById<LinearLayout>(R.id.llColors)?.apply {
				for (index in 0 until this.childCount)
				{
					val child = this.getChildAt(index)
					if (child is EditText)
					{
						child.setOnTextChanged {
							subColor ->
							try
							{
								val pData = subColor.isColor()
								if (pData.second)
								{
									child.setBackgroundColor(Color.parseColor(pData.first))
									bubbleHandle?.changeColor(child.tag?.toString()?.toInt() ?: 0, pData.first)
								}
							}
							catch (ex: Exception) {}
						}
					}
				}
			}
			//endregion
			etAddColor = findViewById(R.id.etAddColor)
			etLightThemeColor = findViewById(R.id.etLightThemeColor)
			etDarkThemeColor = findViewById(R.id.etDarkThemeColor)
			etMaxNumberMessage = findViewById(R.id.etMaxNumberMessage)
			swEnableAutocomplete = findViewById(R.id.swEnableAutocomplete)
			swEnableQuery = findViewById(R.id.swEnableQuery)
			swEnableSuggestion = findViewById(R.id.swEnableSuggestion)
			swEnableDrillDown = findViewById(R.id.swEnableDrillDown)
			swEnableSpeechText = findViewById(R.id.swEnableSpeechText)
		}


	}

	override fun setColors()
	{
		activity?.let {
			activity ->
			for ((_, value) in mViews)
			{
				for (index in 0 until value.size())
				{
					val key = value.keyAt(index)
					val isEnabled = value[key]
					llContainer.findViewById<TextView>(key)?.let {
							tv ->
						if (isEnabled)
						{
							tv.setBackgroundColor(activity.getParsedColor(R.color.blue))
							tv.setTextColor(Color.WHITE)
						}
						else
						{
							tv.setBackgroundColor(Color.WHITE)
							tv.setTextColor(Color.BLACK)
						}
					}
				}
			}
		}
	}

	override fun initListener() {
//		swDemoData?.setOnCheckedChangeListener {
//			_, isChecked ->
//			val iVisible = if (!isChecked) View.VISIBLE else View.GONE
//
//			hProjectId?.visibility = iVisible
//			tvProjectId?.visibility = iVisible
//			hUserId?.visibility = iVisible
//			tvUserId?.visibility = iVisible
//			hApiKey?.visibility = iVisible
//			tvApiKey?.visibility = iVisible
//			hDomainUrl?.visibility = iVisible
//			tvDomainUrl?.visibility = iVisible
//			hUsername?.visibility = iVisible
//			tvUsername?.visibility = iVisible
//			hPassword?.visibility = iVisible
//			tvPassword?.visibility = iVisible
//			btnAuthenticate?.visibility = iVisible
//			bubbleHandle.isNecessaryLogin = !isChecked
//		}
		animationAlert.hideAlert()

		btnAuthenticate?.setOnClickListener(this)
		btnReloadDrawer?.setOnClickListener(this)
		btnOpenDrawer?.setOnClickListener(this)

		swDrawerHandle?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle?.isVisible = isChecked
		}

		etCurrencyCode?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				try
				{
					bubbleHandle?.dataFormatting?.currencyCode = it
				}
				catch (e: Exception) {}
			}
		}

		etFormatMonthYear?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				bubbleHandle?.dataFormatting?.monthYearFormat = it
			}
		}

		etFormatDayMonthYear?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				bubbleHandle?.dataFormatting?.dayMonthYearFormat = it
			}
		}

		etLanguageCode?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				bubbleHandle?.dataFormatting?.languageCode = it
			}
		}

		etDecimalsCurrency?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				it.toIntOrNull()?.let {
						integer ->
					bubbleHandle?.dataFormatting?.currencyDecimals = integer
				}
			}
		}

		etDecimalsQuantity?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				it.toIntOrNull()?.let {
						integer ->
					bubbleHandle?.dataFormatting?.quantityDecimals = integer
				}
			}
		}

		etCustomerMessage?.setOnTextChanged {
			bubbleHandle?.userDisplayName = it
		}

		etIntroMessage?.setOnTextChanged {
			bubbleHandle?.introMessage = it
		}

		etQueryPlaceholder?.setOnTextChanged {
			bubbleHandle?.inputPlaceholder = it
		}

		swClearMessage?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle?.clearOnClose = isChecked
		}

		etTitle?.setOnTextChanged {
			bubbleHandle?.title = it
		}

		etAddColor?.setOnEditorActionListener {
			textView, _, _ ->
			val text = (textView.text ?: "").toString()

			val pData = text.isColor()
			if (pData.second)
			{
				val newColor = pData.first
				llColors?.let { llColors ->
					val newView = CustomViews.setNewColor(textView.context, newColor, llColors.childCount)
					llColors.addView(newView, llColors.childCount)
				}

				bubbleHandle?.addChartColor(newColor)
			}

			textView.text = ""
			false
		}

		etLightThemeColor?.setOnTextChanged {
			try
			{
				val pData = it.isColor()
				if (pData.second)
				{
					etLightThemeColor?.setBackgroundColor(Color.parseColor(pData.first))
					bubbleHandle?.setLightThemeColor(it)
				}
			}
			catch (ex: Exception) {}
		}

		etDarkThemeColor?.setOnTextChanged {
			try
			{
				val pData = it.isColor()
				if (pData.second)
				{
					etDarkThemeColor?.setBackgroundColor(Color.parseColor(pData.first))
					bubbleHandle?.setDarkThemeColor(it)
				}
			}
			catch (ex: Exception) {}
		}

		etMaxNumberMessage?.setOnTextChanged {
			it.toIntOrNull()?.let {
				integer ->
				bubbleHandle?.maxMessages = integer
			}
		}

		swEnableAutocomplete?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle?.autoQLConfig?.enableAutocomplete = isChecked
		}

		swEnableQuery?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle?.autoQLConfig?.enableQueryValidation = isChecked
		}

		swEnableSuggestion?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle?.autoQLConfig?.enableQuerySuggestions = isChecked
		}

		swEnableDrillDown?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle?.autoQLConfig?.enableDrilldowns = isChecked
		}

		swEnableSpeechText?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle?.enableVoiceRecord = isChecked
		}
	}

//	override fun onResume()
//	{
//		super.onResume()
//		if (::bubbleHandle.isInitialized && !bubbleHandle.isVisible)
//		{
//			bubbleHandle.isVisible = true
//		}
//	}

//	override fun onPause()
//	{
//		super.onPause()
//		if (::bubbleHandle.isInitialized)
//		{
//			bubbleHandle.isVisible = false
//		}
//	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.btnAuthenticate ->
				{
					isEnableLogin(false)
					if (isAuthenticate)
					{
						DataMessenger.clearData()
						isAuthenticate = false
						loginIsComplete = false
						changeStateAuthenticate()
						showAlert("Successfully logged out", R.drawable.ic_done)

						bubbleHandle?.setImageResource(R.drawable.ic_bubble_chata)
						bubbleHandle?.setBackgroundColor(R.color.blue_chata_circle)
					}
					else
					{
						projectId = (tvProjectId?.text ?: "").toString().trim()
						userID = (tvUserId?.text ?: "").toString().trim()
						apiKey = (tvApiKey?.text ?: "").toString().trim()
						domainUrl = (tvDomainUrl?.text ?: "").toString().prepareDomain()
						username = (tvUsername?.text ?: "").toString().trim()
						password = (tvPassword?.text ?: "").toString().trim()

						servicePresenter.createAuthenticate()
						showDialog()
					}
				}
				R.id.btnReloadDrawer ->
				{
					activity?.run {
						if (this is PagerActivity)
						{
							bubbleHandle?.let { bubbleHandle ->
								val bubbleData = BubbleData(
									bubbleHandle.userDisplayName,
									bubbleHandle.title,
									bubbleHandle.introMessage,
									bubbleHandle.inputPlaceholder,
									bubbleHandle.maxMessages,
									bubbleHandle.clearOnClose,
									bubbleHandle.enableVoiceRecord)
								clearDataMessenger(bubbleData)
							}
						}
					}
				}
				R.id.btnOpenDrawer ->
				{
					bubbleHandle?.openChatActivity()
				}
				R.id.tvLight, R.id.tvDark ->
				{
					if (it is TextView)
					{
						if (it.tag is String)
						{
							setColorOption(it.tag as String, it.id)
						}
						mTheme[it.id]?.let {
							config ->
							val theme = if (config) "light" else "dark"
							bubbleHandle?.theme = theme
						}
					} else {}
				}
				R.id.tvTop, R.id.tvBottom, R.id.tvLeft, R.id.tvRight ->
				{
					if (it is TextView)
					{
						if (it.tag is String)
						{
							setColorOption(it.tag as String, it.id)
						}
						mPlacement[it.id]?.let { placement -> bubbleHandle?.placement = placement }
					} else {}
				}
				else -> {}
			}
		}
	}

	override fun showAlert(message: String, intRes: Int)
	{
		hideDialog()
		animationAlert.setText(message)
		animationAlert.setResource(intRes)
		animationAlert.showAlert()

		Looper.getMainLooper()?.let {
			android.os.Handler(it).postDelayed({
				animationAlert.hideAlert()
			}, 1500)
		}
	}

//	override fun showError(errorCode: String, errorService: String)
//	{
//		parentActivity?.let {
//			val messageData =
//"""
//Project Id:
//->$projectId<-
//User Id:
//->$userID<-
//apiKey:
//->$apiKey<-
//domainUrl:
//->$domainUrl<-
//username:
//->$username<-
//password:
//->$password<-
//"""
//			val alert = AlertDialog.Builder(it)
//				.setCancelable(false)
////				.setMessage("Error: code $errorCode on service \"$errorService\"")
//				.setMessage(messageData)
//				.setNeutralButton("Error", null)
//				.show()
//
//			alert.findViewById<TextView>(android.R.id.message)?.typeface = Typeface.MONOSPACE
//		}
//	}

	override fun callJWt()
	{
		servicePresenter.createJWT()
	}

	override fun callRelated()
	{
		servicePresenter.callRelated()
	}

	override fun callTopics()
	{
		servicePresenter.callTopics()
	}

	//TODO call method
	override fun initPollService()
	{
//		activity?.let {
//			val intent = Intent(it, PollService::class.java)
//			PollService.enqueueWork(it, intent)
//		}
	}

	override fun changeAuthenticate(isAuthenticate: Boolean)
	{
		this.isAuthenticate = isAuthenticate
	}

	override fun isEnableLogin(isEnable: Boolean)
	{
		btnAuthenticate?.isEnabled = isEnable
	}

	override fun changeStateAuthenticate()
	{
		val text = if (isAuthenticate)
		{
			if (projectId.contains("spira"))
			{
				bubbleHandle?.setImageResource(R.drawable.ic_spira_logo)
				bubbleHandle?.setBackgroundColor(R.color.white)
			}
			else
			{
				bubbleHandle?.setImageResource(R.drawable.ic_bubble_chata)
				bubbleHandle?.setBackgroundColor(R.color.blue_chata_circle)
			}
			"Log Out"
		}
		else
		{
			isEnableLogin(true)
			"Authenticate"
		}

		activity?.run {
			if (this is PagerActivity)
			{
				this.isVisibleTabLayout = isAuthenticate
			}
		}

		btnAuthenticate?.text = text
	}

	override fun savePersistentData()
	{
		//region save preferences
		activity?.let {
			val sharePreferences = it.getPreferences(Context.MODE_PRIVATE) ?: return
			with(sharePreferences.edit())
			{
				putString("PROJECT_ID", projectId)
				putString("API_KEY", apiKey)
				putString("DOMAIN_URL", domainUrl)
				apply()
			}
		}
		//endregion
	}

	private fun showDialog()
	{
		BubbleHandle.isOpenChat = true
		activity?.let { ProgressWait.showProgressDialog(it, "") }
	}

	private fun hideDialog()
	{
		ProgressWait.hideProgressDialog()
		BubbleHandle.isOpenChat = false
	}

	private fun String.prepareDomain(): String
	{
		var tmp = this.trim()
		tmp.lastOrNull()?.let {
			if (it == '/')
			{
				tmp = tmp.removeSuffix("/")
			}
		}
		return tmp
	}

	private fun setColorOption(optionPath: String, idSelected: Int)
	{
		mViews[optionPath]?.let {
			for (index in 0 until it.size())
			{
				val key = it.keyAt(index)
				var isEnabled = it[key]
				if (isEnabled)
				{
					if (key != idSelected)
					{
						isEnabled = false
						it.put(key, isEnabled)
					}
				}
				else
				{
					if (key == idSelected)
					{
						isEnabled = true
						it.put(key, isEnabled)
					}
				}

				llContainer.findViewById<TextView>(key)?.let {
					tv ->
					if (isEnabled)
					{
						parentActivity?.let { activity ->
							tv.setBackgroundColor(activity.getParsedColor(R.color.blue))
						}
						tv.setTextColor(Color.WHITE)
					}
					else
					{
						tv.setBackgroundColor(Color.WHITE)
						tv.setTextColor(Color.BLACK)
					}
				}
			}
		}
	}
}
