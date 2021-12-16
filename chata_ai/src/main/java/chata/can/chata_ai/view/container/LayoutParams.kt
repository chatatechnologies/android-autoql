package chata.can.chata_ai.view.container

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout

object LayoutParams {
	const val MATCH_PARENT_ONLY = 1
	const val WRAP_CONTENT_ONLY = 2
	const val MATCH_PARENT_WRAP_CONTENT = 12
	const val WRAP_CONTENT_MATCH_PARENT = 21

	private fun getConfigSize(configSize: Int): Pair<Int, Int>
	{
		return when(configSize)
		{
			MATCH_PARENT_ONLY -> Pair(-1, -1)
			WRAP_CONTENT_ONLY -> Pair(-2, -2)
			MATCH_PARENT_WRAP_CONTENT -> Pair(-1, -2)
			WRAP_CONTENT_MATCH_PARENT -> Pair(-2, -1)
			else -> Pair(-1, -1)
		}
	}

	fun getViewGroupLayoutParams(configSize: Int): ViewGroup.LayoutParams
	{
		return getConfigSize(configSize).run {
			ViewGroup.LayoutParams(first, second)
		}
	}

	fun getLinearLayoutParams(configSize: Int): LinearLayout.LayoutParams
	{
		return getConfigSize(configSize).run {
			LinearLayout.LayoutParams(first, second)
		}
	}

	fun getLinearLayoutParams(width: Int, height: Int): LinearLayout.LayoutParams
	{
		return LinearLayout.LayoutParams(width, height)
	}

	fun getRelativeLayoutParams(configSize: Int): RelativeLayout.LayoutParams
	{
		return getConfigSize(configSize).run {
			RelativeLayout.LayoutParams(first, second)
		}
	}

	fun getRelativeLayoutParams(width: Int, height: Int): RelativeLayout.LayoutParams
	{
		return RelativeLayout.LayoutParams(width, height)
	}
}