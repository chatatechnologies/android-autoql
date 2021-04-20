package chata.can.chata_ai.dialog.hideColumn

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class HideDialog(
	context: Context,
	private val queryBase: QueryBase ?= null
) : BaseDialog(context, R.layout.dialog_hide, false)
	, View.OnClickListener, StatusResponse
{
	private lateinit var rlParent: View
	private lateinit var tvTitle: TextView
	private lateinit var ivCancel: ImageView
	private lateinit var vBorder: View
	private lateinit var tvColumnName: TextView
	private lateinit var tvVisibility: TextView
	private lateinit var cbAll: CheckBox
	private lateinit var rvColumn: RecyclerView
	private lateinit var btnCancel: Button
	private lateinit var btnApply: Button
	private lateinit var adapter: ColumnAdapter
	private val model = BaseModelList<ColumnQuery>()

	override fun onCreateView()
	{
		super.onCreateView()
		queryBase?.let { queryBase ->
			var isSelect = true
			for (column in queryBase.aColumn)
			{
				if (!column.isVisible)
				isSelect = false
				model.add(column.copy())
			}
			cbAll.isChecked = isSelect
			adapter = ColumnAdapter(model, queryBase)
			rvColumn.layoutManager = LinearLayoutManager(context)
			rvColumn.adapter = adapter

			setData()
		}
	}

	private fun setData()
	{
		tvTitle.text = context.getString(R.string.show_hide_column)
		ivCancel.setOnClickListener(this)
		btnCancel.setOnClickListener(this)
		btnApply.setOnClickListener(this)
		cbAll.setOnCheckedChangeListener { _, boolean ->
			for (position in 0 until model.countData())
				model[position]?.isVisible = boolean
			adapter.notifyDataSetChanged()
		}
	}

	override fun setViews()
	{
		rlParent = findViewById(R.id.rlParent)
		tvTitle = findViewById(R.id.tvTitle)
		ivCancel = findViewById(R.id.ivCancel)
		vBorder = findViewById(R.id.vBorder)
		tvColumnName = findViewById(R.id.tvColumnName)
		tvVisibility = findViewById(R.id.tvVisibility)
		cbAll = findViewById(R.id.cbAll)
		rvColumn = findViewById(R.id.rvColumn)
		btnCancel = findViewById(R.id.btnCancel)
		btnApply = findViewById(R.id.btnApply)
	}

	override fun setColors()
	{
		context.run {
			ThemeColor.currentColor.run {
				rlParent.setBackgroundColor(pDrawerBackgroundColor)
				tvTitle.setTextColor(pDrawerTextColorPrimary)
				tvColumnName.setTextColor(pDrawerTextColorPrimary)
				tvVisibility.setTextColor(pDrawerTextColorPrimary)
				vBorder.setBackgroundColor(pDrawerBorderColor)
				btnApply.background = getBackgroundColor(
					getParsedColor(R.color.blue_chata_circle), getParsedColor(R.color.blue_chata_circle))
				btnApply.setTextColor(pDrawerTextColorPrimary)
				btnCancel.background = getBackgroundColor(pDrawerBackgroundColor, pDrawerBorderColor)
				btnCancel.setTextColor(pDrawerTextColorPrimary)
			}
		}
	}

	override fun onClick(view: View?)
	{
		view?.let { it ->
			when(it.id)
			{
				R.id.ivCancel, R.id.btnCancel -> { dismiss() }
				R.id.btnApply -> {
					if (adapter.hasChanges())
					{
						queryBase?.let {
							val url = "${AutoQLData.domainUrl}/autoql/${api1}query/column-visibility?key=${AutoQLData.apiKey}"
							val header = Authentication.getAuthorizationJWT()
							val mParams = hashMapOf<String, Any>()
							val aColumns = ArrayList< HashMap<String, Any> >()
							for (column in it.aColumn)
							{
								val mColumn = hashMapOf<String, Any>("name" to column.name, "is_visible" to column.isVisible)
								aColumns.add(mColumn)
							}
							mParams["columns"] = aColumns
							callStringRequest(
								Request.Method.PUT,
								url,
								typeJSON,
								headers = header,
								parametersAny = mParams,
								listener = this)
						}
					}
					dismiss()
				}
			}
		}
	}

	override fun onFailure(jsonObject: JSONObject?)
	{
		jsonObject.toString()
	}

	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		queryBase?.resetData()
	}

	private fun getBackgroundColor(color: Int, borderColor: Int) =
		DrawableBuilder.setGradientDrawable(color, 12f, 3, borderColor)
}