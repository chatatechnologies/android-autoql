package chata.can.chata_ai.activity.chat

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.chat.adapter.ChatAdapter
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ChatData
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle

class ChatActivity: AppCompatActivity(), View.OnClickListener, ChatContract
{
	private lateinit var ivCancel: ImageView
	private lateinit var ivDelete: ImageView
	private lateinit var rvChat: RecyclerView
	private lateinit var etQuery: AutoCompleteTextView
	private lateinit var ivMicrophone: ImageView

	private lateinit var model: BaseModelList<ChatData>
	private val renderPresenter = ChatRenderPresenter(this, this)
	private lateinit var chatAdapter: ChatAdapter

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.chat_activity)

		initViews()
		initListener()
		initList()

		renderPresenter.setData()
		initData()
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
			}
		}
	}

	override fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>)
	{
		with(etQuery)
		{
			background = pDrawable.second

		}

		ivMicrophone.background = pDrawable.first
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
	}

	private fun initList()
	{
		model = BaseModelList()
		chatAdapter = ChatAdapter(model)

		model.addData(ChatData(1, "Hi Vicente! I'm here to help you access, search and analyze your date"))
		model.addData(ChatData(2, "All invoices last 4 types"))

		rvChat.layoutManager = LinearLayoutManager(this)
		rvChat.adapter = chatAdapter
	}

	private fun initData()
	{

	}
}