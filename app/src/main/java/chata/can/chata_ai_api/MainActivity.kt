package chata.can.chata_ai_api

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai_api.model.SectionData
import chata.can.chata_ai_api.model.TypeParameter

/**
 * @author Carlos Buruel
 * @since 1.0
 */
class MainActivity: AppCompatActivity(), View.OnClickListener
{
	private lateinit var llContainer: LinearLayout

	private lateinit var btnReloadDrawer: Button
	private lateinit var btnOpenDrawer: Button

	private lateinit var tvTop: TextView
	private lateinit var tvBottom: TextView
	private lateinit var tvLeft: TextView
	private lateinit var tvRight: TextView
	private lateinit var aPlacement: ArrayList<TextView>
	private var currentPlacement: TextView ?= null

	private lateinit var bubbleHandle: BubbleHandle

	/*private val mPlacement = hashMapOf(
		R.id.tvTop to BubbleHandle.TOP_PLACEMENT,
		R.id.tvBottom to BubbleHandle.BOTTOM_PLACEMENT,
		R.id.tvLeft to BubbleHandle.LEFT_PLACEMENT,
		R.id.tvRight to BubbleHandle.RIGHT_PLACEMENT)*/

	private val overlayPermission = 1000

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initViews()
		initListener()
		initColorPlacement()
		setColorPlacement(true)

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
				/*R.id.btnOpenDrawer ->
				{

				}
				R.id.tvTop, R.id.tvBottom, R.id.tvLeft, R.id.tvRight ->
				{
					if (it is TextView)
					{
						setColorPlacement(false)
						aPlacement.remove(currentPlacement)

						aPlacement.add(it)
						currentPlacement = it
						setColorPlacement(true)

						mPlacement[it.id]?.let { placement -> bubbleHandle.setPlacement(placement) }
					}
				}*/
			}
		}
	}

	/**
	 *
	 */
	private fun initColorPlacement()
	{
		/*for (view in aPlacement)
		{
			view.setBackgroundColor(Color.WHITE)
			view.setTextColor(Color.BLACK)
		}*/
	}

	private fun setColorPlacement(isSelected: Boolean)
	{
		currentPlacement?.let {
			with(it)
			{
				if (isSelected)
				{
					setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.blue))
					setTextColor(Color.WHITE)
				}
				else
				{
					setBackgroundColor(Color.WHITE)
					setTextColor(Color.BLACK)
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
				//region label
				with(TextView(this))
				{
					layoutParams = LinearLayout.LayoutParams(-1, -2)
					gravity = Gravity.CENTER_HORIZONTAL
					text = demoParam.label
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
					llContainer.addView(this)
				}
				//endregion
				when(demoParam.type)
				{
					TypeParameter.TOGGLE ->
					{

					}
					TypeParameter.INPUT ->
					{

					}
					TypeParameter.BUTTON ->
					{

					}
					TypeParameter.SEGMENT ->
					{

					}
					TypeParameter.COLOR ->
					{

					}
				}
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
	private fun initListener()
	{
		/*btnOpenDrawer.setOnClickListener(this)
		tvTop.setOnClickListener(this)
		tvBottom.setOnClickListener(this)
		tvLeft.setOnClickListener(this)
		tvRight.setOnClickListener(this)*/
	}

	/**
	 *
	 */
	private fun initBubble()
	{
		bubbleHandle = BubbleHandle(this)
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
