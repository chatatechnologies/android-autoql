package chata.can.chata_ai_api.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai_api.R
import chata.can.chata_ai_api.databinding.TestActivityBinding

class TestActivity: AppCompatActivity()
{
	private lateinit var binding: TestActivityBinding
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		binding = TestActivityBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.textView.text = "Hi!"

	}
}