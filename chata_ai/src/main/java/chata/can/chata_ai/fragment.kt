package chata.can.chata_ai

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

inline fun Fragment.putArgs(block: Bundle.() -> Unit) = apply {
	arguments = Bundle().apply(block)
}

fun addFragment(
	fragmentManager: FragmentManager,
	fragment: Fragment,
	nameFragment: String ?= ""
)
{
	fragmentManager.beginTransaction()
		.replace(R.id.frmLocal, fragment, nameFragment)
		.addToBackStack(null)
		.commit()
}