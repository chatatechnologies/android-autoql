package chata.can.chata_ai

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import chata.can.chata_ai.pojo.request.Request
import com.android.volley.toolbox.Volley

class ChatDrawer: LinearLayout
{
    constructor(context: Context) : super(context)
    {
        View.inflate(context, R.layout.view_chat_drawer, this)
        Request.requestQueue = Volley.newRequestQueue(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    {
        View.inflate(context, R.layout.view_chat_drawer, this)
        Request.requestQueue = Volley.newRequestQueue(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    {
        View.inflate(context, R.layout.view_chat_drawer, this)
        Request.requestQueue = Volley.newRequestQueue(context)
    }


}