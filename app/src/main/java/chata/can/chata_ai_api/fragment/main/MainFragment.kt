package chata.can.chata_ai_api.fragment.main

import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.extension.isColor
import chata.can.chata_ai.extension.setOnTextChanged
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai_api.*

class MainFragment: BaseFragment(), View.OnClickListener
{
	companion object {
		//		const val nameClass = "DashboardFragment"
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
	private lateinit var bubbleHandle: BubbleHandle

	private lateinit var presenter: MainRenderPresenter



	override fun initViews(view: View)
	{
		with(view)
		{
			activity?.let {
				llContainer = findViewById(R.id.llContainer)
				presenter = MainRenderPresenter(it, this@MainFragment)
				presenter.initViews(llContainer)

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

		if (BuildConfig.DEBUG)
		{
			val projectId = "qbo-1"
			tvProjectId?.setText(projectId)
			val domainUrl = "https://qbo-staging.chata.io"
			tvDomainUrl?.setText(domainUrl)
			val apiKey = "AIzaSyD2J8pfYPSI8b--HfxliLYB8V5AehPv0ys"
			tvApiKey?.setText(apiKey)
			val userId = "carlos@rinro.com.mx"
			tvUserId?.setText(userId)
			val username = "admin"
			tvUsername?.setText(username)
			val password = "admin123"
			tvPassword?.setText(password)

			val customerMessage = "Carlos"
			etCustomerMessage?.setText(customerMessage)
			val title = "Data Messenger"
			etTitle?.setText(title)
			val maxMessage = 10
			etMaxNumberMessage?.setText("$maxMessage")
		}
	}

	override fun setColors()
	{
		activity?.let {
			activity ->
			val mViews = CustomViews.mViews
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

	override fun onClick(v: View?)
	{
	}
}