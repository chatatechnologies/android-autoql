package chata.can.chata_ai.activity.chat

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.adapter.AutoCompleteAdapter
import chata.can.chata_ai.activity.chat.adapter.ChatAdapter
import chata.can.chata_ai.extension.getStringResources
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.request.RequestBuilder
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import com.android.volley.toolbox.Volley
import java.net.URLEncoder

class ChatActivity: AppCompatActivity(), View.OnClickListener, ChatContract
{
	private lateinit var ivCancel: ImageView
	private lateinit var ivDelete: ImageView
	private lateinit var rvChat: RecyclerView
	private lateinit var etQuery: AutoCompleteTextView
	private lateinit var ivMicrophone: ImageView

	private lateinit var model: BaseModelList<ChatData>
	private lateinit var adapterAutoComplete: AutoCompleteAdapter
	private val renderPresenter = ChatRenderPresenter(this, this)
	private val servicePresenter = ChatServicePresenter(this, this)
	private lateinit var chatAdapter: ChatAdapter

	private var customerName = ""

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.chat_activity)

		initConfig()
		initViews()
		initData()
		initListener()
		initList()

		renderPresenter.setData()
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean
	{
		menuInflater.inflate(R.menu.chat_menu, menu)
		return true
	}

	override fun finish()
	{
		super.finish()
		BubbleHandle.isOpenChat = false
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_top)
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.ivCancel ->
				{
					finish()
				}
				R.id.ivDelete ->
				{

				}
				//TODO REMOVE
				R.id.ivMicrophone ->
				{
					setRequestQuery()
				}
			}
		}
	}

	override fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>)
	{
		with(etQuery)
		{
			background = pDrawable.second
			//setDropDownBackgroundDrawable(pDrawable.second)

			adapterAutoComplete = AutoCompleteAdapter(this@ChatActivity, R.layout.row_spinner)

			threshold = 1
			setAdapter(adapterAutoComplete)
			etQuery.onItemClickListener = AdapterView.OnItemClickListener {
				parent, _, position, _ ->
				parent?.let {
					it.adapter?.let {
						val text = it.getItem(position).toString()
						etQuery.setText(text)
						setRequestQuery()
					}
				}
			}

			setOnEditorActionListener {
				_, _, _ ->
				setRequestQuery()
				false
			}

			val displayMetrics = DisplayMetrics()
			ScreenData.defaultDisplay.getMetrics(displayMetrics)
			val width = displayMetrics.widthPixels
			etQuery.dropDownWidth = width
		}

		ivMicrophone.background = pDrawable.first
	}

	override fun setDataAutocomplete(aMatches: ArrayList<String>)
	{
		adapterAutoComplete.clear()
		if (aMatches.isNotEmpty())
		{
			adapterAutoComplete.addAll(aMatches)

			val size = Point()
			ScreenData.defaultDisplay.getSize(size)
			val maxHeight = size.y * 0.35

			val count = adapterAutoComplete.count
			val height = ScreenData.densityByDP * (if (count < 2) 2 else adapterAutoComplete.count) * 40

			etQuery.dropDownHeight =
				if (height < maxHeight)
					height.toInt()
				else
					maxHeight.toInt()
		}
		adapterAutoComplete.notifyDataSetChanged()
	}

	override fun addNewChat(queryBase: QueryBase)
	{
		val chatData = ChatData(1, "", queryBase)
		model.addData(chatData)

		chatAdapter.notifyItemChanged(model.countData() - 1)
	}

	private fun initConfig()
	{
		windowManager?.let {
			ScreenData.defaultDisplay = it.defaultDisplay
		}
		resources?.let {
			it.displayMetrics?.let {
					itMetrics ->
				ScreenData.densityByDP = itMetrics.density
			}
		}
		RequestBuilder.requestQueue = Volley.newRequestQueue(this)
	}

	private fun initViews()
	{
		ivCancel = findViewById(R.id.ivCancel)
		ivDelete = findViewById(R.id.ivDelete)
		rvChat = findViewById(R.id.rvChat)
		etQuery = findViewById(R.id.etQuery)
		ivMicrophone = findViewById(R.id.ivMicrophone)
	}

	private fun initListener()
	{
		ivCancel.setOnClickListener(this)
		ivDelete.setOnClickListener(this)
		//TODO REMOVE
		ivMicrophone.setOnClickListener(this)
		etQuery.addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					servicePresenter.getAutocomplete(URLEncoder.encode(string, "UTF-8"))
				}
			}
		})
	}

	private fun initList()
	{
		model = BaseModelList()
		chatAdapter = ChatAdapter(model)

		val introMessageRes = (this as Context).getStringResources(R.string.intro_message_chata_drawer)
		val introMessage = String.format(introMessageRes, customerName)

		model.addData(ChatData(1, introMessage))
		//model.addData(ChatData(2, "All invoices last 4 types"))
		rvChat.layoutManager = LinearLayoutManager(this)
		rvChat.adapter = chatAdapter
	}

	private fun initData()
	{
		customerName = intent?.getStringExtra("CUSTOMER_NAME") ?: ""
	}

	private fun setRequestQuery()
	{
		val query = etQuery.text.toString()
		if (query.isNotEmpty())
		{
			etQuery.setText("")
			model.addData(ChatData(2, query))

			chatAdapter.notifyItemChanged(model.countData() - 1)

			servicePresenter.getSafety(query)
		}
	}
}