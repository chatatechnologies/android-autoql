package chata.can.chata_ai_api.test

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai_api.R

class TestHolder(view: View): RecyclerView.ViewHolder(view)
{
	val text1 = view.findViewById<TextView>(R.id.text1) ?: null
}