package chata.can.chata_ai.dialog.sql

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog

class DisplaySQLDialog(
	context: Context,
	private val query: String
): BaseDialog(context, R.layout.dialog_display_sql)
{
	private lateinit var rlTitle: View
	private lateinit var tvTitle: TextView
	private lateinit var ivCancel: ImageView
	private lateinit var etQuery: TextView

	override fun onCreateView()
	{
		super.onCreateView()
		tvTitle.setText(R.string.generated_sql)
		etQuery.text = formatterSQL(query)
	}

	override fun setColors()
	{

	}

	override fun setViews()
	{
		rlTitle = findViewById(R.id.rlTitle)
		tvTitle = findViewById(R.id.tvTitle)
		ivCancel = findViewById(R.id.ivCancel)
		etQuery = findViewById(R.id.etQuery)
	}

	private val aKeywords = arrayListOf("select", "from", "where")
	private fun formatterSQL(query: String): String
	{
		var iHead = -1
		val sb = StringBuilder("")
		while (iHead < query.length)
		{
			//search white
			val index = query.indexOf(" ", iHead + 1)
			iHead = if (index != -1)
			{
				val word = query.substring(iHead + 1, index)
				val extra = if (word in aKeywords) "\n" else ""
				sb.append("$word$extra")
				index
			}
			else
			{
				sb.append(query.substring(iHead, query.length))
				query.length
			}
		}
		return sb.toString()
	}
}