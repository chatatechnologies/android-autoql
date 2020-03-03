package chata.can.chata_ai_api

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import chata.can.chata_ai.pojo.nullValue
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai.view.bubbles.BubbleLayout
import chata.can.chata_ai.view.bubbles.BubblesManager

/**
 * @author Carlos Buruel
 * @since 1.0
 */
class MainActivity: AppCompatActivity(), View.OnClickListener
{
	private lateinit var btnReloadDrawer: Button
	private lateinit var btnOpenDrawer: Button

	private lateinit var bubblesManager: BubblesManager

	private val overlayPermission = 1000

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initViews()
		initListener()

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
				R.id.btnOpenDrawer ->
				{
					addBubble()
				}
			}
		}
	}

	override fun onDestroy()
	{
		super.onDestroy()
		bubblesManager.recycle()
	}

	/**
	 *
	 */
	private fun initViews()
	{
		btnReloadDrawer = findViewById(R.id.btnReloadDrawer)
		btnOpenDrawer = findViewById(R.id.btnOpenDrawer)
	}

	/**
	 *
	 */
	private fun initListener()
	{
		btnOpenDrawer.setOnClickListener(this)
	}

	/**
	 *
	 */
	private fun initBubble()
	{
		BubblesManager.Builder(this)
			.setTrashLayout(R.layout.bubble_remove)
			.setInitializationCallback { addBubble() }
			.build()?.let {
				bubblesManager = it
			}
		if (::bubblesManager.isInitialized)
		{
			bubblesManager.initialize()
		}
	}

	/**
	 *
	 */
	private fun addBubble()
	{
		with(BubbleHandle(this))
		{
			setOnBubbleRemoveListener { showToast("Removed") }
			setOnBubbleClickListener { showToast("Clicked") }
			setShouldStickToWall(true)
			//bubblesManager.addBubble(this, 144,144)
		}

		(LayoutInflater.from(this).inflate(R.layout.bubble_layout, nullValue) as? BubbleLayout)?.let {
			it.setOnBubbleRemoveListener { showToast("Removed") }

			it.setOnBubbleClickListener { showToast("Clicked") }

			it.setShouldStickToWall(true)
			bubblesManager.addBubble(it, 144,144)
		}
	}

	/**
	 *
	 */
	private fun isEnableDrawer(isEnable: Boolean)
	{
		btnOpenDrawer.isEnabled = isEnable
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
