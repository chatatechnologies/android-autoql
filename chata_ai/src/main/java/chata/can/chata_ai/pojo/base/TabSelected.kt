package chata.can.chata_ai.pojo.base

import com.google.android.material.tabs.TabLayout

interface TabSelected: TabLayout.OnTabSelectedListener
{
	override fun onTabReselected(tab: TabLayout.Tab?) {}
}