package chata.can.chata_ai_api.test

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TestActivity: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(TextView(this).apply {
			val text1 = "Test Activity"
			text = text1
		})

	}
}