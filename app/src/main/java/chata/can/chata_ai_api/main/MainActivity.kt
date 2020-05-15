package chata.can.chata_ai_api.main

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.SparseBooleanArray
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import chata.can.chata_ai.extension.isColor
import chata.can.chata_ai.extension.setOnTextChanged
import chata.can.chata_ai.pojo.ConstantDrawer
import chata.can.chata_ai.pojo.DataMessenger
import chata.can.chata_ai.pojo.SinglentonDashboard
import chata.can.chata_ai.pojo.dashboard.Dashboard
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.request.dashboard.Dashboard as RequestDashboard
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai_api.BuildConfig
import chata.can.chata_ai_api.CustomViews
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.model.SectionData
import chata.can.chata_ai_api.model.TypeParameter
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author Carlos Buruel
 * @since 1.0
 */
class MainActivity: AppCompatActivity(), View.OnClickListener
{
	private lateinit var llContainer: LinearLayout
	private var swDemoData: Switch ?= null
	private var hProjectId: TextView ?= null
	private var tvProjectId: EditText ?= null
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
	private var etCustomerMessage: TextView ?= null
	private var swClearMessage: Switch ?= null
	private var etTitle: EditText ?= null
	private var etMaxNumberMessage: EditText ?= null

	private val mViews = linkedMapOf<String, SparseBooleanArray>()

	private lateinit var bubbleHandle: BubbleHandle

	private val mTheme = hashMapOf(
	R.id.tvLight to BubbleHandle.THEME_LIGHT,
	R.id.tvDark to BubbleHandle.THEME_DARK)

	private val mPlacement = hashMapOf(
		R.id.tvTop to ConstantDrawer.TOP_PLACEMENT,
		R.id.tvBottom to ConstantDrawer.BOTTOM_PLACEMENT,
		R.id.tvLeft to ConstantDrawer.LEFT_PLACEMENT,
		R.id.tvRight to ConstantDrawer.RIGHT_PLACEMENT)

	private val overlayPermission = 1000
	private var isAuthenticate = false

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		if (isMarshmallow())
		{
			if (!canDrawOverlays())
			{
				with(Intent(
					Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
					Uri.parse("package:$packageName")))
				{
					startActivityForResult(this, overlayPermission)
				}
			}
			else
			{
				initBubble()
			}
		}
		else
		{
			initBubble()
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

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
	{
		super.onActivityResult(requestCode, resultCode, data)
		if (isMarshmallow())
		{
			if (canDrawOverlays())
			{
				initBubble()
			}
			else
			{
				showToast()
			}
		}
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.btnAuthenticate ->
				{
					if (isAuthenticate)
					{
						DataMessenger.clearData()
						isAuthenticate = false
						changeStateAuthenticate()
					}
					else
					{
						createAuthenticate()
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

	private fun createAuthenticate()
	{
		with(DataMessenger)
		{
			projectId = (tvProjectId?.text ?: "").toString()
			userID = (tvUserId?.text ?: "").toString()
			apiKey = (tvApiKey?.text ?: "").toString()
			domainUrl = (tvDomainUrl?.text ?: "").toString()
			username = (tvUsername?.text ?: "").toString()
			password = (tvPassword?.text ?: "").toString()

			Authentication.callLogin(username, password,
				object: StatusResponse
				{
					override fun onFailure(jsonObject: JSONObject?)
					{
						//if (jsonObject != null) { }
					}

					override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
					{
						if (jsonObject != null)
						{
							val token = jsonObject.optString("RESPONSE")
							DataMessenger.token = token
							createJWT()
						}

						//if (jsonArray != null)  { }
					}
				})
		}
	}

	private fun createJWT()
	{
		val userId = (tvUserId?.text ?: "").toString()
		val projectId = (tvProjectId?.text ?: "").toString()

		Authentication.callJWL(
			DataMessenger.token,
			userId,
			projectId,
			object: StatusResponse
			{
				override fun onFailure(jsonObject: JSONObject?)
				{
					//if (jsonObject != null) { }
				}

				override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
				{
					if (jsonObject != null)
					{
						val jwt = jsonObject.optString("RESPONSE")
						DataMessenger.JWT = jwt
						isAuthenticate = true
						changeStateAuthenticate()
						getDashboards()
					}

					//if (jsonArray != null)  { }
				}
			})
	}

	private fun getDashboards()
	{
		RequestDashboard.getDashboard(object : StatusResponse
			{
				override fun onFailure(jsonObject: JSONObject?)
				{
					jsonObject.toString()
				}

				override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
				{
					if (jsonArray != null)
					{
						jsonArray.optJSONObject(0)?.let {
							firstJSON ->
							firstJSON.optJSONArray("data")?.let {
								jaData ->
								for (index in 0 until jaData.length())
								{
									jaData.optJSONObject(index)?.let {
										json ->
										with(json)
										{
											val displayType = optString("displayType", "")
											val h = optInt("h", -1)
											val i = optString("i", "")
											val isNewTile = optBoolean("isNewTile", false)
											val key = optString("key", "")
											val maxH = optInt("maxH", -1)
											val minH = optInt("minH", -1)
											val minW = optInt("minW", -1)
											val moved = optBoolean("moved", false)
											val query = optString("query", "")
											val splitView = optBoolean("splitView", false)
											val static = optBoolean("static", false)
											val title = optString("title", "")
											val w = optInt("w", -1)
											val x = optInt("x", -1)
											val y = optInt("y", -1)

											val dashboard = Dashboard(displayType, h, i, isNewTile, key, maxH, minH, minW, moved, query, splitView, static, title, w, x, y)
											SinglentonDashboard.mModel.add(dashboard)
										}
									}
								}
							}
						}
						SinglentonDashboard.mModel.toString()
					}
				}
			})
	}

	private fun changeStateAuthenticate()
	{
		val pair = if (isAuthenticate)
		{
			Pair("Log Out", "Login Successful")
		}
		else Pair("Authenticate", "Sucessfully logged out")

		btnAuthenticate?.text = pair.first
		AlertDialog.Builder(this)
			.setCancelable(false)
			.setMessage(pair.second)
			.setNeutralButton("Ok", null)
			.show()
	}

	private fun setColorOptions()
	{
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
						tv.setBackgroundColor(ContextCompat.getColor(this@MainActivity,
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
						tv.setBackgroundColor(ContextCompat.getColor(this@MainActivity,
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

	override fun onDestroy()
	{
		super.onDestroy()
		bubbleHandle.onDestroy()
	}

	/**
	 *
	 */
	private fun initViews()
	{
		llContainer = findViewById(R.id.llContainer)

		for ((header, demoParams) in SectionData.mData)
		{
			//region header
			with(TextView(this))
			{
				layoutParams = LinearLayout.LayoutParams(-1, -2)
				gravity = Gravity.CENTER_HORIZONTAL
				setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
				setTypeface(typeface, Typeface.BOLD)
				text = header
				llContainer.addView(this)
			}
			//endregion

			for (demoParam in demoParams)
			{
				if (demoParam.type != TypeParameter.BUTTON && demoParam.label.isNotEmpty())
				{
					//region label
					with(TextView(this))
					{
						layoutParams = LinearLayout.LayoutParams(-1, -2)
						gravity = Gravity.CENTER_HORIZONTAL
						text = demoParam.label
						if (demoParam.labelId != 0)
						{
							id = demoParam.labelId

						}
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
						llContainer.addView(this)
					}
					//endregion
				}

				val viewChild = when(demoParam.type)
				{
					TypeParameter.TOGGLE ->
					{
						CustomViews.getSwitch(this, demoParam.value, demoParam.idView)
					}
					TypeParameter.INPUT ->
					{
						CustomViews.getEditText(this, demoParam)
					}
					TypeParameter.BUTTON ->
					{
						CustomViews.getButton(this, demoParam, this)
					}
					TypeParameter.SEGMENT ->
					{
						CustomViews.getSegment(this, demoParam, this)
					}
					TypeParameter.COLOR ->
					{
						CustomViews.getColor(this, demoParam) {
							valueColor ->
							bubbleHandle.addChartColor(valueColor)
						}
					}
				}

				llContainer.addView(viewChild)
			}
		}
	}

	/**
	 *
	 */
	private fun initBubble()
	{
		bubbleHandle = BubbleHandle(this@MainActivity)

		initViews()
		setColorOptions()
		setListener()

		//RequestBuilder.executeRequest(ConstantRequest.Method.GET, "https://backend.chata.ai/api/v1/autocomplete?q=co&projectid=1&user_id=demo&customer_id=demo")
		//RequestBuilder.executeRequest(ConstantRequest.Method.POST, "https://backend.chata.ai/oauth/token", params = params)

		if (BuildConfig.DEBUG)
		{
			val projectId = "qbo-1"
			tvProjectId?.setText(projectId)
			val domainUrl = "https://qbo-staging.chata.io"
			tvDomainUrl?.setText(domainUrl)
			val apiKey = "AIzaSyD2J8pfYPSI8b--HfxliLYB8V5AehPv0ys"
			tvApiKey?.setText(apiKey)
			val userId = "vicente@rinro.com.mx"
			tvUserId?.setText(userId)
			val username = "admin"
			tvUsername?.setText(username)
			val password = "admin123"
			tvPassword?.setText(password)

			val customerMessage = "Carlos"
			etCustomerMessage?.text = customerMessage
			val title = "Data Messenger"
			etTitle?.setText(title)
			val maxMessage = 10
			etMaxNumberMessage?.setText("$maxMessage")
		}

		RequestBuilder.initVolleyRequest(this)
	}

	/**
	 *
	 */
	private fun setListener()
	{
		swDemoData = findViewById<Switch>(R.id.swDemoData)?.apply {
			setOnCheckedChangeListener {
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
		}
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

		btnAuthenticate = findViewById<TextView>(R.id.btnAuthenticate)?.apply {
			setOnClickListener(this@MainActivity)
		}
		btnReloadDrawer = findViewById<TextView>(R.id.btnReloadDrawer)?.apply {
			setOnClickListener(this@MainActivity)
		}
		btnOpenDrawer = findViewById<TextView>(R.id.btnOpenDrawer)?.apply {
			setOnClickListener(this@MainActivity)
		}
		swDrawerHandle = findViewById<Switch>(R.id.swDrawerHandle)?.apply {
			setOnCheckedChangeListener {
				_, isChecked ->
				bubbleHandle.isVisible = isChecked
			}
		}
		findViewById<EditText>(R.id.etCurrencyCode)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					try
					{
						bubbleHandle.dataFormatting.currencyCode = it
					}
					catch (e: Exception) {}
				}
			}
		}
		findViewById<EditText>(R.id.etFormatMonthYear)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					bubbleHandle.dataFormatting.monthYearFormat = it
				}
			}
		}
		findViewById<EditText>(R.id.etFormatDayMonthYear)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					bubbleHandle.dataFormatting.dayMonthYearFormat = it
				}
			}
		}
		findViewById<EditText>(R.id.etLanguageCode)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					bubbleHandle.dataFormatting.languageCode = it
				}
			}
		}
		findViewById<EditText>(R.id.etDecimalsCurrency)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					it.toIntOrNull()?.let {
						integer ->
						bubbleHandle.dataFormatting.currencyDecimals = integer
					}
				}
			}
		}
		findViewById<EditText>(R.id.etDecimalsQuantity)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					it.toIntOrNull()?.let {
						integer ->
						bubbleHandle.dataFormatting.quantityDecimals = integer
					}
				}
			}
		}
		etCustomerMessage = findViewById<EditText>(R.id.etCustomerMessage)?.apply {
			setOnTextChanged {
				bubbleHandle.userDisplayName = it
			}
		}
		findViewById<EditText>(R.id.etIntroMessage)?.apply {
			setOnTextChanged {
				bubbleHandle.introMessage = it
			}
		}
		findViewById<EditText>(R.id.etQueryPlaceholder)?.apply {
			setOnTextChanged {
				bubbleHandle.inputPlaceholder = it
			}
		}
		swClearMessage = findViewById<Switch>(R.id.swClearMessage)?.apply {
			setOnCheckedChangeListener {
				_, isChecked ->
				bubbleHandle.clearOnClose = isChecked
			}
		}

		etTitle = findViewById<EditText>(R.id.etTitle)?.apply {
			setOnTextChanged {
				bubbleHandle.title = it
			}
		}
		//region llColors
		findViewById<LinearLayout>(R.id.llColors)?.let {
			for (index in 0 until it.childCount)
			{
				val view = it.getChildAt(index)
				if (view is EditText)
				{
					view.setOnTextChanged {
						subColor ->
						try
						{
							val pData = subColor.isColor()
							if (pData.second)
							{
								view.setBackgroundColor(Color.parseColor(pData.first))
								bubbleHandle.changeColor(view.tag?.toString()?.toInt() ?: 0, pData.first)
							}
						}
						catch (ex: Exception) {}
					}
				}
			}
		}
		//endregion

		findViewById<EditText>(R.id.etAddColor)?.apply {
			setOnEditorActionListener {
				v, _, _ ->
				val text = (v.text ?: "").toString()
				bubbleHandle.addChartColor(text)
				v.text = ""
				false
			}
		}
		findViewById<EditText>(R.id.etLightThemeColor)?.apply {
			setOnTextChanged {
				try
				{
					val pData = it.isColor()
					if (pData.second)
					{
						this.setBackgroundColor(Color.parseColor(pData.first))
						bubbleHandle.setLightThemeColor(it)
					}
				}
				catch (ex: Exception) {}
			}
		}
		findViewById<EditText>(R.id.etDarkThemeColor)?.apply {
			setOnTextChanged {
				try
				{
					val pData = it.isColor()
					if (pData.second)
					{
						this.setBackgroundColor(Color.parseColor(pData.first))
						bubbleHandle.setDarkThemeColor(it)
					}
				}
				catch (ex: Exception) {}
			}
		}
		etMaxNumberMessage = findViewById<EditText>(R.id.etMaxNumberMessage)?.apply {
			setOnTextChanged {
				it.toIntOrNull()?.let {
					integer ->
					bubbleHandle.maxMessages = integer
				}
			}
		}
		findViewById<Switch>(R.id.swEnableAutocomplete)?.apply {
			setOnCheckedChangeListener {
				_, isChecked ->
				bubbleHandle.autoQLConfig.enableAutocomplete = isChecked
			}
		}
		findViewById<Switch>(R.id.swEnableQuery)?.apply {
			setOnCheckedChangeListener {
				_, isChecked ->
				bubbleHandle.autoQLConfig.enableQueryValidation = isChecked
			}
		}
		findViewById<Switch>(R.id.swEnableSuggestion)?.apply {
			setOnCheckedChangeListener {
				_, isChecked ->
				bubbleHandle.autoQLConfig.enableQuerySuggestions = isChecked
			}
		}
		findViewById<Switch>(R.id.swEnableDrillDown)?.apply {
			setOnCheckedChangeListener {
				_, isChecked ->
				bubbleHandle.autoQLConfig.enableDrilldowns = isChecked
			}
		}
		findViewById<Switch>(R.id.swEnableSpeechText)?.apply {
			setOnCheckedChangeListener {
				_, isChecked ->
				bubbleHandle.enableVoiceRecord = isChecked
			}
		}
	}

	/**
	 * Build.VERSION_CODES.M is 23
	 */
	private fun isMarshmallow() = Build.VERSION.SDK_INT >= 23

	/**
	 * Build.VERSION_CODES.M is 23
	 * M is for Marshmallow!
	 */
	@RequiresApi(api = Build.VERSION_CODES.M)
	private fun canDrawOverlays() = Settings.canDrawOverlays(this)

	/**
	 * remove in future
	 */
	private fun showToast()
	{
		Toast.makeText(
			this@MainActivity,
			"canDrawOverlays is not enable",
			Toast.LENGTH_SHORT).show()
	}
}