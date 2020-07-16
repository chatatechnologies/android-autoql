package chata.can.chata_ai_api.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai_api.R
import com.carlos.buruel.textviewspinner.SpinnerTextView
import com.carlos.buruel.textviewspinner.model.Suggestion

class MainActivity: AppCompatActivity()
{
	private lateinit var stvContent: SpinnerTextView

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		baseContext?.let {
			it.display
		}
		val newDisplay = this.display

		val aData = arrayListOf(
			//Suggestion("total",0,0, null),
			Suggestion("total",0,5, arrayListOf("total", "totel (Original term)")),
			Suggestion("stationery and printing",6, 29, arrayListOf("stationery and printing (account name)", "Digital Post Printing (vendor name)", "opereting (Original term)")),
			Suggestion("expenses",30,38, arrayListOf("expenses", "expinses (Original term)")),
			Suggestion("by",39,41, arrayListOf("by", "bu (Original term)")),
			Suggestion("account",42,49, arrayListOf("account", "accaunt (Original term)"))
		)

		stvContent = findViewById(R.id.stvContent)
		stvContent.setWindowManager(windowManager)
		stvContent.setText(aData)
	}
}