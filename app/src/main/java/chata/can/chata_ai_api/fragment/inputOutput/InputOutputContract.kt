package chata.can.chata_ai_api.fragment.inputOutput

import chata.can.chata_ai.dialog.DrillDownContract

interface InputOutputContract: DrillDownContract
{
	fun setDataAutocomplete(aData: ArrayList<String>)
	fun showText(text: String)
}