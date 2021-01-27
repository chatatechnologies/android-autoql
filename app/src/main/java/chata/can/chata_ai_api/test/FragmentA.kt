package chata.can.chata_ai_api.test

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.putArgs
import chata.can.chata_ai_api.R

class FragmentA: BaseFragment()
{
	companion object {
		fun newInstance() = FragmentA().putArgs {
			putInt("LAYOUT", R.layout.fragment_fragment_a)
		}
	}

	private lateinit var rvList: RecyclerView
	private lateinit var adapter: TestAdapter

	override fun setColors()
	{

	}

	private var lastPosition = 0
	override fun initViews(view: View)
	{
		with(view)
		{
			rvList = findViewById(R.id.rvList)
		}

		activity?.let {
			val model = arrayListOf(
				"Carlos Buruel Carlos Buruel Carlos Buruel ",
				"Carlos Ortiz Carlos Buruel Carlos Buruel ",
				"Ortiz Carlos Buruel ",
				"Buruel Carlos Buruel ")
			adapter = TestAdapter(model)
			rvList.layoutManager = LinearLayoutManager(it)
			rvList.adapter = adapter
			rvList.setBackgroundColor(Color.RED)
			rvList.addOnLayoutChangeListener {
				_, _, _, right, _, _, _, oldRight, _ ->
				if (lastPosition == 0)
				{
					lastPosition = right
				} else
				{
					if (lastPosition < right + 60)
					{
						adapter.notifyDataSetChanged()
					}
				}
			}
		}
	}

	override fun initListener()
	{

	}
}