package chata.can.chata_ai_api.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class TestActivity: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		val locale = Locale("es", "MX")

		for (index in 1 until 13)
		{
			val stringDate = "$index"
			val dateFormat = SimpleDateFormat("MM", locale)
			val date = dateFormat.parse(stringDate) ?: Date(0L)
			val dateFormat1 = SimpleDateFormat("MMM", locale)
			val month = dateFormat1.format(date)
			print("Month spanish: $month")
		}
	}
}