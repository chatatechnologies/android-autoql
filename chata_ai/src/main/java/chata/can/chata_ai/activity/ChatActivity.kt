package chata.can.chata_ai.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.R

class ChatActivity: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.chat_activity)
	}

	override fun finish()
	{
		super.finish()
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_top)
	}
}