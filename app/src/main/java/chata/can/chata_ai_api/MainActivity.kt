package chata.can.chata_ai_api

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.SparseBooleanArray
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import chata.can.chata_ai.extension.setOnTextChanged
import chata.can.chata_ai.pojo.DataMessenger
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.request.authentication.Authentication
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
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
	private var btnLogOut: TextView ?= null
	private var swDrawerHandle: Switch ?= null
	private val mViews = linkedMapOf<String, SparseBooleanArray>()

	private lateinit var btnReloadDrawer: Button
	private lateinit var btnOpenDrawer: Button

	private lateinit var bubbleHandle: BubbleHandle

	private val mTheme = hashMapOf(
	R.id.tvLight to BubbleHandle.THEME_LIGHT,
	R.id.tvDark to BubbleHandle.THEME_DARK)

	private val mPlacement = hashMapOf(
		R.id.tvTop to BubbleHandle.TOP_PLACEMENT,
		R.id.tvBottom to BubbleHandle.BOTTOM_PLACEMENT,
		R.id.tvLeft to BubbleHandle.LEFT_PLACEMENT,
		R.id.tvRight to BubbleHandle.RIGHT_PLACEMENT)

	private val overlayPermission = 1000

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initViews()
		setColorOptions()
		setListener()

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
				isEnableDrawer(true)
				initBubble()
			}
		}
		else
		{
			isEnableDrawer(true)
			initBubble()
		}

		//RequestBuilder.executeRequest(ConstantRequest.Method.GET, "https://backend.chata.ai/api/v1/autocomplete?q=co&projectid=1&user_id=demo&customer_id=demo")
		//RequestBuilder.executeRequest(ConstantRequest.Method.POST, "https://backend.chata.ai/oauth/token", params = params)

		if (BuildConfig.DEBUG)
		{
			tvProjectId?.setText("qbo-1")
			tvDomainUrl?.setText("https://qbo-staging.chata.io")
			tvApiKey?.setText("AIzaSyD2J8pfYPSI8b--HfxliLYB8V5AehPv0ys")
			tvUserId?.setText("carlos@rinro.com.mx")
			tvUsername?.setText("admin")
			tvPassword?.setText("admin123")
		}

		RequestBuilder.initVolleyRequest(this)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
	{
		super.onActivityResult(requestCode, resultCode, data)
		if (isMarshmallow())
		{
			if (canDrawOverlays())
			{
				isEnableDrawer(true)
				initBubble()
			}
			else
			{
				isEnableDrawer(false)
				showToast("canDrawOverlays is not enable")
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
					createAuthenticate()
				}
				R.id.btnLogOut ->
				{
					//DELETE DATA
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
							val theme = if (config)
								ThemeColor.lightColor
							else ThemeColor.darkColor

							bubbleHandle.changeColor(theme)
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
						mPlacement[it.id]?.let { placement -> bubbleHandle.setPlacement(placement) }
					}
				}
			}
		}
	}

	private fun createAuthenticate()
	{
		val username = (tvUsername?.text ?: "").toString()
		val password = (tvPassword?.text ?: "").toString()

		Authentication.callLogin(username, password,
			object: StatusResponse
			{
				override fun onFailure(jsonObject: JSONObject?)
				{
					if (jsonObject != null) { }
				}

				override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
				{
					if (jsonObject != null)
					{
						val token = jsonObject.optString("RESPONSE")
						DataMessenger.token = token
						createJWT()
					}

					if (jsonArray != null)  { }
				}
			})
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
					if (jsonObject != null) { }
				}

				override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
				{
					if (jsonObject != null)
					{
						val jwt = jsonObject.optString("RESPONSE")
						DataMessenger.JWT = jwt
					}

					if (jsonArray != null)  { }
				}
			})
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
						tv.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.blue))
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
						tv.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.blue))
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
				if (demoParam.type != TypeParameter.BUTTON)
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
						Switch(this).apply {
							layoutParams = LinearLayout.LayoutParams(-2, -2)
							gravity = Gravity.CENTER_HORIZONTAL
							isChecked = demoParam.value == "true"
							id = demoParam.idView
						}
					}
					TypeParameter.INPUT ->
					{
						EditText(this).apply {
							background = GradientDrawable().apply {
								shape = GradientDrawable.RECTANGLE
								setColor(ContextCompat.getColor(this@MainActivity, R.color.white))
								cornerRadius = 15f
								setStroke(3, (ContextCompat.getColor(this@MainActivity, R.color.borderEditText)))
							}
							layoutParams = LinearLayout.LayoutParams(-1, -2)
							(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
							gravity = Gravity.CENTER_HORIZONTAL
							id = demoParam.idView
							if (demoParam.value != "true" && demoParam.value != "false")
							{
								setText(demoParam.value)
							}
						}
					}
					TypeParameter.BUTTON ->
					{
						TextView(this).apply {
							setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorButton))
							layoutParams = LinearLayout.LayoutParams(-1, 90)
							(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
							gravity = Gravity.CENTER
							setTextColor(ContextCompat.getColor(this@MainActivity, R.color.textButton))
							id = demoParam.idView
							if (id != 0)
							{
								setOnClickListener(this@MainActivity)
							}
							text = demoParam.label
						}
					}
					TypeParameter.SEGMENT ->
					{
						val subView = LinearLayout(this)
						val sizeOptions = demoParam.options.size
						if (sizeOptions > 0)
						{
							with(subView)
							{
								layoutParams = LinearLayout.LayoutParams(-1, -2, sizeOptions.toFloat())
								(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
								orientation = LinearLayout.HORIZONTAL

								for (iterator in 0 until sizeOptions)
								{
									//val idView = demoParam.options.keyAt(iterator)
									val option = demoParam.options[iterator]
									val tv = TextView(this@MainActivity)
									tv.id = option.idView
									tv.setOnClickListener(this@MainActivity)
									tv.layoutParams = LinearLayout.LayoutParams(0, 90).apply {
										weight = 1f
									}
									tv.gravity = Gravity.CENTER
									tv.text = option.text
									tv.tag = demoParam.label

									mViews[demoParam.label]?.put(tv.id, option.isActive) ?: run {
										val newSparse = SparseBooleanArray()
										newSparse.put(tv.id, option.isActive)
										mViews.put(demoParam.label, newSparse)
									}
									this.addView(tv)
								}
							}
						}
						subView
					}
					TypeParameter.COLOR ->
					{
						TextView(this).apply {
							try
							{
								setBackgroundColor(Color.parseColor(demoParam.value))
							}
							finally
							{
								layoutParams = LinearLayout.LayoutParams(-1, 90)
								(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
								gravity = Gravity.CENTER
								setTextColor(Color.WHITE)
								text = demoParam.value
							}
						}
					}
				}

				llContainer.addView(viewChild)
			}
		}

		/*btnReloadDrawer = findViewById(R.id.btnReloadDrawer)
		btnOpenDrawer = findViewById(R.id.btnOpenDrawer)
		tvTop = findViewById(R.id.tvTop)
		tvBottom = findViewById(R.id.tvBottom)
		tvLeft = findViewById(R.id.tvLeft)
		tvRight = findViewById(R.id.tvRight)
		aPlacement = arrayListOf(tvTop, tvBottom, tvLeft)
		currentPlacement = tvRight*/
	}

	/**
	 *
	 */
	private fun initBubble()
	{
		bubbleHandle = BubbleHandle(this)
		initDataBubble()
	}

	private fun initDataBubble()
	{
		findViewById<EditText>(R.id.tvCustomerMessage)?.let {
			bubbleHandle.setCustomerName(it.text.toString())
		}
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
				btnLogOut?.visibility = iVisible
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
		btnLogOut = findViewById<TextView>(R.id.btnLogOut)?.apply {
			setOnClickListener(this@MainActivity)
		}
		swDrawerHandle = findViewById<Switch>(R.id.swDrawerHandle)?.apply {
			setOnCheckedChangeListener {
				_, isChecked ->
				bubbleHandle.setVisible(isChecked)
			}
		}
		findViewById<EditText>(R.id.etCurrencyCode)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					bubbleHandle.setCurrencyCode(it)
				}
			}
		}

		findViewById<EditText>(R.id.etFormatMonthYear)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					bubbleHandle.setFormatMonthYear(it)
				}
			}
		}

		findViewById<EditText>(R.id.etFormatDayMonthYear)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					bubbleHandle.setFormatDayMonthYear(it)
				}
			}
		}

		findViewById<EditText>(R.id.etLanguageCode)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					bubbleHandle.setLanguageCode(it)
				}
			}
		}

		findViewById<EditText>(R.id.etDecimalsCurrency)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					it.toIntOrNull()?.let {
						integer ->
						bubbleHandle.setDecimalsCurrency(integer)
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
						bubbleHandle.setDecimalsQuantity(integer)
					}
				}
			}
		}

		findViewById<EditText>(R.id.etIntroMessage)?.apply {
			setOnTextChanged {
				if (it.isNotEmpty())
				{
					bubbleHandle.setIntroMessage(it)
				}
			}
		}
	}

	/**
	 *
	 */
	private fun isEnableDrawer(isEnable: Boolean)
	{
		//btnOpenDrawer.isEnabled = isEnable
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
	private fun showToast(message: String)
	{
		Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
	}
}
