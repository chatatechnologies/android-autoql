package chata.can.chata_ai_api.test

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import chata.can.request_native.BaseRequest

class TestActivity: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		setContentView(TextView(this).apply {
			text = "Test Activity"
		})

		BaseRequest().getBaseUrl()
	}
}