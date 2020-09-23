package chata.can.chata_ai_api.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai_api.R

class TestActivity: AppCompatActivity()
{
	private val receiver = object: BroadcastReceiver()
	{
		override fun onReceive(context: Context?, intent: Intent?)
		{
			intent?.extras?.let { bundle ->
				val data = bundle.getString(PollService.DATA)
				Toast.makeText(
					this@TestActivity, "Response data: $data",
					Toast.LENGTH_LONG).show()
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)

		val btn = findViewById<Button>(R.id.btn)
		RequestBuilder.initVolleyRequest(this)

		btn.setOnClickListener {
//			val intent = Intent(this, DownloadService::class.java)
//			intent.putExtra(DownloadService.FILENAME, "index.html")
//			intent.putExtra(DownloadService.URL, "https://www.vogella.com/index.html")
//			startService(intent)
			val intent = Intent(this, PollService::class.java)
			PollService.enqueueWork(this, intent)
		}
	}

	override fun onResume()
	{
		super.onResume()
		registerReceiver(receiver, IntentFilter(PollService.NOTIFICATION))
	}

	override fun onPause()
	{
		super.onPause()
		unregisterReceiver(receiver)
	}
}