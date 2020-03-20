package chata.can.chata_ai.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.R
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle

class ChatActivity: AppCompatActivity(), View.OnClickListener
{
	private lateinit var ivCancel: ImageView
	private lateinit var ivDelete: ImageView

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.chat_activity)

		initViews()
		initListener()
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
		BubbleHandle.isOpenChat = false
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_top)
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.ivCancel ->
				{
					finish()
				}
				R.id.ivDelete ->
				{

				}
			}
		}
	}

	private fun initViews()
	{
		ivCancel = findViewById(R.id.ivCancel)
		ivDelete = findViewById(R.id.ivDelete)
	}

	private fun initListener()
	{
		ivCancel.setOnClickListener(this)
		ivDelete.setOnClickListener(this)
	}

	private fun initData()
	{

	}
}