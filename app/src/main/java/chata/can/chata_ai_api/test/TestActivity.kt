package chata.can.chata_ai_api.test

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai_api.R

class TestActivity: AppCompatActivity(), View.OnClickListener
{

//	private lateinit var btn1: Button
	private lateinit var btn: Button
//	private lateinit var btn2: Button
	private lateinit var tvMsg: TextView

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)

//		btn1 = findViewById(R.id.btn1)
		btn = findViewById(R.id.btn)
//		btn2 = findViewById(R.id.btn2)
		tvMsg = findViewById(R.id.tvMsg)

		btn.setOnClickListener(this)
//		btn1.setOnClickListener(this)
//		btn2.setOnClickListener(this)
		RequestBuilder.initVolleyRequest(this)
		btn.setOnClickListener {

		}
	}

	@SuppressLint("SetTextI18n")
	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.btn ->
				{
					tvMsg.setBackgroundColor(Color.BLUE)
					tvMsg.text = "A tip"
				}
				R.id.btn1 ->
				{
					tvMsg.setBackgroundColor(Color.RED)
					tvMsg.text = "A chat"
				}
				R.id.btn2 ->
				{
					tvMsg.setBackgroundColor(Color.MAGENTA)
					tvMsg.text = "A notification"
				}
			}
		}
	}
}