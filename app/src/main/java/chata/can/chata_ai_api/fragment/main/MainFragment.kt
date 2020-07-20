package chata.can.chata_ai_api.fragment.main

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import chata.can.chata_ai.extension.isColor
import chata.can.chata_ai.extension.setOnTextChanged
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.view.bubbleHandle.Authentication
import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.apiKey
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.domainUrl
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.password
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.projectId
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.userID
import chata.can.chata_ai.view.bubbleHandle.DataMessenger.username
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai_api.*
import chata.can.chata_ai_api.main.PagerActivity

class MainFragment: BaseFragment(), View.OnClickListener, MainContract
{
	companion object {
		const val nameFragment = "Data Messenger"
		fun newInstance() = MainFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_main)
		}
	}

	private lateinit var llContainer: LinearLayout
	private var swDemoData: Switch ?= null
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
	private var swDrawerHandle: Switch ?= null
	private var etCurrencyCode: EditText ?= null
	private var etFormatMonthYear: EditText ?= null
	private var etFormatDayMonthYear: EditText ?= null
	private var etLanguageCode: EditText ?= null
	private var etDecimalsCurrency: EditText ?= null
	private var etDecimalsQuantity: EditText ?= null
	private var etCustomerMessage: EditText ?= null
	private var etIntroMessage: EditText ?= null
	private var etQueryPlaceholder: EditText ?= null
	private var swClearMessage: Switch ?= null
	private var etTitle: EditText ?= null
	private var etAddColor: EditText ?= null
	private var etLightThemeColor: EditText ?= null
	private var etDarkThemeColor: EditText ?= null
	private var etMaxNumberMessage: EditText ?= null
	private var swEnableAutocomplete: Switch ?= null
	private var swEnableQuery: Switch ?= null
	private var swEnableSuggestion: Switch ?= null
	private var swEnableDrillDown: Switch ?= null
	private var swEnableSpeechText: Switch ?= null
	//import module https://developer.android.com/studio/projects/android-library
	private lateinit var bubbleHandle: BubbleHandle

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
		activity?.let {
			val sharePreferences = it.getPreferences(Context.MODE_PRIVATE) ?: return
			with(sharePreferences)
			{
				val projectId = getString("PROJECT_ID", "") ?: ""
				val apiKey = getString("API_KEY", "") ?: ""
				val domainUrl = getString("DOMAIN_URL", "") ?: ""
				tvProjectId?.setText(projectId)
				tvApiKey?.setText(apiKey)
				tvDomainUrl?.setText(domainUrl)
			}
		}

		if (true)
		//if (BuildConfig.DEBUG)
		{
			val projectId = "spira-demo3"
//			val projectId = "accounting-demo"
			tvProjectId?.setText(projectId)
			val domainUrl = "https://spira-staging.chata.io"
//			val domainUrl = "https://accounting-demo-staging.chata.io"
			tvDomainUrl?.setText(domainUrl)
			val apiKey = "AIzaSyD4ewBvQdgdYfXl3yIzXbVaSyWGOcRFVeU"
//			val apiKey = "AIzaSyDX28JVW248PmBwN8_xRROWvO0a2BWH67o"
			tvApiKey?.setText(apiKey)
			val userId = "vidhyak464@gmail.com"
			tvUserId?.setText(userId)
			val username = "admin"
			tvUsername?.setText(username)
			val password = "admin123"
			tvPassword?.setText(password)
			tvPassword?.setSelection(password.length)

//			val customerMessage = "Carlos"
			val customerMessage = ""
			etCustomerMessage?.setText(customerMessage)
			val title = nameFragment
			etTitle?.setText(title)
			val maxMessage = 10
			etMaxNumberMessage?.setText("$maxMessage")
		}
		else
		{
			etCustomerMessage?.setText(etCustomerMessage?.text ?: "")
			etTitle?.setText(etTitle?.text ?: "")
			etIntroMessage?.setText(etIntroMessage?.text ?: "")
			etQueryPlaceholder?.setText(etQueryPlaceholder?.text ?: "")
			etMaxNumberMessage?.setText(etMaxNumberMessage?.text ?: "")
		}

		//region autologin REMOVE
		projectId = (tvProjectId?.text ?: "").toString().trim()
		userID = (tvUserId?.text ?: "").toString().trim()
		apiKey = (tvApiKey?.text ?: "").toString().trim()
		domainUrl = (tvDomainUrl?.text ?: "").toString().trim()
		username = (tvUsername?.text ?: "").toString().trim()
		password = (tvPassword?.text ?: "").toString().trim()

		servicePresenter.createAuthenticate()
		//endregion
	}

	override fun initViews(view: View)
	{
		with(view)
		{
			llContainer = findViewById(R.id.llContainer)
			parentActivity?.let {
				context ->
				val authentication = Authentication("", "", "")
				bubbleHandle = BubbleHandle(context, authentication)
				renderPresenter = MainRenderPresenter(context, this@MainFragment, bubbleHandle)
				servicePresenter = MainServicePresenter(this@MainFragment)
			}
			renderPresenter.initViews(llContainer)

			swDemoData = findViewById(R.id.swDemoData)
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
			findViewById<LinearLayout>(R.id.llColors)?.let {
				llColors ->
				for (index in 0 until llColors.childCount)
				{
					val child = llColors.getChildAt(index)
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
									bubbleHandle.changeColor(child.tag?.toString()?.toInt() ?: 0, pData.first)
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
							tv.setBackgroundColor(
								ContextCompat.getColor(activity,
									R.color.blue
								))
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

		swDemoData?.setOnCheckedChangeListener {
			_, isChecked ->
			val iVisible = if (!isChecked) View.VISIBLE else View.GONE

			hProjectId?.visibility = iVisible
			tvProjectId?.visibility = iVisible
			hUserId?.visibility = iVisible
			tvUserId?.visibility = iVisible
			hApiKey?.visibility = iVisible
			tvApiKey?.visibility = iVisible
			hDomainUrl?.visibility = iVisible
			tvDomainUrl?.visibility = iVisible
			hUsername?.visibility = iVisible
			tvUsername?.visibility = iVisible
			hPassword?.visibility = iVisible
			tvPassword?.visibility = iVisible
			btnAuthenticate?.visibility = iVisible
			bubbleHandle.isNecessaryLogin = !isChecked
		}

		btnAuthenticate?.setOnClickListener(this)
		btnReloadDrawer?.setOnClickListener(this)
		btnOpenDrawer?.setOnClickListener(this)

		swDrawerHandle?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle.isVisible = isChecked
		}

		etCurrencyCode?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				try
				{
					bubbleHandle.dataFormatting.currencyCode = it
				}
				catch (e: Exception) {}
			}
		}

		etFormatMonthYear?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				bubbleHandle.dataFormatting.monthYearFormat = it
			}
		}

		etFormatDayMonthYear?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				bubbleHandle.dataFormatting.dayMonthYearFormat = it
			}
		}

		etLanguageCode?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				bubbleHandle.dataFormatting.languageCode = it
			}
		}

		etDecimalsCurrency?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				it.toIntOrNull()?.let {
						integer ->
					bubbleHandle.dataFormatting.currencyDecimals = integer
				}
			}
		}

		etDecimalsQuantity?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				it.toIntOrNull()?.let {
						integer ->
					bubbleHandle.dataFormatting.quantityDecimals = integer
				}
			}
		}

		etCustomerMessage?.setOnTextChanged {
			bubbleHandle.userDisplayName = it
		}

		etIntroMessage?.setOnTextChanged {
			bubbleHandle.introMessage = it
		}

		etQueryPlaceholder?.setOnTextChanged {
			bubbleHandle.inputPlaceholder = it
		}

		swClearMessage?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle.clearOnClose = isChecked
		}

		etTitle?.setOnTextChanged {
			bubbleHandle.title = it
		}

		etAddColor?.setOnEditorActionListener {
			textView, _, _ ->
			val text = (textView.text ?: "").toString()
			bubbleHandle.addChartColor(text)
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
					bubbleHandle.setLightThemeColor(it)
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
					bubbleHandle.setDarkThemeColor(it)
				}
			}
			catch (ex: Exception) {}
		}

		etMaxNumberMessage?.setOnTextChanged {
			it.toIntOrNull()?.let {
				integer ->
				bubbleHandle.maxMessages = integer
			}
		}

		swEnableAutocomplete?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle.autoQLConfig.enableAutocomplete = isChecked
		}

		swEnableQuery?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle.autoQLConfig.enableQueryValidation = isChecked
		}

		swEnableSuggestion?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle.autoQLConfig.enableQuerySuggestions = isChecked
		}

		swEnableDrillDown?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle.autoQLConfig.enableDrilldowns = isChecked
		}

		swEnableSpeechText?.setOnCheckedChangeListener {
			_, isChecked ->
			bubbleHandle.enableVoiceRecord = isChecked
		}
	}

	override fun onResume()
	{
		super.onResume()
		if (::bubbleHandle.isInitialized && !bubbleHandle.isVisible)
		{
			bubbleHandle.isVisible = true
		}
	}

	override fun onPause()
	{
		super.onPause()
		if (::bubbleHandle.isInitialized)
		{
			bubbleHandle.isVisible = false
		}
	}

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
						changeStateAuthenticate()
					}
					else
					{
						projectId = (tvProjectId?.text ?: "").toString().trim()
						userID = (tvUserId?.text ?: "").toString().trim()
						apiKey = (tvApiKey?.text ?: "").toString().trim()
						domainUrl = (tvDomainUrl?.text ?: "").toString().trim()
						username = (tvUsername?.text ?: "").toString().trim()
						password = (tvPassword?.text ?: "").toString().trim()

						servicePresenter.createAuthenticate()
					}
				}
				R.id.btnReloadDrawer ->
				{
					bubbleHandle.reloadData()
				}
				R.id.btnOpenDrawer ->
				{
					bubbleHandle.openChatActivity()
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
							bubbleHandle.theme = theme
						}
					}
				}
				R.id.tvTop, R.id.tvBottom, R.id.tvLeft, R.id.tvRight ->
				{
					if (it is TextView)
					{
						if (it.tag is String)
						{
							setColorOption(it.tag as String, it.id)
						}
						mPlacement[it.id]?.let { placement -> bubbleHandle.placement = placement }
					}
				}
			}
		}
	}

	override fun showError(errorCode: String, errorService: String)
	{
		parentActivity?.let {
			AlertDialog.Builder(it)
				.setCancelable(false)
				.setMessage("Error: code $errorCode on service \"$errorService\"")
				.setNeutralButton("Error", null)
				.show()
		}
	}

	override fun callJWt()
	{
		val userId = (tvUserId?.text ?: "").toString()
		val projectId = (tvProjectId?.text ?: "").toString()

		servicePresenter.createJWT(userId, projectId)
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
		val pair = if (isAuthenticate)
		{
			Pair("Log Out", "Login Successful")
		}
		else
		{
			isEnableLogin(true)
			Pair("Authenticate", "Sucessfully logged out")
		}

		activity?.run {
			if (this is PagerActivity)
			{
				this.isVisibleTabLayout = isAuthenticate
			}
		}

		btnAuthenticate?.text = pair.first
		parentActivity?.let {
			AlertDialog.Builder(it)
				.setCancelable(false)
				.setMessage(pair.second)
				.setNeutralButton("Ok", null)
				.show()
		}
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
							tv.setBackgroundColor(ContextCompat.getColor(activity, R.color.blue))
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
