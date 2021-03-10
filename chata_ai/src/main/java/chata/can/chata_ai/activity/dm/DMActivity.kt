package chata.can.chata_ai.activity.dm

import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.base.BaseActivity

class DMActivity: BaseActivity(R.layout.view_pager_options)
{
	override fun onCreateView()
	{

	}

	override fun finish() {
		super.finish()
		overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out)
	}
}