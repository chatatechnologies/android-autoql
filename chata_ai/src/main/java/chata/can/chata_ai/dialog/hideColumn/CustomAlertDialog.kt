package chata.can.chata_ai.dialog.hideColumn

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class CustomAlertDialog(
	private val context1: Context,
	private val queryBase: QueryBase?
	): View.OnClickListener, ColumnChanges.AllColumn, StatusResponse
{
	private lateinit var rlParent: View
	private lateinit var tvTitle: TextView
	private lateinit var tvColumnName: TextView
	private lateinit var tvVisibility: TextView
	private lateinit var ivCancel: ImageView
	private lateinit var btnCancel: Button
	private lateinit var btnApply: Button
	private lateinit var cbAll: CheckBox
	private lateinit var rvColumn: RecyclerView
	private lateinit var adapter: ColumnAdapter
	private lateinit var dialog: AlertDialog
	val model = BaseModelList<ColumnQuery>()

	private val buttonChecked = CompoundButton.OnCheckedChangeListener { _, value ->
		for (position in 0 until model.countData())
			model[position]?.isVisible = value
		adapter.notifyDataSetChanged()
	}

	fun showDialog()
	{
		rlParent = RelativeLayout(context1).apply {
			layoutParams = RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT)
			//orientation = LinearLayout.VERTICAL
			id = R.id.rlParent
			paddingAll(8f)
			//region RelativeLayout
			val rlTitle = RelativeLayout(context1).apply {
				layoutParams = RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT)
				id = R.id.rlTitle
				//region Title
				tvTitle = TextView(context1).apply {
					layoutParams = RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
						addRule(RelativeLayout.CENTER_IN_PARENT)
					}
					gravity = Gravity.CENTER
					text = context1.getString(R.string.show_hide_column)
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
					setTypeface(typeface, Typeface.BOLD)
					id = R.id.tvTitle
				}
				addView(tvTitle)
				//endregion
				//region close dialog
				ivCancel = ImageView(context1).apply {
					layoutParams = RelativeLayout.LayoutParams(dpToPx(24f), dpToPx(24f))
						.apply {
							addRule(RelativeLayout.ALIGN_PARENT_END)
						}
					setImageResource(R.drawable.ic_cancel)
					id = R.id.ivCancel
				}
				addView(ivCancel)
				//endregion
			}
			addView(rlTitle)
			//endregion
			//region top menu
			val rlList = RelativeLayout(context1).apply {
				layoutParams = RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
						addRule(RelativeLayout.BELOW, R.id.rlTitle)
					}
				margin(12f, end = 12f)
				id = R.id.rlList
				//all checkBox
				cbAll = CheckBox(ContextThemeWrapper(context1, R.style.checkBoxStyle)).apply {
					layoutParams = RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT)
						.apply {
							addRule(RelativeLayout.ALIGN_PARENT_END)
						}
					id = R.id.cbAll
				}
				addView(cbAll)
				//endregion
				addView(LinearLayout(context1).apply {
					layoutParams = RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
						addRule(RelativeLayout.CENTER_VERTICAL)
						addRule(RelativeLayout.START_OF, R.id.cbAll)
					}
					orientation = LinearLayout.HORIZONTAL
					tvColumnName = TextView(context1).apply {
						layoutParams = LinearLayout.LayoutParams(
							0, LinearLayout.LayoutParams.WRAP_CONTENT
						).apply { weight = 1f }
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
						text = context1.getString(R.string.column_name)
						id = R.id.tvColumnName
					}
					addView(tvColumnName)
					tvVisibility = TextView(context1).apply {
						layoutParams = LinearLayout.LayoutParams(
							0, LinearLayout.LayoutParams.WRAP_CONTENT
						).apply { weight = 1f }
						gravity = Gravity.END
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
						text = context1.getString(R.string.visibility)
						id = R.id.tvVisibility
					}
					addView(tvVisibility)
				})
			}
			addView(rlList)
			//endregion
			//region RecyclerView
			rvColumn = RecyclerView(context1).apply {
				val numColumns = queryBase?.aColumn?.size ?: 1
				layoutParams = RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					dpToPx(33f * numColumns)).apply {
//						addRule(RelativeLayout.ABOVE, R.id.llBottom)
						addRule(RelativeLayout.BELOW, R.id.rlList)
					}
				id = R.id.rvColumn
			}
			addView(rvColumn)
			//endregion
			//region bottom actions
			val llBottom = LinearLayout(context1).apply {
				layoutParams = RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
						addRule(RelativeLayout.BELOW, R.id.rvColumn)
						//addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
					}
				orientation = LinearLayout.HORIZONTAL
				gravity = Gravity.END
				id = R.id.llBottom
				btnCancel = Button(context1).apply {
					setText(R.string.cancel)
					isAllCaps = false
					id = R.id.btnCancel
				}
				addView(btnCancel)
				btnApply = Button(context1).apply {
					setText(R.string.apply)
					isAllCaps = false
					id = R.id.btnApply
				}
				addView(btnApply)
			}
			addView(llBottom)
			//endregion
		}
		setColors()
		setColumns()
		dialog = AlertDialog.Builder(context1).create().apply {
			setView(rlParent)
			setCancelable(false)
			show()
		}
	}

	fun setColors()
	{
		context1.run {
			ThemeColor.currentColor.run {
				rlParent.setBackgroundColor(pDrawerBackgroundColor)
				tvTitle.setTextColor(pDrawerTextColorPrimary)
				tvColumnName.setTextColor(pDrawerTextColorPrimary)
				tvVisibility.setTextColor(pDrawerTextColorPrimary)
				//vBorder.setBackgroundColor(pDrawerBorderColor)
				btnApply.background = getBackgroundColor(
					getParsedColor(R.color.blue_chata_circle), getParsedColor(R.color.blue_chata_circle))
				btnApply.setTextColor(pDrawerTextColorPrimary)
				btnCancel.background = getBackgroundColor(pDrawerBackgroundColor, pDrawerBorderColor)
				btnCancel.setTextColor(pDrawerTextColorPrimary)
			}
		}
	}

	fun setColumns()
	{
		queryBase?.let { queryBase ->
			var isSelect = true
			for(column in queryBase.aColumn)
			{
				if (!column.isVisible) isSelect = false
				model.add(column.copy())
			}
			cbAll.isChecked = isSelect
			adapter = ColumnAdapter(model, queryBase, this)
			rvColumn.layoutManager = LinearLayoutManager(context1)
			rvColumn.adapter = adapter

			ivCancel.setOnClickListener(this)
			btnCancel.setOnClickListener(this)
			btnApply.setOnClickListener(this)
			cbAll.setOnCheckedChangeListener(buttonChecked)
		}
	}

	override fun onClick(view: View?)
	{
		view?.let { it ->
			when(it.id)
			{
				R.id.ivCancel, R.id.btnCancel -> dialog.dismiss()
				R.id.btnApply ->
				{
					if (adapter.hasChanges())
					{
						queryBase?.let {
							val url = "${AutoQLData.domainUrl}/autoql/${api1}query/column-visibility?key=${AutoQLData.apiKey}"
							val header = Authentication.getAuthorizationJWT()
							header["accept-language"] = SinglentonDrawer.languageCode
							val mParams = hashMapOf<String, Any>()
							val aColumns = ArrayList< HashMap<String, Any> >()
							for (column in it.aColumn)
							{
								val mColumn = hashMapOf<String, Any>("name" to column.name, "is_visible" to column.isVisible)
								aColumns.add(mColumn)
							}
							mParams["columns"] = aColumns
							RequestBuilder.callStringRequest(
								Request.Method.PUT,
								url,
								typeJSON,
								headers = header,
								parametersAny = mParams,
								listener = this
							)
						}
					}
					dialog.dismiss()
				}
			}
		}
	}

	override fun changeAllColumn(value: Boolean)
	{
		cbAll.setOnCheckedChangeListener(null)
		cbAll.isChecked = value
		cbAll.setOnCheckedChangeListener(buttonChecked)
	}

	override fun onFailure(jsonObject: JSONObject?)
	{

	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		queryBase?.run {
			canChangeHeight = true
			resetData()
		}
	}

	private fun getBackgroundColor(color: Int, borderColor: Int) =
		DrawableBuilder.setGradientDrawable(color, 12f, 3, borderColor)
}