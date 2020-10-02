package chata.can.chata_ai.fragment

import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.activity.dataMessenger.ChatContract
import chata.can.chata_ai.activity.dataMessenger.presenter.ChatServicePresenter
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.chat.SimpleQuery
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs
import chata.can.chata_ai.view.animationAlert.AnimationAlert
import chata.can.chata_ai.view.typing.TypingAutoComplete

class DataMessengerFragment: BaseFragment(), ChatContract.View
{
	companion object {
		const val nameFragment = "Data Messenger"
		fun newInstance() = DataMessengerFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_data_messenger)
		}
	}

	private lateinit var llParent: View
	private lateinit var rvChat: RecyclerView
	private lateinit var gifView: View
	private lateinit var tvMsg: TextView
	private lateinit var ivRun: ImageView
	private lateinit var etQuery: TypingAutoComplete
	private lateinit var ivMicrophone: ImageView
	private lateinit var animationAlert: AnimationAlert

	private lateinit var presenter: ChatServicePresenter

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		activity?.let {
			presenter = ChatServicePresenter(it, this)
		}
	}

	override fun initListener()
	{
		animationAlert.hideAlert()
		etQuery.addTextChangedListener(object: TextChanged {
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					if (SinglentonDrawer.mIsEnableAutocomplete)
					{
						//servicePresenter.getAutocomplete(string)
					}
					with(ivMicrophone)
					{
						setImageResource(R.drawable.ic_send)
						setOnTouchListener(null)
						setOnClickListener { /*setRequestQuery()*/ }
					}
				}
				else
				{
					ivMicrophone.setImageResource(R.drawable.ic_microphone)
					//setTouchListener()
				}
			}
		})
		etQuery.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
			parent?.let {
				it.adapter?.let { adapter ->
					val text = adapter.getItem(position).toString()
					etQuery.setText(text)
//					setRequestQuery()
				}
			}
		}

		etQuery.setFinishAnimationListener {
			val query = etQuery.text.toString()
			if (query.isNotEmpty())
			{
				hideKeyboard()
				etQuery.setText("")
//				servicePresenter.getQuery(query)
			}
		}

		etQuery.setOnEditorActionListener { _, _, _ ->
//			setRequestQuery()
			false
		}

		val displayMetrics = DisplayMetrics()
		ScreenData.defaultDisplay.getRealMetrics(displayMetrics)
		val width = displayMetrics.widthPixels
		etQuery.dropDownWidth = width
	}

	override fun initViews(view: View)
	{
		with(view)
		{
			llParent = findViewById(R.id.llParent)
			rvChat = findViewById(R.id.rvChat)
			gifView = findViewById(R.id.gifView)
			tvMsg = findViewById(R.id.tvMsg)
			ivRun = findViewById(R.id.ivRun)
			etQuery = findViewById(R.id.etQuery)
			ivMicrophone = findViewById(R.id.ivMicrophone)
			animationAlert = AnimationAlert(findViewById(R.id.rlAlert))
		}
	}

	override fun setColors()
	{
		with(ThemeColor.currentColor)
		{
			activity?.let {
				llParent.setBackgroundColor(it.getParsedColor(drawerBackgroundColor))
				tvMsg.setTextColor(it.getParsedColor(drawerColorPrimary))
				ivRun.setColorFilter(it.getParsedColor(drawerColorPrimary))

				etQuery.setHintTextColor(it.getParsedColor(drawerColorPrimary))
				etQuery.setTextColor(it.getParsedColor(drawerColorPrimary))

				val blue = it.getParsedColor(drawerAccentColor)
				val circleDrawable = GradientDrawable().apply {
					shape = GradientDrawable.OVAL
					setColor(blue)
				}

				val white = it.getParsedColor(drawerBackgroundColor)
				val gray = it.getParsedColor(drawerColorPrimary)
				val rectangleDrawable = DrawableBuilder.setGradientDrawable(white,64f,1, gray)

				ivMicrophone.background = circleDrawable
				etQuery.background = rectangleDrawable
			}
		}
	}

	override fun addChatMessage(typeView: Int, message: String, query: String)
	{

	}

	override fun addNewChat(typeView: Int, queryBase: SimpleQuery)
	{

	}

	override fun isLoading(isVisible: Boolean)
	{

	}

	override fun runTyping(text: String)
	{

	}

	override fun setData(pDrawable: Pair<GradientDrawable, GradientDrawable>) {}

	override fun setDataAutocomplete(aMatches: ArrayList<String>)
	{

	}

	override fun setRecorder()
	{

	}

	override fun setSpeech(message: String)
	{

	}

	override fun setStopRecorder()
	{

	}

	override fun showAlert(message: String, intRes: Int)
	{

	}
}