package chata.can.chata_ai_api.test

import android.os.Handler
import android.os.Looper
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai_api.R
import java.util.concurrent.Executors

class BubbleTest: BaseActivity(R.layout.activity_bubble)
{
	override fun onCreateView()
	{
		val executor = Executors.newSingleThreadExecutor()
		val handler = Handler(Looper.getMainLooper())

		object: Runnable {
			override fun run()
			{
				//Background work here
				handler.post(object: Runnable {
					override fun run() {
						//UI Thread work here
					}
				})
			}
		}
	}
}