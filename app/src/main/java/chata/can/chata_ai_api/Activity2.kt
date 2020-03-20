package chata.can.chata_ai_api

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Activity2: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_2)

		findViewById<Button>(R.id.openActivity)?.let {
			it.setOnClickListener {
				startActivity(Intent(this, Activity3::class.java))
			}
		}
	}

	override fun finish()
	{
		super.finish()
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
	}
}