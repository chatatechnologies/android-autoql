package chata.can.chata_ai.pojo.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(private val intRes: Int): AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		adjustFontScale(resources.configuration)
		setContentView(intRes)
		onCreateView()
	}

	private fun adjustFontScale(configuration: Configuration)
	{
		if (configuration.fontScale > 1.3f)
		{
			configuration.fontScale = 1.3f
			val metrics = resources.displayMetrics
			val wm = getSystemService(WINDOW_SERVICE) as WindowManager
			wm.defaultDisplay.getMetrics(metrics)
			metrics.scaledDensity = configuration.fontScale * metrics.density
			baseContext.resources.updateConfiguration(configuration, metrics)
		}
	}

	abstract fun onCreateView()

	fun hideKeyboard()
	{
		currentFocus?.let {
			val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
			imm.hideSoftInputFromWindow(it.windowToken, 0)
		}
	}
}