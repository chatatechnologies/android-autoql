package chata.can.chata_ai.view.container

import android.view.ViewGroup

object LayoutParams {
	val MATCH_PARENT = 1
	val WRAP_CONTENT = 2
	val MATCH_PARENT_WRAP_CONTENT = 12
	val WRAP_CONTENT_MATCH_PARENT = 21

	private fun getConfigSize(configSize: Int): Pair<Int, Int>
	{
		return when(configSize)
		{
			MATCH_PARENT -> Pair(-1, -1)
			WRAP_CONTENT -> Pair(-2, -2)
			MATCH_PARENT_WRAP_CONTENT -> Pair(-1, -2)
			WRAP_CONTENT_MATCH_PARENT -> Pair(-2, -1)
			else -> Pair(-1, -1)
		}
	}

	fun getViewGroupLayoutParam(configSize: Int): ViewGroup.LayoutParams
	{
		return getConfigSize(configSize).run {
			ViewGroup.LayoutParams(first, second)
		}
	}
}