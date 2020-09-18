package chata.can.chata_ai_api.test

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai_api.R

class TestActivity: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)

		val tw = findViewById<TypeWriter>(R.id.tv)
		val btn = findViewById<Button>(R.id.btn)
		btn.setOnClickListener {
			tw.text = ""
			tw.setCharacterDelay(150)
			tw.animateText("Word in test")
		}
	}
}