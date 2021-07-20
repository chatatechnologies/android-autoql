package chata.can.chata_ai_api.test

import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai_api.R
import com.google.android.material.button.MaterialButtonToggleGroup

class BubbleActivity: BaseActivity(R.layout.activity_bubble)
{
	override fun onCreateView()
	{
		var lastId = 0
		findViewById<MaterialButtonToggleGroup>(R.id.toggleButton)?.run {
			addOnButtonCheckedListener { group, checkedId, _ ->
				group.check(checkedId)
				if (lastId != 0)
				{
					group.uncheck(lastId)
				}
				lastId = checkedId
			}
		}
	}
}