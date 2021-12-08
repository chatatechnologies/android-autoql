package chata.can.chata_ai.pojo.base

import android.content.res.Configuration
import android.os.Bundle
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

	abstract fun onCreateView()
}