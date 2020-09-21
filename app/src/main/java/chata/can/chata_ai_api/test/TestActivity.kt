package chata.can.chata_ai_api.test

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.view.typing.TypeAutoComplete
import chata.can.chata_ai_api.R

class TestActivity: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)

		val actv = findViewById<TypeAutoComplete>(R.id.actv)
		val btn = findViewById<Button>(R.id.btn)
		btn.setOnClickListener {
			actv.setCharacterDelay(100)
			actv.animateText("Text in testing!")
		}
	}
}