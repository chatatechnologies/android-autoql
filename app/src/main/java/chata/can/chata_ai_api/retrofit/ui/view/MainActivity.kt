package chata.can.chata_ai_api.retrofit.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai_api.databinding.ActivityMainBinding
import chata.can.chata_ai_api.retrofit.ui.viewModel.QuoteViewModel

class MainActivity: AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private val quoteViewModel: QuoteViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
//	buildFeatures is necessary for ActivityMainBinding with value viewBinding = true
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.model = quoteViewModel

		binding.run {
			tvIncrement.setOnClickListener {
				quoteViewModel.manageNum.increment()
			}
			tvDecrement.setOnClickListener {
				quoteViewModel.manageNum.decrement()
			}
		}



//		quoteViewModel.onCreate()
//		quoteViewModel.quoteModel.observe(this) /*Observer*/ { currentQuote ->
//			binding.tvQuote.text = currentQuote.quote
//			binding.tvAuthor.text = currentQuote.author
//		}
//		quoteViewModel.isLoading.observe(this) /*Observer*/ {
//			binding.progress.isVisible = it
//		}
//
//		binding.viewContainer.setOnClickListener {  }
	}
}