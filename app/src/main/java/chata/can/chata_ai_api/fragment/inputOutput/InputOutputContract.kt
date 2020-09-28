package chata.can.chata_ai_api.fragment.inputOutput

import chata.can.chata_ai.dialog.DrillDownContract
import chata.can.chata_ai.pojo.chat.FullSuggestionQuery

interface InputOutputContract: DrillDownContract
{
	fun setDataAutocomplete(aData: ArrayList<String>)
	fun showText(text: String)
	fun showSuggestion(aItems: ArrayList<String>)
	fun showFullSuggestion(fullSuggestion: FullSuggestionQuery)
}