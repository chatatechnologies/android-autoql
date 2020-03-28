package chata.can.chata_ai.pojo.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(private val intRes: Int): AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(intRes)
		onCreateView()
	}

	abstract fun onCreateView()

	fun hideKeyboard()
	{
		currentFocus?.let {
			val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
			imm.hideSoftInputFromWindow(it.windowToken, 0)
		}
	}
}