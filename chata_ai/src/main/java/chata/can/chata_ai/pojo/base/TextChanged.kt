package chata.can.chata_ai.pojo.base

import android.text.Editable
import android.text.TextWatcher

interface TextChanged: TextWatcher
{
	override fun afterTextChanged(s: Editable?) {}
	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
	override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
	{
		onTextChanged(s?.toString() ?: "")
	}

	fun onTextChanged(string: String)
}