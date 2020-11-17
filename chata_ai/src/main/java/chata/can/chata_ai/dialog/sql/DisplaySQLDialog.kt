package chata.can.chata_ai.dialog.sql

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog

abstract class DisplaySQLDialog(
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
		tvTitle.text = "Generated SQL"
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
}