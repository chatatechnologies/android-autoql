package chata.can.chata_ai

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout

class ChatDrawer: LinearLayout
{
    constructor(context: Context) : super(context)
    {
        Log.e("CONSTRUCTOR", "CONSTRUCTOR 1 PARAMETERS")
        View.inflate(context, R.layout.view_chat_drawer, this)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    {
        Log.e("CONSTRUCTOR", "CONSTRUCTOR 2 PARAMETERS")
        View.inflate(context, R.layout.view_chat_drawer, this)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    {
        Log.e("CONSTRUCTOR", "CONSTRUCTOR 3 PARAMETERS")
        View.inflate(context, R.layout.view_chat_drawer, this)
    }
}