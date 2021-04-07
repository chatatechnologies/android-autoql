package chata.can.chata_ai_api.test

import android.widget.Toast
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai_api.R

class BubbleTest: BaseActivity(R.layout.activity_bubble)
{
	private lateinit var bubbleFrame: BubbleFrame
	override fun onCreateView()
	{
		bubbleFrame = findViewById(R.id.bubbleFrame)
		bubbleFrame.setEventClick {
			Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
		}
	}
}