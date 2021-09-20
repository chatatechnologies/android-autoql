package chata.can.chata_ai.view.popup

import android.content.Context
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.sql.DisplaySQLDialog

object PopupConfig
{
	val mIndexMethod = arrayListOf(
		PopupData(R.id.iColumn, R.string.show_hide_column, R.drawable.ic_eye_1),
		PopupData(R.id.iFilterTable, R.string.filter_table, R.drawable.ic_filter),
		PopupData(R.id.iReportProblem, R.string.report_problem, R.drawable.ic_report_m),
		PopupData(R.id.iDelete, R.string.delete_response, R.drawable.ic_delete_m),
		PopupData(R.id.iGenerateSQL, R.string.view_generated_sql, R.drawable.ic_database),
	)

	fun generateSQL(context: Context, sql: String) = DisplaySQLDialog(context, sql).show()
}