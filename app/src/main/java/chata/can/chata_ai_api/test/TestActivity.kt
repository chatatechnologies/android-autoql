package chata.can.chata_ai_api.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.addFragment
import chata.can.chata_ai_api.R

class TestActivity: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)
		addFragment(R.id.inner_secondary, supportFragmentManager, FragmentA.newInstance())
	}
}