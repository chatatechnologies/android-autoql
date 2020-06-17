package chata.can.chata_ai

import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun Fragment.putArgs(block: Bundle.() -> Unit) = apply {
	arguments = Bundle().apply(block)
}