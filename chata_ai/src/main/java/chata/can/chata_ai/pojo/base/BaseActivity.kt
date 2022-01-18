package chata.can.chata_ai.pojo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		adjustFontScale()
		onCreatedView()
	}

	private fun adjustFontScale()
	{
		val configuration = resources.configuration
		if (configuration.fontScale > 1.3f)
		{
			configuration.fontScale = 1.3f
			baseContext?.run {
				resources?.displayMetrics?.let {
					val metrics = it
					metrics.scaledDensity = configuration.fontScale * it.density
					createConfigurationContext(configuration)
					resources.displayMetrics.setTo(metrics)
				}
			}
		}
	}

	abstract fun onCreatedView()
}