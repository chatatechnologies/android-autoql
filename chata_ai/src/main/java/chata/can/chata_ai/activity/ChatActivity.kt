package chata.can.chata_ai.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import chata.can.chata_ai.R

class ChatActivity: AppCompatActivity()
{
	private lateinit var toolbar: Toolbar

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.chat_activity)

		initViews()
		initData()
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean
	{
		menuInflater.inflate(R.menu.chat_menu, menu)
		return true
	}

	override fun finish()
	{
		super.finish()
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_top)
	}

	private fun initViews()
	{
		toolbar = findViewById(R.id.toolbar)
	}

	private fun initData()
	{
		setSupportActionBar(toolbar)
	}
}