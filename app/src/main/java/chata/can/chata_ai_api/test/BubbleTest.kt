package chata.can.chata_ai_api.test

import chata.can.chata_ai.Executor
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai_api.R

class BubbleTest: BaseActivity(R.layout.activity_bubble)
{
	override fun onCreateView()
	{
		Executor({
			Thread.sleep(3000)
		}, {
			println("Hi!")
		}).execute()
	}
}