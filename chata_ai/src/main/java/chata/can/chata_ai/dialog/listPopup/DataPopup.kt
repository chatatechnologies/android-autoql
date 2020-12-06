package chata.can.chata_ai.dialog.listPopup

import android.view.View
import chata.can.chata_ai.fragment.dataMessenger.ChatContract
import chata.can.chata_ai.fragment.dataMessenger.adapter.ChatAdapterContract

data class DataPopup(
	val viewRoot: View,
	val chatView: ChatContract.View?,
	val adapterView: ChatAdapterContract?,
	val adapterPosition: Int,
	val queryId: String,
	val sql: String,
	val isReduce: Boolean)
