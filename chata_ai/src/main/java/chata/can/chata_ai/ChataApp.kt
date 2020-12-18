package chata.can.chata_ai

import android.app.Application
import android.content.Context

class ChataApp : Application()
{
	companion object {
		lateinit var context: Context
	}

	override fun onCreate()
	{
		super.onCreate()
		context = applicationContext
	}
}