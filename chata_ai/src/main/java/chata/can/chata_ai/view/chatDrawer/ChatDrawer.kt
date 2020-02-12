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
import com.android.volley.toolbox.Volley

class ChatDrawer: LinearLayout
{
    private lateinit var rvQueries: RecyclerView
    private lateinit var ivMicrophone: ImageView
    private lateinit var acAsk: AutoCompleteTextView

    private val presenter = ChatDrawerPresenter()

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

    /**
     * Inflate view for chat drawer
     * @param cContext for to inflate
     */
    private fun inflateView(cContext: Context)
    {
        View.inflate(cContext, R.layout.view_chat_drawer, this)
        initViews()
        initListener()
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
}