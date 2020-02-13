package chata.can.chata_ai.view.chatDrawer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.view.chatDrawer.adapter.AutocompleteAdapter
import com.android.volley.toolbox.Volley

class ChatDrawer: LinearLayout, ChatDrawerContract
{
	private lateinit var rvQueries: RecyclerView
	private lateinit var ivMicrophone: ImageView
	private lateinit var acAsk: AutoCompleteTextView

	private lateinit var adapter: AutocompleteAdapter

	private val presenter = ChatDrawerPresenter(this)

	constructor(cContext: Context) : super(cContext)
	{
		startView(cContext)
	}

	constructor(cContext: Context, attrs: AttributeSet) : super(cContext, attrs)
	{
		startView(cContext)
	}

	constructor(cContext: Context, attrs: AttributeSet, defStyleAttr: Int) : super(cContext, attrs, defStyleAttr)
	{
		startView(cContext)
	}

	private fun startView(cContext: Context)
	{
		inflateView(cContext)
		startRequest(cContext)
	}

	override fun setDataAutocomplete(aMatches: ArrayList<String>)
	{
		resources?.let {
			it.displayMetrics?.let {
				itDisplayMetrics ->
				val sizeAutocomplete = if (aMatches.size > 4) 4 else aMatches.size
				val height = (sizeAutocomplete * itDisplayMetrics.density * 40).toInt()
				acAsk.dropDownHeight = height

				adapter.clear()
				if (aMatches.isNotEmpty())
				{
					adapter.addAll(aMatches)
				}
				adapter.notifyDataSetChanged()
			}
		}
	}

	/**
	 * Inflate view for chat drawer
	 * @param cContext for to inflate
	 */
	private fun inflateView(cContext: Context)
	{
		View.inflate(cContext, R.layout.view_chat_drawer, this)
		initViews()
		initListener()
		initData()
	}

	private fun startRequest(cContext: Context)
	{
		RequestBuilder.requestQueue = Volley.newRequestQueue(cContext)
	}

	private fun initViews()
	{
		rvQueries = findViewById(R.id.rvQueries)
		ivMicrophone = findViewById(R.id.ivMicrophone)
		acAsk = findViewById(R.id.acAsk)
	}

	private fun initListener()
	{
		acAsk.addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				val queryEncoded = string.replace(" ", "%20")
					.replace("?", "%3F")
				presenter.autocomplete(queryEncoded)
			}
		})
	}

	private fun initData()
	{
		acAsk.threshold = 1
		adapter = AutocompleteAdapter(context)
		acAsk.setAdapter(adapter)
	}
}