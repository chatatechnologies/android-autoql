package chata.can.chata_ai_api.test

import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai_api.R

class TestActivity: AppCompatActivity()
{
	private lateinit var ivChat: ImageView
	private lateinit var ivTips: ImageView
	private lateinit var ivNotify: ImageView

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)

		ivChat = findViewById(R.id.ivChat)
		ivTips = findViewById(R.id.ivTips)
		ivNotify = findViewById(R.id.ivNotify)

		ivChat.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
		ivChat.paddingAll(left = 6f, right = 6f)
		(ivChat.layoutParams as? RelativeLayout.LayoutParams)?.run {
			height = dpToPx(56f)
			width = -1
		}
		ivTips.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
		ivTips.paddingAll(left = 6f, right = 6f)
		(ivTips.layoutParams as? RelativeLayout.LayoutParams)?.run {
			height = dpToPx(56f)
			width = -1
		}
		ivNotify.setBackgroundColor(getParsedColor(R.color.blue_chata_circle))
		ivNotify.paddingAll(left = 6f, right = 6f)
		(ivNotify.layoutParams as? RelativeLayout.LayoutParams)?.run {
			height = dpToPx(56f)
			width = -1
		}
	}
}