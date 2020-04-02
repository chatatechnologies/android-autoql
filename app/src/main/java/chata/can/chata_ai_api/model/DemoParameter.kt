package chata.can.chata_ai_api.model

import android.util.SparseArray

data class DemoParameter(
	val label: String,
	val type: TypeParameter,
	val value: String = "false",
	val options: SparseArray<Segment> = SparseArray(),
	val labelId: Int = 0,
	val idView: Int = 0)