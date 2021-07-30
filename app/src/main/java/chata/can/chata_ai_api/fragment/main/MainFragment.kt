package chata.can.chata_ai_api.fragment.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.model.DashboardAdmin
import chata.can.chata_ai.model.DataMessengerAdmin
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.putArgs
import chata.can.chata_ai.service.PollService
import chata.can.chata_ai.view.ProgressWait
import chata.can.chata_ai.view.SwitchDM
import chata.can.chata_ai.view.animationAlert.AnimationAlert
import chata.can.chata_ai.view.dm.AutoQL
import chata.can.chata_ai_api.*
import chata.can.chata_ai_api.main.PagerActivity
import java.util.*
import kotlin.collections.ArrayList

class MainFragment: BaseFragment(), View.OnClickListener, MainContract
{
	companion object {
		const val nameFragment = "Data Messenger"
		fun newInstance() = MainFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_main)
		}
	}

	private lateinit var floatingView: AutoQL
	private lateinit var llContainer: LinearLayout
	private lateinit var swQA: SwitchDM
	//private var swDemoData: Switch ?= null
	private var hProjectId: TextView ?= null
	private var etProjectId: EditText?= null
	private var hThemeColor: TextView?= null
	private var etThemeColor: EditText?= null
	private var hUserId: TextView ?= null
	private var etUserId: EditText ?= null
	private var hApiKey: TextView ?= null
	private var etApiKey: EditText ?= null
	private var hDomainUrl: TextView ?= null
	private var etDomainUrl: EditText ?= null
	private var hUsername: TextView ?= null
	private var etUsername: EditText ?= null
	private var hPassword: TextView ?= null
	private var etPassword: EditText ?= null
	private var btnAuthenticate: TextView ?= null

	private var aClearFocus = ArrayList<EditText>()

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
	private var etDashboardColor: EditText ?= null
	private var etLightThemeColor: EditText ?= null
	private var etDarkThemeColor: EditText ?= null
	private var etMaxNumberMessage: EditText ?= null
	private var swEnableAutocomplete: SwitchCompat ?= null
	private var swEnableQuery: SwitchCompat ?= null
	private var swEnableSuggestion: SwitchCompat ?= null
	private var swEnableDrillDown: SwitchCompat ?= null
	private var swEnableColumn: SwitchCompat ?= null
	private var swEnableNotification: SwitchCompat ?= null
	private var swBackgroundBehind: SwitchCompat ?= null
	private var swTabExploreQueries: SwitchCompat ?= null
	private var swTabNotification: SwitchCompat ?= null
	private var swEnableSpeechText: SwitchCompat ?= null
	private lateinit var animationAlert: AnimationAlert
	//import module https://developer.android.com/studio/projects/android-library
	private lateinit var servicePresenter: MainServicePresenter
	private val mViews = CustomViews.mViews

	private val mTheme = hashMapOf(
		R.id.tvLight to AutoQLData.THEME_LIGHT,
		R.id.tvDark to AutoQLData.THEME_DARK)

	private val mPlacement = hashMapOf(
		R.id.tvTop to ConstantDrawer.TOP_PLACEMENT,
		R.id.tvBottom to ConstantDrawer.BOTTOM_PLACEMENT,
		R.id.tvLeft to ConstantDrawer.LEFT_PLACEMENT,
		R.id.tvRight to ConstantDrawer.RIGHT_PLACEMENT)

	private var isAuthenticate = false

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		if (BuildConfig.DEBUG)
//		if (false)
		{
			val projectId = "spira-demo3"
//			val projectId = "accounting-demo"
			etProjectId?.setText(projectId)
			val apiKey = "AIzaSyBxmGxl9J9siXz--dS-oY3-5XRSFKt_eVo"
//			val apiKey = "AIzaSyA8EomrHDJxkTnc2euI3NOaGDnUGJLCj2c"
			etApiKey?.setText(apiKey)
			val domainUrl = "https://spira-staging.chata.io"
//			val domainUrl = "https://accounting-demo.chata.io"
			etDomainUrl?.setText(domainUrl)
			val userId = "carlos@rinro.com.mx"
			etUserId?.setText(userId)
			val username = "admin"
//			val username = "accountdemo"
			etUsername?.setText(username)
			val password = "admin123"
//			val password = "accountdemo123"
			etPassword?.setText(password)
			etPassword?.setSelection(password.length)

			val customerMessage = "Carlos"
			etCustomerMessage?.setText(customerMessage)
			val title = nameFragment
			etTitle?.setText(title)
			val maxMessage = 10
			etMaxNumberMessage?.setText("$maxMessage")
			AutoQLData.projectId = (etProjectId?.text ?: "").toString().trim()
			AutoQLData.userID = (etUserId?.text ?: "").toString().trim()
			AutoQLData.apiKey = (etApiKey?.text ?: "").toString().trim()
			AutoQLData.domainUrl = (etDomainUrl?.text ?: "").toString().prepareDomain()
			AutoQLData.username = (etUsername?.text ?: "").toString().trim()
			AutoQLData.password = (etPassword?.text ?: "").toString().trim()

			//servicePresenter.createAuthenticate()
		}
		else
		{
			etCustomerMessage?.setText((etCustomerMessage?.text ?: "").trim())
			etTitle?.setText((etTitle?.text ?: "").trim())
			etIntroMessage?.setText((etIntroMessage?.text ?: "").trim())
			etQueryPlaceholder?.setText((etQueryPlaceholder?.text ?: "").trim())
			etMaxNumberMessage?.setText((etMaxNumberMessage?.text ?: "").trim())
		}
		//region get languageCode
		Locale.getDefault().run {
			val languageCode = "$language-$country"
			etLanguageCode?.setText(languageCode)
		}
		//endregion
	}

	override fun initViews(view: View)
	{
		with(view)
		{
			activity?.run {
				if (this is PagerActivity)
				{
					floatingView = findViewById(R.id.floatingView)
				}
			}

			llContainer = findViewById(R.id.llContainer)
			parentActivity?.let {
//				if (bubbleHandle == null)
//				{
//					val dataMessenger = DataMessenger("#data-messenger",
//						authentication = Authentication(
//							token,
//							apiKey,
//							domainUrl
//						),
//						ConstantDrawer.RIGHT_PLACEMENT
//					)
//					bubbleHandle = BubbleHandle(context, dataMessenger) {
//						//region catch data
//						FirebaseCrashlytics.getInstance().run {
//							setCustomKey("isOpenChat", BubbleHandle.isOpenChat)
//							setCustomKey("domain_ulr", domainUrl)
//							setCustomKey("api_key", apiKey)
//							setCustomKey("project_id", projectId)
//							setCustomKey("user_id", userID)
//						}
//						//endregion
//						hideKeyboard()
//						bubbleHandle?.let { bubbleHandle ->
//							val bubbleData = BubbleData(
//								bubbleHandle.userDisplayName,
//								bubbleHandle.title,
//								bubbleHandle.introMessage,
//								bubbleHandle.inputPlaceholder,
//								bubbleHandle.maxMessages,
//								bubbleHandle.clearOnClose,
//								bubbleHandle.isDarkenBackgroundBehind,
//								bubbleHandle.visibleExploreQueries,
//								bubbleHandle.visibleNotification,
//								bubbleHandle.enableVoiceRecord,
//								isDataMessenger)
//							(parentActivity as? PagerActivity)?.let {
//								for (clearView in aClearFocus)
//								{
//									clearView.clearFocus()
//								}
//								it.setStatusGUI(true, bubbleData)
//							}
//						}
//					}
//				}
				servicePresenter = MainServicePresenter(this@MainFragment)
			}
			MainRenderPresenter(context, this@MainFragment).run { initViews(llContainer) }
			animationAlert = AnimationAlert(findViewById(R.id.rlAlert))

			swQA = findViewById(R.id.swQA)
			hProjectId = findViewById(R.id.hProjectId)
			etProjectId = findViewById(R.id.etProjectId)
			hThemeColor = findViewById(R.id.hThemeColor)
			etThemeColor = findViewById(R.id.etThemeColor)
			hUserId = findViewById(R.id.hUserId)
			etUserId = findViewById(R.id.etUserId)
			hApiKey = findViewById(R.id.hApiKey)
			etApiKey = findViewById(R.id.etApiKey)
			hDomainUrl = findViewById(R.id.hDomainUrl)
			etDomainUrl = findViewById(R.id.etDomainUrl)
			hUsername = findViewById(R.id.hUsername)
			etUsername = findViewById(R.id.etUsername)
			hPassword = findViewById(R.id.hPassword)
			etPassword = findViewById(R.id.etPassword)
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
									pData.first.getContrast().run {
										child.setBackgroundColor(first)
										child.setTextColor(second)
									}
									DataMessengerAdmin.changeColor(child.tag?.toString()?.toInt() ?: 0, pData.first)
								}
							}
							catch (ex: Exception) {}
						}
					}
				}
			}
			//endregion
			etAddColor = findViewById(R.id.etAddColor)
			etDashboardColor = findViewById(R.id.etDashboardColor)
			etLightThemeColor = findViewById(R.id.etLightThemeColor)
			etDarkThemeColor = findViewById(R.id.etDarkThemeColor)
			etMaxNumberMessage = findViewById(R.id.etMaxNumberMessage)
			swEnableAutocomplete = findViewById(R.id.swEnableAutocomplete)
			swEnableQuery = findViewById(R.id.swEnableQuery)
			swEnableSuggestion = findViewById(R.id.swEnableSuggestion)
			swEnableDrillDown = findViewById(R.id.swEnableDrillDown)
			swEnableColumn = findViewById(R.id.swEnableColumn)
			swEnableNotification = findViewById(R.id.swEnableNotification)
			swBackgroundBehind = findViewById(R.id.swBackgroundBehind)
			swTabExploreQueries = findViewById(R.id.swTabExploreQueries)
			swTabNotification = findViewById(R.id.swTabNotification)
			swEnableSpeechText = findViewById(R.id.swEnableSpeechText)

			arrayListOf(etProjectId, etUserId, etApiKey, etDomainUrl, etUsername, etPassword).whenAllNotNull {
				aClearFocus.addAll(it)
			}
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
					llContainer.findViewById<TextView>(key)?.let { tv ->
						var parent: LinearLayout ?= null
						//region set background for view parent
						tv.parent?.let {
							(it as? LinearLayout)?.let { tmpParent ->
								if (tmpParent.tag == "child")
								{
									parent = tmpParent
								}
							}
						}
						//endregion
						val background = if (isEnabled)
						{
							tv.setTextColor(Color.WHITE)
							GradientDrawable().apply {
								shape = GradientDrawable.RECTANGLE
								setColor(activity.getParsedColor(R.color.colorButton))
							}
						}
						else
						{
							tv.setTextColor(Color.BLACK)
							GradientDrawable().apply {
								shape = GradientDrawable.RECTANGLE
								setColor(Color.WHITE)
								setStroke(1, Color.GRAY)
							}
						}

						parent?.let {
							it.getChildAt(0)?.let { iv ->
								(iv as? ImageView)?.let {
									iv.setColorFilter(if (isEnabled) Color.WHITE else Color.BLACK)
								}
							}
							it.background = background
						} ?: run {
							tv.background = background
						}
					}
				}
			}
		}
	}

	private val visible = View.VISIBLE
	val gone = View.GONE
	override fun initListener()
	{
		swQA.setCallbackEventChanged({
			hThemeColor?.visibility = gone
			etThemeColor?.visibility = gone
			hDomainUrl?.visibility = visible
			etDomainUrl?.visibility = visible
			hUsername?.visibility = visible
			etUsername?.visibility = visible
			hPassword?.visibility = visible
			etPassword?.visibility = visible
		},{
			hThemeColor?.visibility = visible
			etThemeColor?.visibility = visible
			hUserId?.visibility = gone
			etUserId?.visibility = gone
			hDomainUrl?.visibility = gone
			etDomainUrl?.visibility = gone
			hUsername?.visibility = gone
			etUsername?.visibility = gone
			hPassword?.visibility = gone
			etPassword?.visibility = gone
		})
		swQA.visibility = gone
		animationAlert.hideAlert()

		btnAuthenticate?.setOnClickListener(this)
		btnReloadDrawer?.setOnClickListener(this)
		btnOpenDrawer?.setOnClickListener(this)

		swDrawerHandle?.setOnCheckedChangeListener {
			_, isChecked ->
			if (::floatingView.isInitialized) {
				floatingView.visibility = if (isChecked) visible else gone
				AutoQLData.isVisible = isChecked
			}
		}

		etCurrencyCode?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				try
				{
					AutoQLData.dataFormatting.currencyCode = it
				}
				catch (e: Exception) {}
			}
		}

		etFormatMonthYear?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				AutoQLData.dataFormatting.monthYearFormat = it
			}
		}

		etFormatDayMonthYear?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				AutoQLData.dataFormatting.dayMonthYearFormat = it
			}
		}

		etLanguageCode?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				AutoQLData.dataFormatting.languageCode = it
			}
		}

		etDecimalsCurrency?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				it.toIntOrNull()?.let { integer ->
					AutoQLData.dataFormatting.currencyDecimals = integer
				}
			}
		}

		etDecimalsQuantity?.setOnTextChanged {
			if (it.isNotEmpty())
			{
				it.toIntOrNull()?.let { integer ->
					AutoQLData.dataFormatting.quantityDecimals = integer
				}
			}
		}

		etCustomerMessage?.setOnTextChanged {
			AutoQLData.customerName = it
		}

		etIntroMessage?.setOnTextChanged {
			AutoQLData.introMessage = it
		}

		etQueryPlaceholder?.setOnTextChanged {
			AutoQLData.inputPlaceholder = it
		}

		swClearMessage?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.clearOnClose = isChecked
		}

		etTitle?.setOnTextChanged {
			AutoQLData.title = it
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

				DataMessengerAdmin.addChartColor(newColor)
			}

			textView.text = ""
			false
		}

		etDashboardColor?.setOnTextChanged {
			try {
				val pData = it.isColor()
				if (pData.second)
				{
					pData.first.getContrast().run {
						etDashboardColor?.setBackgroundColor(first)
						etDashboardColor?.setTextColor(second)
					}
					DashboardAdmin.setDashboardColor(it)
				}
			}
			catch (ex: Exception) {}
		}

		etLightThemeColor?.setOnTextChanged {
			try
			{
				val pData = it.isColor()
				if (pData.second)
				{
					pData.first.getContrast().run {
						etLightThemeColor?.setBackgroundColor(first)
						etLightThemeColor?.setTextColor(second)
					}
					DataMessengerAdmin.setLightThemeColor(it)
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
					pData.first.getContrast().run {
						etDarkThemeColor?.setBackgroundColor(first)
						etDarkThemeColor?.setTextColor(second)
					}
					DataMessengerAdmin.setDarkThemeColor(it)
				}
			}
			catch (ex: Exception) {}
		}

		etMaxNumberMessage?.setOnTextChanged {
			it.toIntOrNull()?.let {
				integer ->
				AutoQLData.maxMessages = integer
			}
		}

		swEnableAutocomplete?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.autoQLConfig.enableAutocomplete = isChecked
		}

		swEnableQuery?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.autoQLConfig.enableQueryValidation = isChecked
		}

		swEnableSuggestion?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.autoQLConfig.enableQuerySuggestions = isChecked
		}

		swEnableDrillDown?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.autoQLConfig.enableDrilldowns = isChecked
		}

		swEnableColumn?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.isColumnVisibility = isChecked
		}

		swEnableNotification?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.visibleNotification = isChecked
			AutoQLData.activeNotifications = isChecked
		}

		swBackgroundBehind?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.isDarkenBackgroundBehind = isChecked
		}

		swTabExploreQueries?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.visibleExploreQueries = isChecked
		}

		swTabNotification?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.visibleNotification = isChecked
		}

		swEnableSpeechText?.setOnCheckedChangeListener {
			_, isChecked ->
			AutoQLData.enableVoiceRecord = isChecked
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
						AutoQLData.clearData()
						isAuthenticate = false
						changeStateAuthenticate()
						showAlert("Successfully logged out", R.drawable.ic_done)

						for(child in aClearFocus)
							child.setText("")
					}
					else
					{
						AutoQLData.clearData()
						AutoQLData.projectId = (etProjectId?.text ?: "").toString().trim()
						AutoQLData.userID = (etUserId?.text ?: "").toString().trim()
						AutoQLData.apiKey = (etApiKey?.text ?: "").toString().trim()
						AutoQLData.domainUrl = (etDomainUrl?.text ?: "").toString().prepareDomain()
						AutoQLData.username = (etUsername?.text ?: "").toString().trim()
						AutoQLData.password = (etPassword?.text ?: "").toString().trim()

						servicePresenter.createAuthenticate()
						showDialog()
					}
				}
				R.id.btnReloadDrawer ->
				{
					activity?.run {
						SinglentonDrawer.mModel.clear()
//						if (this is PagerActivity)
//						{
//							bubbleHandle?.let { bubbleHandle ->
//								val bubbleData = BubbleData(
//									bubbleHandle.userDisplayName,
//									bubbleHandle.title,
//									bubbleHandle.introMessage,
//									bubbleHandle.inputPlaceholder,
//									bubbleHandle.maxMessages,
//									bubbleHandle.clearOnClose,
//									bubbleHandle.isDarkenBackgroundBehind,
//									bubbleHandle.visibleExploreQueries,
//									bubbleHandle.visibleNotification,
//									bubbleHandle.enableVoiceRecord,
//									isDataMessenger)
//								clearDataMessenger(bubbleData)
//							}
//						}
					}
				}
				R.id.btnOpenDrawer ->
				{
					floatingView.runEvent()
				}
				R.id.tvLight, R.id.tvDark ->
				{
					mTheme[it.id]?.let { config ->
						val theme = if (config) "light" else "dark"
						floatingView.theme = theme
					}
				}
				R.id.tvTop, R.id.tvBottom, R.id.tvLeft, R.id.tvRight ->
				{
					mPlacement[it.id]?.let { placement ->
						floatingView.placement = placement
					}
				}
				R.id.tvDataMessenger, R.id.tvExploreQueries ->
				{
					AutoQLData.isDataMessenger = it.id == R.id.tvDataMessenger
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
			Handler(it).postDelayed({
				animationAlert.hideAlert()
			}, 1500)
		}
	}

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

	private val mHandler = Handler(Looper.getMainLooper())
	private val runnable = object: Runnable {
		override fun run()
		{
			activity?.let {
				val intent = Intent(it, PollService::class.java)
				PollService.enqueueWork(it, intent)
			}
			mHandler.postDelayed(this, 10000)
		}
	}

	override fun initPollService()
	{
		mHandler.postDelayed(runnable, 10000)
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
				AutoQLData.isRelease = true
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
				putString("PROJECT_ID", AutoQLData.projectId)
				putString("API_KEY", AutoQLData.apiKey)
				putString("DOMAIN_URL", AutoQLData.domainUrl)
				apply()
			}
		}
		//endregion
	}

	private fun showDialog()
	{
		activity?.let { ProgressWait.showProgressDialog(it, "") }
	}

	private fun hideDialog()
	{
		ProgressWait.hideProgressDialog()
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
}
